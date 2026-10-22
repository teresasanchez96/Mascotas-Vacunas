package proyectoFCT.mascotasyvacunas.vacunaciones.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import proyectoFCT.mascotasyvacunas.vacunaciones.data.network.GetAllVacunacionesbyMascotaUseCase
import proyectoFCT.mascotasyvacunas.vacunaciones.domain.CreateVacunacionUseCase
import proyectoFCT.mascotasyvacunas.vacunaciones.domain.DeleteVacunacionUseCase
import proyectoFCT.mascotasyvacunas.vacunaciones.domain.UpdateVacunacionUseCase
import proyectoFCT.mascotasyvacunas.vacunaciones.ui.model.Vacunacion
import javax.inject.Inject

@HiltViewModel
class VacunacionViewModel @Inject constructor(
    private val getVacunacionesByMascotaUseCase: GetAllVacunacionesbyMascotaUseCase,
    private val createVacunacionUseCase: CreateVacunacionUseCase,
    private val updateVacunacionUseCase: UpdateVacunacionUseCase,
    private val deleteVacunacionUseCase: DeleteVacunacionUseCase
) : ViewModel() {

    private val _vacunaciones =
        MutableStateFlow<List<Vacunacion>>(emptyList())

    val vacunaciones = _vacunaciones.asStateFlow()

    private val _message =
        MutableStateFlow<String?>(null)

    val message =
        _message.asStateFlow()

    // Cargar las vacunaciones de una mascota.
    fun cargarVacunaciones(mascotaId: Long) {

        viewModelScope.launch {

            val result =
                getVacunacionesByMascotaUseCase(mascotaId)

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
    fun createVacunacion(
        mascotaId: Long,
        vacunacion: Vacunacion
    ) {

        viewModelScope.launch {

            val result =
                createVacunacionUseCase(
                    mascotaId,
                    vacunacion
                )

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
    fun updateVacunacion(
        mascotaId: Long,
        id: Long,
        vacunacion: Vacunacion
    ) {

        viewModelScope.launch {

            val result =
                updateVacunacionUseCase(
                    id,
                    vacunacion
                )

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
    fun deleteVacunacion(
        mascotaId: Long,
        id: Long
    ) {

        viewModelScope.launch {

            val result =
                deleteVacunacionUseCase(id)

            result.fold(

                onSuccess = {
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
}