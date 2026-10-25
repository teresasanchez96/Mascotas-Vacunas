package proyecto.mascotasvacunas.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import proyecto.mascotasvacunas.entity.Mascota;
import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.repository.MascotaRepository;
import proyecto.mascotasvacunas.repository.UsuarioRepository;

import java.util.List;

/*
 * Esta clase contiene la lógica relacionada con las mascotas.
 * Se encarga de consultar, crear, modificar y eliminar mascotas.
 */
@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

    public MascotaService(MascotaRepository mascotaRepository,
                          UsuarioRepository usuarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Obtiene las mascotas del usuario que ha iniciado sesión.
    public List<Mascota> findAll() {

        Usuario usuario = obtenerUsuarioAutenticado();

        return mascotaRepository.findByUsuario(usuario);
    }

    // Busca una mascota por su ID comprobando que pertenece al usuario.
    public Mascota findById(Long id) {

        Usuario usuario = obtenerUsuarioAutenticado();

        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mascota no encontrada"));

        if (mascota.getUsuario() == null ||
                !mascota.getUsuario().getId().equals(usuario.getId())) {

            throw new RuntimeException(
                    "La mascota no pertenece al usuario");
        }

        return mascota;
    }

    // Guarda una nueva mascota asociándola al usuario que ha iniciado sesión.
    public Mascota save(Mascota mascota) {

        Usuario usuario = obtenerUsuarioAutenticado();

        mascota.setUsuario(usuario);

        return mascotaRepository.save(mascota);
    }

    // Modifica los datos de una mascota perteneciente al usuario.
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

    // Elimina una mascota perteneciente al usuario.
    public void delete(Long id) {

        Mascota mascota = findById(id);

        mascotaRepository.delete(mascota);
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