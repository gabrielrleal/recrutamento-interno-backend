package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.models.Candidatura;
import gabrielleal.recrutamentointerno.models.Vaga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CandidaturaRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    private Vaga vaga;
    private Candidato candidato;

    @BeforeEach
    public void setUp() {
        vaga = new Vaga();
        vaga.setStatus(true);
        entityManager.persistAndFlush(vaga);

        candidato = new Candidato();
        candidato.setNome("Test Candidato");
        candidato.setEmail("candidato@example.com");
        entityManager.persistAndFlush(candidato);

        Candidatura candidatura = new Candidatura();
        candidatura.setVaga(vaga);
        candidatura.setCandidato(candidato);
        entityManager.persistAndFlush(candidatura);
    }

    @Test
    public void testFindByVagaIdAndCandidatoId() {
        Candidatura candidatura = candidaturaRepository.findByVagaIdAndCandidatoId(vaga.getId(), candidato.getId());
        assertNotNull(candidatura);
        assertEquals(vaga.getId(), candidatura.getVaga().getId());
        assertEquals(candidato.getId(), candidatura.getCandidato().getId());
    }

    @Test
    public void testFindByVagaId() {
        List<Candidatura> candidaturas = candidaturaRepository.findByVagaId(vaga.getId());
        assertNotNull(candidaturas);
        assertEquals(1, candidaturas.size());
        assertEquals(vaga.getId(), candidaturas.get(0).getVaga().getId());
    }

    @Test
    public void testFindByCandidatoId() {
        List<Candidatura> candidaturas = candidaturaRepository.findByCandidatoId(candidato.getId());
        assertNotNull(candidaturas);
        assertEquals(1, candidaturas.size());
        assertEquals(candidato.getId(), candidaturas.get(0).getCandidato().getId());
    }
}