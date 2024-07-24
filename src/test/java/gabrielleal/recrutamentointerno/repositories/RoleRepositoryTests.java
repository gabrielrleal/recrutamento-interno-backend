package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.enums.RoleEnum;
import gabrielleal.recrutamentointerno.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Usa o perfil de teste
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByNome() {
        Role role = new Role();
        role.setNome(RoleEnum.ADMINISTRADOR);
        roleRepository.save(role);

        Optional<Role> found = roleRepository.findByNome(RoleEnum.ADMINISTRADOR);
        assertTrue(found.isPresent());
        assertEquals(RoleEnum.ADMINISTRADOR, found.get().getNome());
    }

    @Test
    public void testExistsByNome() {
        Role role = new Role();
        role.setNome(RoleEnum.CANDIDATO);
        roleRepository.save(role);

        boolean exists = roleRepository.existsByNome(RoleEnum.CANDIDATO);
        assertTrue(exists);

        boolean notExists = roleRepository.existsByNome(RoleEnum.ADMINISTRADOR);
        assertFalse(notExists);
    }
}