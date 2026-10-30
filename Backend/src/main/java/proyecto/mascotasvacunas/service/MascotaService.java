package proyecto.mascotasvacunas.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import proyecto.mascotasvacunas.entity.Mascota;
import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.repository.MascotaRepository;
import proyecto.mascotasvacunas.repository.UsuarioRepository;

import java.io.IOException;
import java.util.List;

/*
 * Esta clase contiene la lógica relacionada con las mascotas.
 * Se encarga de consultar, crear, modificar y eliminar mascotas,
 * así como de actualizar sus fotografías.
 */
@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FotoMascotaService fotoMascotaService;

    public MascotaService(
            MascotaRepository mascotaRepository,
            UsuarioRepository usuarioRepository,
            FotoMascotaService fotoMascotaService) {
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
        this.fotoMascotaService = fotoMascotaService;
    }

    // Obtiene las mascotas del usuario que ha iniciado sesión.
    public List<Mascota> findAll() {
        Usuario usuario = obtenerUsuarioAutenticado();
        return mascotaRepository.findByUsuario(usuario);
    }

    // Busca una mascota comprobando que pertenece al usuario.
    public Mascota findById(Long id) {
        Usuario usuario = obtenerUsuarioAutenticado();

        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() ->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Mascota no encontrada"));

        if (mascota.getUsuario() == null
                || !mascota.getUsuario().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La mascota no pertenece al usuario");
        }

        return mascota;
    }

    // Guarda una nueva mascota y la asocia al usuario autenticado.
    public Mascota save(Mascota mascota) {
        Usuario usuario = obtenerUsuarioAutenticado();

        mascota.setUsuario(usuario);

        return mascotaRepository.save(mascota);
    }

    // Modifica los datos de una mascota sin alterar su fotografía.
    public Mascota update(Long id, Mascota mascotaDetails) {
        Mascota mascota = findById(id);

        mascota.setNombre(mascotaDetails.getNombre());
        mascota.setEspecie(mascotaDetails.getEspecie());
        mascota.setGenero(mascotaDetails.getGenero());
        mascota.setRaza(mascotaDetails.getRaza());
        mascota.setFechaNacimiento(mascotaDetails.getFechaNacimiento());
        mascota.setObservaciones(mascotaDetails.getObservaciones());

        return mascotaRepository.save(mascota);
    }

    // Actualiza la fotografía de una mascota del usuario autenticado.
    public Mascota actualizarFotoMascota(Long id, String nombreFotoMascota) {
        Mascota mascota = findById(id);

        mascota.setFotoMascota(nombreFotoMascota);

        return mascotaRepository.save(mascota);
    }

    // Elimina una mascota perteneciente al usuario y su fotografía, si tiene una.
    public void delete(Long id) {
        Mascota mascota = findById(id);
        String foto = mascota.getFotoMascota();

        mascotaRepository.delete(mascota);

        if (foto != null && !foto.isBlank()) {
            try {
                fotoMascotaService.eliminarFoto(foto);
            } catch (IOException e) {
                System.err.println(
                        "No se ha podido eliminar la fotografía de la mascota: "
                                + e.getMessage());
            }
        }
    }

    // Obtiene el usuario que ha iniciado sesión mediante JWT.
    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuario no autenticado");
        }

        String email = authentication.getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Usuario no encontrado"));
    }
}
