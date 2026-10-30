package proyectoFCT.mascotasyvacunas.authentication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import proyectoFCT.mascotasyvacunas.authentication.data.network.CuentaRepository
import proyectoFCT.mascotasyvacunas.authentication.data.remote.CambiarPasswordRequest
import proyectoFCT.mascotasyvacunas.authentication.data.remote.UsuarioResponse
import javax.inject.Inject

@HiltViewModel
class CuentaViewModel @Inject constructor(
    private val cuentaRepository: CuentaRepository
) : ViewModel() {

    private val _usuario = MutableStateFlow<UsuarioResponse?>(null)
    val usuario = _usuario.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando = _cargando.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _cambiandoPassword = MutableStateFlow(false)
    val cambiandoPassword = _cambiandoPassword.asStateFlow()

    private val _mensajeExito = MutableStateFlow<String?>(null)
    val mensajeExito = _mensajeExito.asStateFlow()

    fun cargarMiCuenta() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null

            try {
                _usuario.value = cuentaRepository.obtenerMiCuenta()
            } catch (e: Exception) {
                _error.value = e.message
                    ?: "No se han podido cargar los datos de la cuenta."
            } finally {
                _cargando.value = false
            }
        }
    }

    fun mostrarError(mensaje: String) {
        _error.value = mensaje
        _mensajeExito.value = null
    }

    fun limpiarMensajes() {
        _error.value = null
        _mensajeExito.value = null
    }

    fun cambiarPassword(
        passwordActual: String,
        nuevaPassword: String,
        confirmarNuevaPassword: String
    ) {
        // Validaciones antes de enviar la petición.
        when {
            passwordActual.isBlank() -> {
                mostrarError("Introduce tu contraseña actual.")
                return
            }

            nuevaPassword.length < 8 -> {
                mostrarError(
                    "La nueva contraseña debe tener al menos 8 caracteres."
                )
                return
            }

            nuevaPassword == passwordActual -> {
                mostrarError(
                    "La nueva contraseña debe ser diferente de la actual."
                )
                return
            }

            nuevaPassword != confirmarNuevaPassword -> {
                mostrarError("Las nuevas contraseñas no coinciden.")
                return
            }
        }

        viewModelScope.launch {
            _error.value = null
            _mensajeExito.value = null
            _cambiandoPassword.value = true

            try {
                val request = CambiarPasswordRequest(
                    passwordActual = passwordActual,
                    nuevaPassword = nuevaPassword,
                    confirmarNuevaPassword = confirmarNuevaPassword
                )

                val resultado = cuentaRepository.cambiarPassword(request)

                resultado.fold(
                    onSuccess = {
                        _mensajeExito.value =
                            "La contraseña se ha cambiado correctamente."
                    },
                    onFailure = { error ->
                        _error.value = error.message
                            ?: "No se ha podido cambiar la contraseña."
                    }
                )
            } catch (e: Exception) {
                _error.value = e.message
                    ?: "Error de conexión con el servidor."
            } finally {
                _cambiandoPassword.value = false
            }
        }
    }
}
