package proyecto.mascotasvacunas.repository;

import proyecto.mascotasvacunas.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Esta clase permite acceder a los datos de las mascotas
 * almacenados en la base de datos.
 */
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}