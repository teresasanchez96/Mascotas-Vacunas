package proyecto.mascotasvacunas.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Vacunación no encontrada"));

        if (vacunacion.getMascota() == null ||
                vacunacion.getMascota().getUsuario() == null ||
                !vacunacion.getMascota().getUsuario().getId()
                        .equals(usuario.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La vacunación no pertenece al usuario");
        }

        return vacunacion;
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

        // Actualizar la vacuna asociada si se ha seleccionado una.
        if (vacunacionDetails.getVacuna() != null
                && vacunacionDetails.getVacuna().getId() != null) {

            Vacuna vacuna = buscarVacunaDelUsuario(
                    vacunacionDetails.getVacuna().getId(),
                    obtenerUsuarioAutenticado()
            );

            vacunacion.setVacuna(vacuna);
        }

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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Mascota no encontrada"));

        // Comprobar que la mascota pertenece al usuario.
        if (mascota.getUsuario() == null ||
                !mascota.getUsuario().getId().equals(usuario.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La mascota no pertenece al usuario");
        }

        // Comprobar que se ha indicado una vacuna.
        if (vacunacion.getVacuna() == null ||
                vacunacion.getVacuna().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La vacuna es obligatoria");
        }

        // Buscar la vacuna comprobando que pertenece al usuario.
        Vacuna vacuna = buscarVacunaDelUsuario(
                vacunacion.getVacuna().getId(),
                usuario
        );

        // Asociar las entidades reales.
        vacunacion.setMascota(mascota);
        vacunacion.setVacuna(vacuna);

        return vacunacionRepository.save(vacunacion);
    }

    // Obtiene las vacunaciones de una mascota perteneciente al usuario.
    public List<Vacunacion> findByMascota(Long mascotaId) {

        Usuario usuario = obtenerUsuarioAutenticado();

        // Comprobar que la mascota existe y pertenece al usuario.
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Mascota no encontrada"));

        if (mascota.getUsuario() == null
                || !mascota.getUsuario().getId().equals(usuario.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Mascota no encontrada");
        }

        return vacunacionRepository
                .findByMascotaUsuario(usuario)
                .stream()
                .filter(vacunacion ->
                        vacunacion.getMascota().getId().equals(mascotaId))
                .toList();
    }

    // Busca una vacuna y comprueba que pertenece al usuario.
    private Vacuna buscarVacunaDelUsuario(Long vacunaId, Usuario usuario) {

        Vacuna vacuna = vacunaRepository.findById(vacunaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Vacuna no encontrada"));

        if (vacuna.getUsuario() == null
                || !vacuna.getUsuario().getId().equals(usuario.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Vacuna no encontrada");
        }

        return vacuna;
    }

    // Obtiene el usuario que ha iniciado sesión.
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

    // Actualiza la fotografía de la etiqueta de una vacunación.
    public Vacunacion actualizarFotoEtiqueta(Long id, String nombreFoto) {
        Vacunacion vacunacion = findById(id);
        vacunacion.setFotoEtiqueta(nombreFoto);
        return vacunacionRepository.save(vacunacion);
    }
}