package proyectoFCT.mascotasyvacunas.authentication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import proyectoFCT.mascotasyvacunas.authentication.domain.LoginUseCase
import proyectoFCT.mascotasyvacunas.authentication.domain.RegistroUseCase
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registroUseCase: RegistroUseCase
) : ViewModel() {

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                loginUseCase(
                    email = email,
                    password = password
                )

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Error al iniciar sesión"
                )
            }
        }
    }

    fun registrar(
        nombre: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                registroUseCase(
                    nombre = nombre,
                    email = email,
                    password = password
                )

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Error al crear la cuenta"
                )
            }
        }
    }
}