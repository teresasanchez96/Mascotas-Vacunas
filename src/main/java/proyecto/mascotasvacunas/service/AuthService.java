package proyecto.mascotasvacunas.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proyecto.mascotasvacunas.dto.LoginRequest;
import proyecto.mascotasvacunas.dto.RegistroRequest;
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
            throw new RuntimeException("El email ya está registrado");
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
                        new RuntimeException("Usuario no encontrado"));

        // Comprueba que la contraseña introducida sea correcta.
        if (!passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword())) {

            throw new RuntimeException("Contraseña incorrecta");
        }

        // Genera el token JWT para el usuario.
        return jwtService.generarToken(usuario.getEmail());
    }
}