package proyecto.mascotasvacunas.repository;

import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.entity.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
 * Esta clase permite acceder a los datos de las vacunas
 * almacenados en la base de datos.
 */
public interface VacunaRepository extends JpaRepository<Vacuna, Long> {

    // Obtiene las vacunas que pertenecen a un usuario.
    List<Vacuna> findByUsuario(Usuario usuario);

}