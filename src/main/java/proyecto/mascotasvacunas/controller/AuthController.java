package proyecto.mascotasvacunas.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.mascotasvacunas.dto.LoginRequest;
import proyecto.mascotasvacunas.dto.LoginResponse;
import proyecto.mascotasvacunas.dto.RegistroRequest;
import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.service.AuthService;

/*
 * Esta clase controla las peticiones relacionadas con la autenticación
 * de los usuarios, como el registro y el inicio de sesión.
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
}