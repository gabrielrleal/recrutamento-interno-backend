package gabrielleal.recrutamentointerno.facades;

import gabrielleal.recrutamentointerno.dtos.CandidaturaDTO;
import gabrielleal.recrutamentointerno.models.Candidatura;
import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.models.Vaga;
import gabrielleal.recrutamentointerno.services.CandidaturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CandidaturaFacadeTests {

    @Mock
    private CandidaturaService candidaturaService;

    @InjectMocks
    private CandidaturaFacade candidaturaFacade;

    private Candidato candidato;
    private Vaga vaga;
    private Candidatura candidatura;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        candidato = new Candidato();
        candidato.setId(1L);
        candidato.setNome("Test Candidato");
        candidato.setEmail("candidato@example.com");

        vaga = new Vaga();
        vaga.setId(1L);
        vaga.setTitulo("Test Vaga");
        vaga.setDescricao("Descrição da Vaga");

        candidatura = new Candidatura();
        candidatura.setId(1L);
        candidatura.setCandidato(candidato);
        candidatura.setVaga(vaga);
    }

    @Test
    public void testListarTodasCandidaturas() {
        when(candidaturaService.listarTodasCandidaturas()).thenReturn(Arrays.asList(candidatura));

        List<CandidaturaDTO> candidaturas = candidaturaFacade.listarTodasCandidaturas();

        assertNotNull(candidaturas);
        assertEquals(1, candidaturas.size());
        assertEquals(1L, candidaturas.get(0).getId());
        assertEquals(1L, candidaturas.get(0).getVagaId());
        assertEquals(1L, candidaturas.get(0).getCandidatoId());
    }

    @Test
    public void testBuscarCandidaturaPorId() {
        when(candidaturaService.buscarCandidaturaPorId(1L)).thenReturn(candidatura);

        CandidaturaDTO candidaturaDTO = candidaturaFacade.buscarCandidaturaPorId(1L);

        assertNotNull(candidaturaDTO);
        assertEquals(1L, candidaturaDTO.getId());
        assertEquals(1L, candidaturaDTO.getVagaId());
        assertEquals(1L, candidaturaDTO.getCandidatoId());
    }

    @Test
    public void testBuscarCandidaturasPorVagaId() {
        when(candidaturaService.buscarCandidaturasPorVagaId(1L)).thenReturn(Arrays.asList(candidatura));

        List<CandidaturaDTO> candidaturas = candidaturaFacade.buscarCandidaturasPorVagaId(1L);

        assertNotNull(candidaturas);
        assertEquals(1, candidaturas.size());
        assertEquals(1L, candidaturas.get(0).getId());
        assertEquals(1L, candidaturas.get(0).getVagaId());
        assertEquals(1L, candidaturas.get(0).getCandidatoId());
    }

    @Test
    public void testCriarCandidatura() {
        when(candidaturaService.candidatarSeAVaga(1L, 1L)).thenReturn(candidatura);

        CandidaturaDTO candidaturaDTO = new CandidaturaDTO();
        candidaturaDTO.setVagaId(1L);
        candidaturaDTO.setCandidatoId(1L);

        CandidaturaDTO novaCandidaturaDTO = candidaturaFacade.criarCandidatura(candidaturaDTO);

        assertNotNull(novaCandidaturaDTO);
        assertEquals(1L, novaCandidaturaDTO.getId());
        assertEquals(1L, novaCandidaturaDTO.getVagaId());
        assertEquals(1L, novaCandidaturaDTO.getCandidatoId());
    }

    @Test
    public void testCriarCandidatura2() {
        when(candidaturaService.criarCandidatura(any(Candidatura.class))).thenReturn(candidatura);

        CandidaturaDTO candidaturaDTO = new CandidaturaDTO();
        candidaturaDTO.setId(1L);

        CandidaturaDTO novaCandidaturaDTO = candidaturaFacade.criarCandidatura2(candidaturaDTO);

        assertNotNull(novaCandidaturaDTO);
        assertEquals(1L, novaCandidaturaDTO.getId());
        assertEquals(1L, novaCandidaturaDTO.getVagaId());
        assertEquals(1L, novaCandidaturaDTO.getCandidatoId());
    }

    @Test
    public void testAtualizarCandidatura() {
        when(candidaturaService.buscarCandidaturaPorId(1L)).thenReturn(candidatura);
        when(candidaturaService.atualizarCandidatura(any(Candidatura.class))).thenReturn(candidatura);

        CandidaturaDTO candidaturaDTO = new CandidaturaDTO();
        candidaturaDTO.setId(1L);

        CandidaturaDTO candidaturaAtualizadaDTO = candidaturaFacade.atualizarCandidatura(1L, candidaturaDTO);

        assertNotNull(candidaturaAtualizadaDTO);
        assertEquals(1L, candidaturaAtualizadaDTO.getId());
        assertEquals(1L, candidaturaAtualizadaDTO.getVagaId());
        assertEquals(1L, candidaturaAtualizadaDTO.getCandidatoId());
    }

    @Test
    public void testDeletarCandidatura() {
        doNothing().when(candidaturaService).deletarCandidatura(1L);

        candidaturaFacade.deletarCandidatura(1L);

        verify(candidaturaService, times(1)).deletarCandidatura(1L);
    }
}