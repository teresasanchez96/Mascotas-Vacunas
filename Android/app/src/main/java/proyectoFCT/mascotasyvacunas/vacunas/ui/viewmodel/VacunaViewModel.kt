package proyectoFCT.mascotasyvacunas.vacunas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import proyectoFCT.mascotasyvacunas.vacunas.domain.CreateVacunaUseCase
import proyectoFCT.mascotasyvacunas.vacunas.domain.DeleteVacunaUseCase
import proyectoFCT.mascotasyvacunas.vacunas.domain.GetAllVacunasUseCase
import proyectoFCT.mascotasyvacunas.vacunas.ui.model.Vacuna
import javax.inject.Inject


@HiltViewModel
class VacunaViewModel @Inject constructor(
    private val getAllVacunasUseCase: GetAllVacunasUseCase,
    private val createVacunaUseCase: CreateVacunaUseCase,
    private val deleteVacunaUseCase: DeleteVacunaUseCase
) : ViewModel() {

    private val _vacunas = MutableStateFlow<List<Vacuna>>(emptyList())
    val vacunas = _vacunas.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()

    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog = _showSuccessDialog.asStateFlow()

    private var idVacuna: Long? = null

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    init {
        cargarVacunas()
    }

    // Carga todas las vacunas.
    fun cargarVacunas() {
        viewModelScope.launch {
            val result = getAllVacunasUseCase()

            result.fold(
                onSuccess = {
                    _vacunas.value = it.map { vacunaResponse ->
                        vacunaResponse.toModel()
                    }
                },
                onFailure = {
                    _message.value = it.message ?: "Error al cargar las vacunas"
                }
            )
        }
    }

    // Guarda el ID de la vacuna seleccionada.
    fun setIdVacuna(id: Long?) {
        idVacuna = id
    }

    // Muestra u oculta el diálogo de confirmación.
    fun changeShowDialog() {
        _showDialog.value = !_showDialog.value
    }

    // Oculta el diálogo de éxito.
    fun hideSuccessDialog() {
        _showSuccessDialog.value = false
    }

    // Limpia el mensaje de error.
    fun clearMessage() {
        _message.value = null
    }

    // Registra una nueva vacuna.
    fun createVacuna(vacuna: Vacuna) {
        viewModelScope.launch {
            val result = createVacunaUseCase(vacuna)

            result.fold(
                onSuccess = {
                    cargarVacunas()
                    _showSuccessDialog.value = true
                },
                onFailure = {
                    _message.value =
                        it.message ?: "Error al registrar la vacuna"
                }
            )
        }
    }

    // Elimina la vacuna seleccionada.
    fun deleteVacuna() {
        viewModelScope.launch {

            idVacuna?.let { id ->

                val result = deleteVacunaUseCase(id)

                result.fold(
                    onSuccess = {
                        cargarVacunas()
                        changeShowDialog()
                    },
                    onFailure = {
                        _message.value =
                            it.message ?: "Error al eliminar la vacuna"
                    }
                )
            }
        }
    }
}