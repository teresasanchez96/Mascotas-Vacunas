package proyecto.mascotasvacunas.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import proyecto.mascotasvacunas.dto.*;
import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.service.AuthService;

/*
 * Esta clase controla las peticiones relacionadas con la autenticación
 * y la gestión de la cuenta de los usuarios.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Registra un nuevo usuario en la aplicación.
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(
            @Valid @RequestBody RegistroRequest request) {

        Usuario usuario = authService.registrarUsuario(request);

        return ResponseEntity.ok(usuario);
    }

    // Comprueba las credenciales y devuelve el token JWT.
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    // Devuelve los datos del usuario autenticado.
    @GetMapping("/mi-cuenta")
    public ResponseEntity<UsuarioResponse> obtenerMiCuenta(
            Authentication authentication) {

        UsuarioResponse respuesta =
                authService.obtenerMiCuenta(authentication.getName());

        return ResponseEntity.ok(respuesta);
    }

    // Cambia la contraseña del usuario autenticado.
    @PutMapping("/cambiar-password")
    public ResponseEntity<String> cambiarPassword(
            @RequestBody CambiarPasswordRequest request,
            Authentication authentication) {

        authService.cambiarPassword(
                authentication.getName(),
                request
        );

        return ResponseEntity.ok(
                "Contraseña actualizada correctamente");
    }
}
