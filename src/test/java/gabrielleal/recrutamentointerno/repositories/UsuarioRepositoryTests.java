package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.models.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Usa o perfil de teste
public class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByEmail() {
        Usuario usuario = new Usuario();
        usuario.setNome("Test User"); // Adiciona o nome do usuário
        usuario.setEmail("test@example.com");
        usuario.setSenha("password");
        usuarioRepository.save(usuario);

        Usuario found = usuarioRepository.findByEmail("test@example.com");
        assertNotNull(found);
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    public void testExistsByEmail() {
        Usuario usuario = new Usuario();
        usuario.setNome("Test User");
        usuario.setEmail("test@example.com");
        usuario.setSenha("password");
        usuarioRepository.save(usuario);

        boolean exists = usuarioRepository.existsByEmail("test@example.com");
        assertTrue(exists);

        boolean notExists = usuarioRepository.existsByEmail("nonexistent@example.com");
        assertFalse(notExists);
    }
}