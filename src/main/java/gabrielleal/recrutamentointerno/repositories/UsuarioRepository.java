package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email); // Método para buscar usuário por email
    boolean existsByEmail(String email); // Método para verificar a existência de um usuário por email


}
