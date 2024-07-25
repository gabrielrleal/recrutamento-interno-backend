package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.models.Candidatura;
import gabrielleal.recrutamentointerno.models.Vaga;
import gabrielleal.recrutamentointerno.repositories.CandidatoRepository;
import gabrielleal.recrutamentointerno.repositories.CandidaturaRepository;
import gabrielleal.recrutamentointerno.repositories.VagaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CandidaturaServiceTests {

    @Mock
    private CandidaturaRepository candidaturaRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private VagaRepository vagaRepository;

    @InjectMocks
    private CandidaturaService candidaturaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodasCandidaturas() {
        Candidatura candidatura1 = new Candidatura();
        Candidatura candidatura2 = new Candidatura();
        List<Candidatura> candidaturas = Arrays.asList(candidatura1, candidatura2);

        when(candidaturaRepository.findAll()).thenReturn(candidaturas);

        List<Candidatura> result = candidaturaService.listarTodasCandidaturas();
        assertEquals(2, result.size());
        verify(candidaturaRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarCandidaturaPorId() {
        Candidatura candidatura = new Candidatura();
        when(candidaturaRepository.findById(1L)).thenReturn(Optional.of(candidatura));

        Candidatura result = candidaturaService.buscarCandidaturaPorId(1L);
        assertNotNull(result);
        verify(candidaturaRepository, times(1)).findById(1L);
    }

    @Test
    public void testBuscarCandidaturaPorId_NotFound() {
        when(candidaturaRepository.findById(1L)).thenReturn(Optional.empty());

        Candidatura result = candidaturaService.buscarCandidaturaPorId(1L);
        assertNull(result);
        verify(candidaturaRepository, times(1)).findById(1L);
    }

    @Test
    public void testBuscarCandidaturasPorVagaId() {
        Candidatura candidatura1 = new Candidatura();
        Candidatura candidatura2 = new Candidatura();
        List<Candidatura> candidaturas = Arrays.asList(candidatura1, candidatura2);

        when(candidaturaRepository.findByVagaId(1L)).thenReturn(candidaturas);

        List<Candidatura> result = candidaturaService.buscarCandidaturasPorVagaId(1L);
        assertEquals(2, result.size());
        verify(candidaturaRepository, times(1)).findByVagaId(1L);
    }

    @Test
    public void testCriarCandidatura() {
        Candidatura candidatura = new Candidatura();
        Vaga vaga = new Vaga();
        Candidato candidato = new Candidato();
        candidatura.setVaga(vaga);
        candidatura.setCandidato(candidato);

        when(candidaturaRepository.save(any(Candidatura.class))).thenReturn(candidatura);

        Candidatura result = candidaturaService.criarCandidatura(candidatura);
        assertNotNull(result);
        assertNotNull(result.getDataCandidatura());
        verify(candidaturaRepository, times(1)).save(candidatura);
    }

    @Test
    public void testCriarCandidatura_NullVagaOrCandidato() {
        Candidatura candidatura = new Candidatura();

        assertThrows(IllegalArgumentException.class, () -> {
            candidaturaService.criarCandidatura(candidatura);
        });

        verify(candidaturaRepository, times(0)).save(any(Candidatura.class));
    }

    @Test
    public void testAtualizarCandidatura() {
        Candidatura candidatura = new Candidatura();
        when(candidaturaRepository.save(any(Candidatura.class))).thenReturn(candidatura);

        Candidatura result = candidaturaService.atualizarCandidatura(candidatura);
        assertNotNull(result);
        verify(candidaturaRepository, times(1)).save(candidatura);
    }

    @Test
    public void testDeletarCandidatura() {
        doNothing().when(candidaturaRepository).deleteById(1L);

        candidaturaService.deletarCandidatura(1L);
        verify(candidaturaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCandidatarSeAVaga() {
        Candidato candidato = new Candidato();
        Vaga vaga = new Vaga();
        when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));
        when(vagaRepository.findById(1L)).thenReturn(Optional.of(vaga));
        when(candidaturaRepository.save(any(Candidatura.class))).thenReturn(new Candidatura());

        Candidatura result = candidaturaService.candidatarSeAVaga(1L, 1L);
        assertNotNull(result);
        verify(candidatoRepository, times(1)).findById(1L);
        verify(vagaRepository, times(1)).findById(1L);
        verify(candidaturaRepository, times(1)).save(any(Candidatura.class));
    }

    @Test
    public void testCandidatarSeAVaga_CandidatoOrVagaNotFound() {
        when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());
        when(vagaRepository.findById(1L)).thenReturn(Optional.empty());

        Candidatura result = candidaturaService.candidatarSeAVaga(1L, 1L);
        assertNull(result);
        verify(candidatoRepository, times(1)).findById(1L);
        verify(vagaRepository, times(1)).findById(1L);
        verify(candidaturaRepository, times(0)).save(any(Candidatura.class));
    }

    @Test
    public void testBuscarCandidaturaPorVagaECandidato() {
        Candidatura candidatura = new Candidatura();
        when(candidaturaRepository.findByVagaIdAndCandidatoId(1L, 1L)).thenReturn(candidatura);

        Candidatura result = candidaturaService.buscarCandidaturaPorVagaECandidato(1L, 1L);
        assertNotNull(result);
        verify(candidaturaRepository, times(1)).findByVagaIdAndCandidatoId(1L, 1L);
    }

    @Test
    public void testBuscarCandidaturasPorCandidatoId() {
        Candidatura candidatura1 = new Candidatura();
        Candidatura candidatura2 = new Candidatura();
        List<Candidatura> candidaturas = Arrays.asList(candidatura1, candidatura2);

        when(candidaturaRepository.findByCandidatoId(1L)).thenReturn(candidaturas);

        List<Candidatura> result = candidaturaService.buscarCandidaturasPorCandidatoId(1L);
        assertEquals(2, result.size());
        verify(candidaturaRepository, times(1)).findByCandidatoId(1L);
    }
}