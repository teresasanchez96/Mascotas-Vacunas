package proyecto.mascotasvacunas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.mascotasvacunas.entity.Mascota;
import proyecto.mascotasvacunas.entity.Usuario;

import java.util.List;
import java.util.Optional;

/*
 * Esta clase permite acceder a los datos de las mascotas
 * almacenados en la base de datos.
 */
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // Obtiene las mascotas que pertenecen a un usuario.
    List<Mascota> findByUsuario(Usuario usuario);

}