package proyecto.mascotasvacunas.repository;

import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.entity.Vacunacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Esta clase permite acceder a los datos de las vacunaciones
 * almacenados en la base de datos.
 */
public interface VacunacionRepository extends JpaRepository<Vacunacion, Long> {

    // Obtiene las vacunaciones de las mascotas que pertenecen a un usuario.
    List<Vacunacion> findByMascotaUsuario(Usuario usuario);

}