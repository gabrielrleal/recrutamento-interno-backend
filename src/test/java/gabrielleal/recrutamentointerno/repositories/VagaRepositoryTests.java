package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.models.Vaga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VagaRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VagaRepository vagaRepository;

    @BeforeEach
    public void setUp() {
        Vaga vagaAtiva = new Vaga();
        vagaAtiva.setStatus(true);
        entityManager.persistAndFlush(vagaAtiva);

        Vaga vagaInativa = new Vaga();
        vagaInativa.setStatus(false);
        entityManager.persistAndFlush(vagaInativa);
    }

    @Test
    public void testFindByStatusTrue() {
        List<Vaga> vagasAtivas = vagaRepository.findByStatusTrue();
        assertNotNull(vagasAtivas);
        assertEquals(1, vagasAtivas.size());
        assertTrue(vagasAtivas.get(0).getStatus());
    }

    @Test
    public void testFindByStatusFalse() {
        List<Vaga> vagasInativas = vagaRepository.findByStatusFalse();
        assertNotNull(vagasInativas);
        assertEquals(1, vagasInativas.size());
        assertFalse(vagasInativas.get(0).getStatus());
    }
}