package proyectoFCT.mascotasyvacunas.vacunaciones.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import proyectoFCT.mascotasyvacunas.vacunaciones.data.network.VacunacionRepository
import proyectoFCT.mascotasyvacunas.vacunaciones.domain.GetAllVacunacionesbyMascotaUseCase
import proyectoFCT.mascotasyvacunas.vacunaciones.domain.CreateVacunacionUseCase
import proyectoFCT.mascotasyvacunas.vacunaciones.domain.DeleteVacunacionUseCase
import proyectoFCT.mascotasyvacunas.vacunaciones.domain.UpdateVacunacionUseCase
import proyectoFCT.mascotasyvacunas.vacunaciones.ui.model.Vacunacion
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class VacunacionViewModel @Inject constructor(
    private val getVacunacionesByMascotaUseCase: GetAllVacunacionesbyMascotaUseCase,
    private val createVacunacionUseCase: CreateVacunacionUseCase,
    private val updateVacunacionUseCase: UpdateVacunacionUseCase,
    private val deleteVacunacionUseCase: DeleteVacunacionUseCase,
    private val vacunacionRepository: VacunacionRepository
) : ViewModel() {

    private val _vacunaciones = MutableStateFlow<List<Vacunacion>>(emptyList())
    val vacunaciones = _vacunaciones.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    private val _fotosEtiquetas = MutableStateFlow<Map<Long, Bitmap>>(emptyMap())
    val fotosEtiquetas = _fotosEtiquetas.asStateFlow()

    private val _fotosEtiquetasCargadas = MutableStateFlow<Set<Long>>(emptySet())
    val fotosEtiquetasCargadas = _fotosEtiquetasCargadas.asStateFlow()

    private val fotosCargando = mutableSetOf<Long>()

    // Cargar las vacunaciones de una mascota.
    fun cargarVacunaciones(mascotaId: Long) {
        viewModelScope.launch {
            val result = getVacunacionesByMascotaUseCase(mascotaId)
            result.fold(
                onSuccess = {
                    _vacunaciones.value = it.map { vacunacionResponse ->
                        vacunacionResponse.toModel()
                    }
                },
                onFailure = {
                    _message.value =
                        it.message
                            ?: "Error al obtener las vacunaciones"
                }
            )
        }
    }

    // Crear una nueva vacunación.
    fun createVacunacion(mascotaId: Long, vacunacion: Vacunacion) {
        viewModelScope.launch {
            val result =
                createVacunacionUseCase(mascotaId, vacunacion)
            result.fold(
                onSuccess = {
                    cargarVacunaciones(mascotaId)
                },
                onFailure = {
                    _message.value =
                        it.message
                            ?: "Error al registrar la vacunación"
                }
            )
        }
    }

    // Actualizar una vacunación.
    fun updateVacunacion(mascotaId: Long, id: Long, vacunacion: Vacunacion) {
        viewModelScope.launch {
            val result =
                updateVacunacionUseCase(id, vacunacion)
            result.fold(
                onSuccess = {
                    cargarVacunaciones(mascotaId)
                },
                onFailure = {
                    _message.value =
                        it.message
                            ?: "Error al actualizar la vacunación"
                }
            )
        }
    }

    // Eliminar una vacunación.
    fun deleteVacunacion(mascotaId: Long, id: Long) {
        viewModelScope.launch {
            val result =
                deleteVacunacionUseCase(id)
            result.fold(
                onSuccess = {
                    _fotosEtiquetas.value -= id
                    _fotosEtiquetasCargadas.value -= id
                    cargarVacunaciones(mascotaId)
                },
                onFailure = {
                    _message.value =
                        it.message
                            ?: "Error al eliminar la vacunación"
                }
            )
        }
    }

    // Sube la fotografía de la etiqueta de una vacunación.
    fun subirFotoEtiqueta(context: Context, mascotaId: Long, vacunacionId: Long, uri: Uri) {
        viewModelScope.launch {
            try {
                val resolver = context.contentResolver
                val tipoContenido = resolver.getType(uri) ?: "image/jpeg"

                if (!tipoContenido.lowercase().startsWith("image/")) {
                    throw IOException("El archivo seleccionado no es una imagen")
                }

                val bytes = resolver.openInputStream(uri)?.use {
                    it.readBytes()
                } ?: throw IOException(
                    "No se ha podido leer la imagen seleccionada"
                )

                if (bytes.isEmpty()) {
                    throw IOException("La imagen está vacía")
                }

                if (bytes.size > 5 * 1024 * 1024) {
                    throw IOException("La imagen no puede superar los 5 MB")
                }

                val requestBody = bytes.toRequestBody(
                    tipoContenido.toMediaTypeOrNull()
                )

                val archivo = MultipartBody.Part.createFormData(
                    "archivo",
                    "etiqueta_vacuna",
                    requestBody
                )

                val respuesta = withContext(Dispatchers.IO) {
                    vacunacionRepository.subirFotoEtiqueta(
                        id = vacunacionId,
                        archivo = archivo
                    )
                }

                if (respuesta.isSuccessful) {
                    _fotosEtiquetas.value -= vacunacionId
                    _fotosEtiquetasCargadas.value -= vacunacionId

                    val fotoDescargada = descargarFotoEtiqueta(vacunacionId)

                    _message.value = if (fotoDescargada) {
                        "Fotografía subida correctamente"
                    } else {
                        "La foto se ha subido, pero no se ha podido mostrar. Vuelve a abrir la pantalla."
                    }

                    cargarVacunaciones(mascotaId)
                } else {
                    val errorServidor = respuesta.errorBody()?.use {
                        it.string()
                    }

                    _message.value =
                        "Error al subir la fotografía: HTTP ${respuesta.code()}. " +
                                (errorServidor?.takeIf { it.isNotBlank() }
                                    ?: respuesta.message())
                }
            } catch (e: Exception) {
                android.util.Log.e(
                    "FotoVacunacion",
                    "Error al subir la fotografía",
                    e
                )

                _message.value =
                    "Error al subir la fotografía: ${e.message}"
            }
        }
    }


    // Carga la fotografía de la etiqueta de una vacunación si todavía no se ha cargado.
    fun cargarFotoEtiqueta(vacunacionId: Long) {
        if (vacunacionId in _fotosEtiquetasCargadas.value) return

        synchronized(fotosCargando) {
            if (!fotosCargando.add(vacunacionId)) return
        }

        viewModelScope.launch {
            try {
                descargarFotoEtiqueta(vacunacionId)
            } finally {
                synchronized(fotosCargando) {
                    fotosCargando.remove(vacunacionId)
                }
            }
        }
    }


    // Descarga la fotografía de la etiqueta y la convierte en un Bitmap para mostrarla.
    private suspend fun descargarFotoEtiqueta(vacunacionId: Long): Boolean {
        return try {
            val respuesta = withContext(Dispatchers.IO) {
                vacunacionRepository.obtenerFotoEtiqueta(vacunacionId)
            }

            if (!respuesta.isSuccessful) {
                false
            } else {
                val bitmap = withContext(Dispatchers.IO) {
                    respuesta.body()?.use { cuerpo ->
                        val bytes = cuerpo.bytes()
                        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    }
                }

                if (bitmap != null) {
                    _fotosEtiquetas.value += (vacunacionId to bitmap)

                    _fotosEtiquetasCargadas.value += vacunacionId

                    true
                } else {
                    false
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(
                "FotoVacunacion",
                "Error al descargar la fotografía",
                e
            )
            false
        } finally {
            _fotosEtiquetasCargadas.value += vacunacionId
        }
    }


}