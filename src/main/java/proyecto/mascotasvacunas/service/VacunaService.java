package proyecto.mascotasvacunas.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.entity.Vacuna;
import proyecto.mascotasvacunas.repository.UsuarioRepository;
import proyecto.mascotasvacunas.repository.VacunaRepository;

import java.util.List;

/*
 * Esta clase contiene la lógica relacionada con las vacunas.
 * Se encarga de consultar, crear, modificar y eliminar vacunas.
 */
@Service
public class VacunaService {

    private final VacunaRepository vacunaRepository;
    private final UsuarioRepository usuarioRepository;

    public VacunaService(VacunaRepository vacunaRepository,
                         UsuarioRepository usuarioRepository) {
        this.vacunaRepository = vacunaRepository;
        this.usuarioRepository = usuarioRepository;
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
                .orElseThrow(() ->
                        new RuntimeException("Vacuna no encontrada"));

        if (vacuna.getUsuario() == null ||
                !vacuna.getUsuario().getId().equals(usuario.getId())) {

            throw new RuntimeException(
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

    // Elimina una vacuna perteneciente al usuario.
    public void delete(Long id) {

        Vacuna vacuna = findById(id);

        vacunaRepository.delete(vacuna);
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