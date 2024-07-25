package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.enums.RoleEnum;
import gabrielleal.recrutamentointerno.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RoleRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        Role roleAdmin = new Role();
        roleAdmin.setNome(RoleEnum.ADMINISTRADOR);
        entityManager.persistAndFlush(roleAdmin);

        Role roleUser = new Role();
        roleUser.setNome(RoleEnum.CANDIDATO);
        entityManager.persistAndFlush(roleUser);
    }

    @Test
    public void testFindByNome() {
        Optional<Role> role = roleRepository.findByNome(RoleEnum.ADMINISTRADOR);
        assertTrue(role.isPresent());
        assertEquals(RoleEnum.ADMINISTRADOR, role.get().getNome());
    }


    @Test
    public void testExistsByNome() {
        boolean exists = roleRepository.existsByNome(RoleEnum.ADMINISTRADOR);
        assertTrue(exists);
    }
}
