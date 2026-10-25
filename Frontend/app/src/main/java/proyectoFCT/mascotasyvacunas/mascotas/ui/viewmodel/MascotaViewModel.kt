package proyectoFCT.mascotasyvacunas.mascotas.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import proyectoFCT.mascotasyvacunas.mascotas.data.network.MascotaRepository
import proyectoFCT.mascotasyvacunas.mascotas.domain.CreateMascotaUseCase
import proyectoFCT.mascotasyvacunas.mascotas.domain.DeleteMascotaUseCase
import proyectoFCT.mascotasyvacunas.mascotas.domain.GetAllMascotasUseCase
import proyectoFCT.mascotasyvacunas.mascotas.domain.UpdateMascotaUseCase
import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MascotaViewModel @Inject constructor(
    private val getAllMascotasUseCase: GetAllMascotasUseCase,
    private val createMascotaUseCase: CreateMascotaUseCase,
    private val updateMascotaUseCase: UpdateMascotaUseCase,
    private val deleteMascotaUseCase: DeleteMascotaUseCase,
    private val mascotaRepository: MascotaRepository
) : ViewModel() {

    // Lista de mascotas de la aplicación.
    private val _mascotas =
        MutableStateFlow<List<Mascota>>(emptyList())
    val mascotas = _mascotas.asStateFlow()

    // Indicar si se están cargando las mascotas inicialmente.
    private val _cargandoInicial = MutableStateFlow(true)
    val cargandoInicial = _cargandoInicial.asStateFlow()

    // Controlar la visibilidad del diálogo de eliminación.
    private val _showDialog = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()

    // Guardar el identificador de la mascota seleccionada.
    private var idMascota: Long? = null

    // Guardar los mensajes que se muestran en pantalla.
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    // Controlar la visibilidad del diálogo de éxito.
    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog = _showSuccessDialog.asStateFlow()

    // Indicar si se está subiendo una fotografía.
    private val _subiendoFoto = MutableStateFlow(false)
    val subiendoFoto = _subiendoFoto.asStateFlow()

    // Almacenar las fotografías descargadas mediante su identificador.
    private val _fotosMascotas =
        MutableStateFlow<Map<Long, Bitmap>>(emptyMap())
    val fotosMascotas = _fotosMascotas.asStateFlow()

    // Registrar las mascotas cuyas fotografías se están cargando.
    private val fotosCargando = mutableSetOf<Long>()

    // Registrar las mascotas cuyas fotografías ya se han consultado.
    private val _fotosMascotasCargadas =
        MutableStateFlow<Set<Long>>(emptySet())

    val fotosMascotasCargadas = _fotosMascotasCargadas.asStateFlow()

    // Evitar que se inicien varias cargas de mascotas simultáneamente.
    private var cargandoMascotas = false

    // Cargar las mascotas al crear el ViewModel.
    init {
        cargarMascotas()
    }

    // Ocultar el diálogo de éxito.
    fun hideSuccessDialog() {
        _showSuccessDialog.value = false
    }

    // Obtener las mascotas y cargar sus fotografías.
    fun cargarMascotas() {
        if (cargandoMascotas) return

        viewModelScope.launch {
            cargandoMascotas = true
            _cargandoInicial.value = true

            try {
                val result = getAllMascotasUseCase()

                result.fold(
                    onSuccess = { respuestas ->
                        val lista = respuestas.map { it.toModel() }
                        _mascotas.value = lista

                        coroutineScope {
                            lista.mapNotNull { it.id }
                                .map { id ->
                                    async {
                                        cargarFotoMascota(id)
                                    }
                                }
                                .awaitAll()
                        }
                    },
                    onFailure = {
                        _message.value =
                            it.message ?: "Error al cargar las mascotas"
                    }
                )
            } catch (e: Exception) {
                _message.value =
                    e.message ?: "Error al cargar las mascotas"
            } finally {
                _cargandoInicial.value = false
                cargandoMascotas = false
            }
        }
    }

    // Descargar la fotografía de una mascota.
    suspend fun cargarFotoMascota(id: Long) {
        if (_fotosMascotas.value.containsKey(id)) {
            _fotosMascotasCargadas.value += id
            return
        }

        if (!fotosCargando.add(id)) return

        try {
            val respuesta = mascotaRepository.obtenerFotoMascota(id)

            if (respuesta.isSuccessful) {
                val bitmap = withContext(Dispatchers.IO) {
                    respuesta.body()?.use { cuerpo ->
                        val bytes = cuerpo.bytes()
                        BitmapFactory.decodeByteArray(
                            bytes,
                            0,
                            bytes.size
                        )
                    }
                }

                if (bitmap != null) {
                    _fotosMascotas.value += (id to bitmap)
                }
            }
        } catch (_: Exception) {
            // Si no hay foto, se muestra la ilustración predeterminada.
        } finally {
            _fotosMascotasCargadas.value += id
            fotosCargando.remove(id)
        }
    }

    // Volver a descargar la fotografía de una mascota.
    suspend fun recargarFotoMascota(id: Long) {
        try {
            val respuesta = mascotaRepository.obtenerFotoMascota(id)

            if (respuesta.isSuccessful) {
                val bitmap = withContext(Dispatchers.IO) {
                    respuesta.body()?.use { cuerpo ->
                        val bytes = cuerpo.bytes()
                        BitmapFactory.decodeByteArray(
                            bytes,
                            0,
                            bytes.size
                        )
                    }
                }

                if (bitmap != null) {
                    _fotosMascotas.value += (id to bitmap)
                }
            }
        } catch (_: Exception) {
            // Si falla la descarga, se conserva la foto anterior.
        }
    }

    // Guardar el identificador de la mascota seleccionada.
    fun setIdMascota(id: Long?) {
        idMascota = id
    }

    // Mostrar u ocultar el diálogo de eliminación.
    fun changeShowDialog() {
        _showDialog.value = !_showDialog.value
    }

    // Crear una mascota y, si se selecciona, subir su fotografía.
    fun createMascota(
        mascota: Mascota,
        context: Context? = null,
        fotoUri: Uri? = null
    ) {
        viewModelScope.launch {
            _message.value = null

            val result = createMascotaUseCase(mascota)

            result.fold(
                onSuccess = { respuesta ->
                    val idCreado = respuesta.id

                    if (fotoUri != null && context != null) {
                        if (idCreado == null) {
                            _message.value =
                                "La mascota se ha guardado, pero no se ha podido obtener su identificador para subir la foto."
                            cargarMascotas()
                            return@fold
                        }

                        _subiendoFoto.value = true

                        val fotoGuardada = try {
                            subirFotoMascota(
                                context = context,
                                id = idCreado,
                                uri = fotoUri
                            )
                        } catch (e: Exception) {
                            _message.value =
                                e.message ?: "No se pudo subir la fotografía"
                            false
                        } finally {
                            _subiendoFoto.value = false
                        }

                        // Quitar la foto anterior de la memoria.
                        _fotosMascotas.value -= idCreado
                        _fotosMascotasCargadas.value -= idCreado

                        cargarMascotas()

                        if (fotoGuardada) {
                            _showSuccessDialog.value = true
                        } else {
                            _message.value =
                                "La mascota se ha guardado, pero no se ha podido subir la fotografía. Puedes intentarlo de nuevo."
                        }
                    } else {
                        cargarMascotas()
                        _showSuccessDialog.value = true
                    }
                },
                onFailure = {
                    _message.value =
                        it.message ?: "Error al registrar la mascota"
                }
            )
        }
    }

    // Actualizar una mascota y, si se selecciona, cambiar su fotografía.
    fun updateMascota(
        id: Long,
        mascota: Mascota,
        context: Context? = null,
        fotoUri: Uri? = null,
        onFotoActualizada: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            _message.value = null

            val result = updateMascotaUseCase(
                id = id,
                mascota = mascota
            )

            result.fold(
                onSuccess = {
                    if (fotoUri != null && context != null) {
                        _subiendoFoto.value = true

                        val fotoGuardada = try {
                            subirFotoMascota(
                                context = context,
                                id = id,
                                uri = fotoUri
                            )
                        } catch (e: Exception) {
                            _message.value =
                                e.message ?: "No se pudo actualizar la fotografía"
                            false
                        } finally {
                            _subiendoFoto.value = false
                        }

                        if (fotoGuardada) {
                            recargarFotoMascota(id)
                            onFotoActualizada?.invoke()
                            _message.value =
                                "Datos y fotografía actualizados correctamente"
                            _showSuccessDialog.value = true
                        } else {
                            cargarMascotas()
                        }
                    } else {
                        cargarMascotas()
                        _showSuccessDialog.value = true
                    }
                },
                onFailure = {
                    _message.value =
                        it.message ?: "Error al actualizar la mascota"
                }
            )
        }
    }

    // Subir una fotografía seleccionada a la API.
    private suspend fun subirFotoMascota(
        context: Context,
        id: Long,
        uri: Uri
    ): Boolean {
        val resolver = context.contentResolver
        val tipoContenido = resolver.getType(uri) ?: "image/jpeg"

        val bytes = resolver.openInputStream(uri)?.use {
            it.readBytes()
        } ?: throw IOException(
            "No se ha podido leer la fotografía seleccionada"
        )

        if (bytes.isEmpty()) {
            throw IOException("La fotografía está vacía")
        }

        // Comprobar que la fotografía no supera los 5 MB.
        if (bytes.size > 5 * 1024 * 1024) {
            throw IOException(
                "La fotografía no puede superar los 5 MB"
            )
        }

        // Comprobar que el archivo seleccionado es una imagen.
        if (!tipoContenido.lowercase().startsWith("image/")) {
            throw IOException("El archivo seleccionado no es una imagen")
        }

        // Preparar los datos de la imagen para enviarlos mediante HTTP.
        val requestBody = bytes.toRequestBody(
            tipoContenido.toMediaTypeOrNull()
        )

        val archivo = MultipartBody.Part.createFormData(
            "archivo",
            "foto_mascota",
            requestBody
        )

        val respuesta = mascotaRepository.subirFotoMascota(
            id,
            archivo
        )

        if (!respuesta.isSuccessful) {
            val detalleError = respuesta.errorBody()
                ?.string()
                ?.takeIf { it.isNotBlank() }

            throw IOException(
                "No se pudo subir la fotografía. " +
                        "Código HTTP: ${respuesta.code()}. " +
                        (detalleError ?: respuesta.message())
            )
        }

        return true
    }

    // Eliminar una mascota y quitar su fotografía de la memoria.
    fun deleteMascota() {
        viewModelScope.launch {
            idMascota?.let { id ->
                val result = deleteMascotaUseCase(id)

                result.fold(
                    onSuccess = {
                        _fotosMascotas.value -= id
                        _fotosMascotasCargadas.value -= id
                        cargarMascotas()
                        changeShowDialog()
                    },
                    onFailure = {
                        _message.value =
                            it.message ?: "Error al eliminar la mascota"
                    }
                )
            }
        }
    }
}
