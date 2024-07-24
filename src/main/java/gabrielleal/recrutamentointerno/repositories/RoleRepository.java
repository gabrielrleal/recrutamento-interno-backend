package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.enums.RoleEnum;
import gabrielleal.recrutamentointerno.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(RoleEnum nome);
    boolean existsByNome(RoleEnum nome);


}
