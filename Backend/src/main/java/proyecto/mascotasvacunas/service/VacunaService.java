package proyecto.mascotasvacunas.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.entity.Vacuna;
import proyecto.mascotasvacunas.repository.UsuarioRepository;
import proyecto.mascotasvacunas.repository.VacunaRepository;
import proyecto.mascotasvacunas.repository.VacunacionRepository;

import java.util.List;

/*
 * Esta clase contiene la lógica relacionada con las vacunas.
 * Se encarga de consultar, crear, modificar y eliminar vacunas.
 */
@Service
public class VacunaService {

    private final VacunaRepository vacunaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VacunacionRepository vacunacionRepository;

    public VacunaService(VacunaRepository vacunaRepository,
                         UsuarioRepository usuarioRepository,
                         VacunacionRepository vacunacionRepository) {
        this.vacunaRepository = vacunaRepository;
        this.usuarioRepository = usuarioRepository;
        this.vacunacionRepository = vacunacionRepository;
    }

    // Obtiene las vacunas del usuario que ha iniciado sesión.
    public List<Vacuna> findAll() {

        Usuario usuario = obtenerUsuarioAutenticado();

        return vacunaRepository.findByUsuario(usuario);
    }

    // Busca una vacuna por su ID comprobando que pertenece al usuario.
    public Vacuna findById(Long id) {

        Usuario usuario = obtenerUsuarioAutenticado();

        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Vacuna no encontrada"));

        if (vacuna.getUsuario() == null ||
                !vacuna.getUsuario().getId().equals(usuario.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La vacuna no pertenece al usuario");
        }

        return vacuna;
    }

    // Guarda una nueva vacuna asociándola al usuario que ha iniciado sesión.
    public Vacuna save(Vacuna vacuna) {

        Usuario usuario = obtenerUsuarioAutenticado();

        vacuna.setUsuario(usuario);

        return vacunaRepository.save(vacuna);
    }

    // Modifica una vacuna perteneciente al usuario.
    public Vacuna update(Long id, Vacuna vacunaDetails) {

        Vacuna vacuna = findById(id);

        vacuna.setNombreVacuna(vacunaDetails.getNombreVacuna());

        return vacunaRepository.save(vacuna);
    }

    // Elimina una vacuna perteneciente al usuario si no está en uso.
    public void delete(Long id) {

        Vacuna vacuna = findById(id);

        // Comprueba si la vacuna está asociada a alguna vacunación.
        if (vacunacionRepository.existsByVacuna_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar esta vacuna porque está asociada "
                            + "a una o varias vacunaciones de tus mascotas.");
        }

        vacunaRepository.delete(vacuna);
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
}