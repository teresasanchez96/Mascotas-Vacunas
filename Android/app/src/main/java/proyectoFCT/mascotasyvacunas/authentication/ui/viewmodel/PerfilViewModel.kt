package proyectoFCT.mascotasyvacunas.authentication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import proyectoFCT.mascotasyvacunas.authentication.data.local.TokenManager
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _sesionCerrada = MutableStateFlow(false)
    val sesionCerrada = _sesionCerrada.asStateFlow()

    fun cerrarSesion() {
        viewModelScope.launch {
            tokenManager.eliminarToken()
            _sesionCerrada.value = true
        }
    }
}