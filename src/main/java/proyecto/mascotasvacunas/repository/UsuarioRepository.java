package proyecto.mascotasvacunas.repository;

import proyecto.mascotasvacunas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Esta clase permite acceder a los datos de los usuarios
 * almacenados en la base de datos.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}