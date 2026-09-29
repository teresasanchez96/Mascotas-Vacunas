package proyecto.mascotasvacunas.repository;

import proyecto.mascotasvacunas.entity.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Esta clase permite acceder a los datos de las vacunas
 * almacenados en la base de datos.
 */
public interface VacunaRepository extends JpaRepository<Vacuna, Long> {
}