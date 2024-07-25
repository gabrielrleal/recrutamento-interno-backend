package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setNome("Test User");
        usuario.setEmail("test@example.com");
        usuario.setSenha("password");
        entityManager.persistAndFlush(usuario);
    }

    @Test
    public void testFindByEmail() {
        Usuario found = usuarioRepository.findByEmail("test@example.com");
        assertNotNull(found);
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    public void testFindByEmail_NotFound() {
        Usuario found = usuarioRepository.findByEmail("notfound@example.com");
        assertNull(found);
    }

    @Test
    public void testExistsByEmail() {
        boolean exists = usuarioRepository.existsByEmail("test@example.com");
        assertTrue(exists);
    }

    @Test
    public void testExistsByEmail_NotFound() {
        boolean exists = usuarioRepository.existsByEmail("notfound@example.com");
        assertFalse(exists);
    }
}