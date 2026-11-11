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
import proyecto.mascotasvacunas.repository.VacunacionRepository;

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
    private final VacunacionRepository vacunacionRepository;

    public MascotaService(
            MascotaRepository mascotaRepository,
            UsuarioRepository usuarioRepository,
            FotoMascotaService fotoMascotaService,
            VacunacionRepository vacunacionRepository) {
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
        this.fotoMascotaService = fotoMascotaService;
        this.vacunacionRepository = vacunacionRepository;
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

        String fotoAnterior = mascota.getFotoMascota();

        mascota.setFotoMascota(nombreFotoMascota);

        Mascota mascotaActualizada =  mascotaRepository.save(mascota);

        // Elimina la fotografía anterior si se ha guardado la nueva correctamente.
        if (fotoAnterior != null
                && !fotoAnterior.isBlank()
                && !fotoAnterior.equals(nombreFotoMascota)) {

            try {
                fotoMascotaService.eliminarFoto(fotoAnterior);
            } catch (IOException e) {
                System.err.println(
                        "No se ha podido eliminar la fotografía anterior: "
                                + e.getMessage());
            }
        }

        return mascotaActualizada;
    }

    // Elimina la fotografía de una mascota sin eliminar la mascota.
    public void eliminarFotoMascota(Long id) {
        Mascota mascota = findById(id);

        String foto = mascota.getFotoMascota();

        if (foto == null || foto.isBlank()) {
            return;
        }

        mascota.setFotoMascota(null);

        mascotaRepository.save(mascota);

        try {
            fotoMascotaService.eliminarFoto(foto);
        } catch (IOException e) {
            System.err.println(
                    "No se ha podido eliminar la fotografía: "
                            + e.getMessage());
        }
    }

    // Elimina una mascota perteneciente al usuario y su fotografía, si tiene una.
    public void delete(Long id) {
        Mascota mascota = findById(id);
        String foto = mascota.getFotoMascota();

        // Primero elimina todas las vacunaciones de esa mascota.
        vacunacionRepository.deleteByMascota_Id(id);

        // Después elimina la mascota.
        mascotaRepository.delete(mascota);

        // Elimina la fotografía de la mascota, si existe.
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
