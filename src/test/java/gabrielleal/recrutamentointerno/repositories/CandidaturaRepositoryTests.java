package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.models.Candidatura;
import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.models.Vaga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Usa o perfil de teste
public class CandidaturaRepositoryTests {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private VagaRepository vagaRepository;

    private Candidato candidato;
    private Vaga vaga;

    @BeforeEach
    public void setUp() {
        candidato = new Candidato();
        candidato.setNome("Test Candidato");
        candidato.setEmail("candidato@example.com");
        candidato = candidatoRepository.save(candidato);

        vaga = new Vaga();
        vaga.setTitulo("Test Vaga");
        vaga.setDescricao("Descrição da Vaga");
        vaga = vagaRepository.save(vaga);
    }

    @Test
    public void testFindByVagaIdAndCandidatoId() {
        Candidatura candidatura = new Candidatura();
        candidatura.setCandidato(candidato);
        candidatura.setVaga(vaga);
        candidaturaRepository.save(candidatura);

        Candidatura found = candidaturaRepository.findByVagaIdAndCandidatoId(vaga.getId(), candidato.getId());
        assertNotNull(found);
        assertEquals(vaga.getId(), found.getVaga().getId());
        assertEquals(candidato.getId(), found.getCandidato().getId());
    }

    @Test
    public void testFindByVagaId() {
        Candidatura candidatura1 = new Candidatura();
        candidatura1.setCandidato(candidato);
        candidatura1.setVaga(vaga);
        candidaturaRepository.save(candidatura1);

        Candidato outroCandidato = new Candidato();
        outroCandidato.setNome("Outro Candidato");
        outroCandidato.setEmail("outro@example.com");
        outroCandidato = candidatoRepository.save(outroCandidato);

        Candidatura candidatura2 = new Candidatura();
        candidatura2.setCandidato(outroCandidato);
        candidatura2.setVaga(vaga);
        candidaturaRepository.save(candidatura2);

        List<Candidatura> candidaturas = candidaturaRepository.findByVagaId(vaga.getId());
        assertNotNull(candidaturas);
        assertEquals(2, candidaturas.size());
    }
}