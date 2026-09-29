package proyecto.mascotasvacunas.repository;

import proyecto.mascotasvacunas.entity.Vacunacion;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Esta clase permite acceder a los datos de las vacunaciones
 * almacenados en la base de datos.
 */
public interface VacunacionRepository extends JpaRepository<Vacunacion, Long> {
}