package proyecto.mascotasvacunas.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import proyecto.mascotasvacunas.dto.CambiarPasswordRequest;
import proyecto.mascotasvacunas.dto.LoginRequest;
import proyecto.mascotasvacunas.dto.RegistroRequest;
import proyecto.mascotasvacunas.dto.UsuarioResponse;
import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.repository.UsuarioRepository;
import proyecto.mascotasvacunas.security.JwtService;

/*
 * Esta clase contiene la lógica relacionada con la autenticación.
 * Se encarga de registrar usuarios y comprobar sus credenciales
 * durante el inicio de sesión.
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Registra un nuevo usuario y cifra su contraseña.
    public Usuario registrarUsuario(RegistroRequest request) {

        // Comprueba que el email no esté registrado.
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El email ya está registrado.");
        }

        // Cifra la contraseña antes de guardarla.
        String passwordCifrada =
                passwordEncoder.encode(request.getPassword());

        Usuario usuario = new Usuario(
                request.getNombre(),
                request.getEmail(),
                passwordCifrada
        );

        return usuarioRepository.save(usuario);
    }

    // Comprueba las credenciales y genera el token JWT.
    public String login(LoginRequest request) {

        // Busca el usuario mediante su email.
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Email o contraseña incorrectos."));

        // Comprueba que la contraseña introducida sea correcta.
        if (!passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword())) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Email o contraseña incorrectos.");
        }

        // Genera el token JWT para el usuario.
        return jwtService.generarToken(usuario.getEmail());
    }
    // Obtiene los datos de la cuenta del usuario autenticado.
    public UsuarioResponse obtenerMiCuenta(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Usuario no encontrado."));

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail()
        );
    }

    // Cambia la contraseña del usuario autenticado.
    public void cambiarPassword(
            String email,
            CambiarPasswordRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Usuario no encontrado."));

        if (request.getPasswordActual() == null
                || !passwordEncoder.matches(
                request.getPasswordActual(),
                usuario.getPassword())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La contraseña actual no es correcta.");
        }

        if (request.getNuevaPassword() == null
                || request.getNuevaPassword().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La nueva contraseña no puede estar vacía.");
        }

        if (request.getNuevaPassword().length() < 8) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La nueva contraseña debe tener al menos 8 caracteres.");
        }

        if (!request.getNuevaPassword().equals(
                request.getConfirmarNuevaPassword())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La confirmación no coincide con la nueva contraseña.");
        }

        if (passwordEncoder.matches(
                request.getNuevaPassword(),
                usuario.getPassword())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La nueva contraseña debe ser diferente de la actual.");
        }

        usuario.setPassword(
                passwordEncoder.encode(request.getNuevaPassword()));

        usuarioRepository.save(usuario);
    }
}
