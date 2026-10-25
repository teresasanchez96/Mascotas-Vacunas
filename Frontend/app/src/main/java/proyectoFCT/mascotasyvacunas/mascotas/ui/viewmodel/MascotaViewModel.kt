package proyectoFCT.mascotasyvacunas.mascotas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import proyectoFCT.mascotasyvacunas.mascotas.domain.CreateMascotaUseCase
import proyectoFCT.mascotasyvacunas.mascotas.domain.DeleteMascotaUseCase
import proyectoFCT.mascotasyvacunas.mascotas.domain.GetAllMascotasUseCase
import proyectoFCT.mascotasyvacunas.mascotas.domain.UpdateMascotaUseCase
import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import javax.inject.Inject

@HiltViewModel
class MascotaViewModel @Inject constructor(
    private val getAllMascotasUseCase: GetAllMascotasUseCase,
    private val createMascotaUseCase: CreateMascotaUseCase,
    private val updateMascotaUseCase: UpdateMascotaUseCase,
    private val deleteMascotaUseCase: DeleteMascotaUseCase,
) : ViewModel() {

    private val _mascotas =
        MutableStateFlow<List<Mascota>>(emptyList())

    val mascotas = _mascotas.asStateFlow()

    private val _showDialog =
        MutableStateFlow(false)

    val showDialog = _showDialog.asStateFlow()

    private var idMascota: Long? = null

    private val _message =
        MutableStateFlow<String?>(null)

    val message = _message.asStateFlow()

    private val _showSuccessDialog =
        MutableStateFlow(false)

    val showSuccessDialog =
        _showSuccessDialog.asStateFlow()

    fun hideSuccessDialog() {
        _showSuccessDialog.value = false
    }

    init {
        cargarMascotas()
    }

    fun cargarMascotas() {

        viewModelScope.launch {

            val result = getAllMascotasUseCase()

            result.fold(

                onSuccess = {

                    _mascotas.value = it.map { mascotaResponse ->
                        mascotaResponse.toModel()
                    }
                },

                onFailure = {

                    _message.value = it.message
                }
            )
        }
    }

    fun setIdMascota(id: Long?) {
        idMascota = id
    }

    fun changeShowDialog() {
        _showDialog.value = !_showDialog.value
    }

    fun createMascota(
        mascota: Mascota
    ) {

        viewModelScope.launch {

            val result =
                createMascotaUseCase(mascota)

            result.fold(

                onSuccess = {

                    cargarMascotas()

                    _showSuccessDialog.value = true
                },

                onFailure = {

                    _message.value =
                        it.message
                            ?: "Error al registrar la mascota"
                }
            )
        }
    }

    fun updateMascota(
        id: Long,
        mascota: Mascota
    ) {

        viewModelScope.launch {

            val result =
                updateMascotaUseCase(
                    id = id,
                    mascota = mascota
                )

            result.fold(

                onSuccess = {

                    cargarMascotas()

                    _showSuccessDialog.value = true
                },

                onFailure = {

                    _message.value =
                        it.message
                            ?: "Error al actualizar la mascota"
                }
            )
        }
    }

    fun deleteMascota() {

        viewModelScope.launch {

            idMascota?.let { id ->

                val result =
                    deleteMascotaUseCase(id)

                result.fold(

                    onSuccess = {

                        cargarMascotas()

                        changeShowDialog()
                    },

                    onFailure = {

                        _message.value =
                            it.message
                                ?: "Error al eliminar la mascota"
                    }
                )
            }
        }
    }

}