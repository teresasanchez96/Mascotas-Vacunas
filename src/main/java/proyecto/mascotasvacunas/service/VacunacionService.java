package proyecto.mascotasvacunas.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import proyecto.mascotasvacunas.entity.Mascota;
import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.entity.Vacuna;
import proyecto.mascotasvacunas.entity.Vacunacion;
import proyecto.mascotasvacunas.repository.MascotaRepository;
import proyecto.mascotasvacunas.repository.UsuarioRepository;
import proyecto.mascotasvacunas.repository.VacunaRepository;
import proyecto.mascotasvacunas.repository.VacunacionRepository;

import java.util.List;

/*
 * Esta clase contiene la lógica relacionada con las vacunaciones.
 */
@Service
public class VacunacionService {

    private final VacunacionRepository vacunacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;
    private final VacunaRepository vacunaRepository;

    public VacunacionService(VacunacionRepository vacunacionRepository,
                             UsuarioRepository usuarioRepository,
                             MascotaRepository mascotaRepository,
                             VacunaRepository vacunaRepository) {
        this.vacunacionRepository = vacunacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.mascotaRepository = mascotaRepository;
        this.vacunaRepository = vacunaRepository;
    }

    // Obtiene las vacunaciones de las mascotas del usuario que ha iniciado sesión.
    public List<Vacunacion> findAll() {

        Usuario usuario = obtenerUsuarioAutenticado();

        return vacunacionRepository.findByMascotaUsuario(usuario);
    }

    // Busca una vacunación comprobando que pertenece al usuario.
    public Vacunacion findById(Long id) {

        Usuario usuario = obtenerUsuarioAutenticado();

        Vacunacion vacunacion = vacunacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vacunación no encontrada"));

        if (vacunacion.getMascota() == null ||
                vacunacion.getMascota().getUsuario() == null ||
                !vacunacion.getMascota().getUsuario().getId()
                        .equals(usuario.getId())) {

            throw new RuntimeException(
                    "La vacunación no pertenece al usuario");
        }

        return vacunacion;
    }

    // Guarda una vacunación.
    public Vacunacion save(Vacunacion vacunacion) {
        return vacunacionRepository.save(vacunacion);
    }

    // Actualiza una vacunación perteneciente al usuario.
    public Vacunacion update(Long id, Vacunacion vacunacionDetails) {

        Vacunacion vacunacion = findById(id);

        vacunacion.setFechaPrevista(
                vacunacionDetails.getFechaPrevista()
        );

        vacunacion.setVacunaAdministrada(
                vacunacionDetails.getVacunaAdministrada()
        );

        vacunacion.setFechaAdministracion(
                vacunacionDetails.getFechaAdministracion()
        );

        return vacunacionRepository.save(vacunacion);
    }

    // Elimina una vacunación perteneciente al usuario.
    public void delete(Long id) {

        Vacunacion vacunacion = findById(id);

        vacunacionRepository.delete(vacunacion);
    }

    // Crea una vacunación comprobando que la mascota pertenece al usuario.
    public Vacunacion crear(Long mascotaId, Vacunacion vacunacion) {

        Usuario usuario = obtenerUsuarioAutenticado();

        // Buscar la mascota en la base de datos.
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() ->
                        new RuntimeException("Mascota no encontrada"));

        // Comprobar que la mascota pertenece al usuario.
        if (mascota.getUsuario() == null ||
                !mascota.getUsuario().getId().equals(usuario.getId())) {

            throw new RuntimeException(
                    "La mascota no pertenece al usuario");
        }

        // Comprobar que se ha indicado una vacuna.
        if (vacunacion.getVacuna() == null ||
                vacunacion.getVacuna().getId() == null) {

            throw new RuntimeException(
                    "La vacuna es obligatoria");
        }

        // Buscar la vacuna real en la base de datos.
        Vacuna vacuna = vacunaRepository.findById(
                vacunacion.getVacuna().getId()
        ).orElseThrow(() ->
                new RuntimeException("Vacuna no encontrada"));

        // Asociar las entidades reales.
        vacunacion.setMascota(mascota);
        vacunacion.setVacuna(vacuna);

        return vacunacionRepository.save(vacunacion);
    }

    // Obtiene las vacunaciones de una mascota perteneciente al usuario.
    public List<Vacunacion> findByMascota(Long mascotaId) {

        Usuario usuario = obtenerUsuarioAutenticado();

        return vacunacionRepository
                .findByMascotaUsuario(usuario)
                .stream()
                .filter(vacunacion ->
                        vacunacion.getMascota().getId().equals(mascotaId))
                .toList();
    }

    // Obtiene el usuario que ha iniciado sesión.
    private Usuario obtenerUsuarioAutenticado() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));
    }
}