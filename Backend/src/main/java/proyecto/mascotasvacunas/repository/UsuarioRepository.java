package proyecto.mascotasvacunas.repository;

import proyecto.mascotasvacunas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Esta clase permite acceder a los datos de los usuarios
 * almacenados en la base de datos.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca un usuario utilizando su email.
    Optional<Usuario> findByEmail(String email);
}
