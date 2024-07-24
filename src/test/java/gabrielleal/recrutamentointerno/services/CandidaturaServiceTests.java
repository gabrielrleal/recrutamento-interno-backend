package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.models.Candidatura;
import gabrielleal.recrutamentointerno.models.Vaga;
import gabrielleal.recrutamentointerno.repositories.CandidatoRepository;
import gabrielleal.recrutamentointerno.repositories.CandidaturaRepository;
import gabrielleal.recrutamentointerno.repositories.VagaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CandidaturaServiceTests {

    @Mock
    private CandidaturaRepository candidaturaRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private VagaRepository vagaRepository;

    @InjectMocks
    private CandidaturaService candidaturaService;

    @Test
    public void testListarTodasCandidaturas() {
        Candidatura candidatura1 = new Candidatura();
        candidatura1.setId(1L);
        Candidatura candidatura2 = new Candidatura();
        candidatura2.setId(2L);

        List<Candidatura> listaCandidaturas = Arrays.asList(candidatura1, candidatura2);
        when(candidaturaRepository.findAll()).thenReturn(listaCandidaturas);

        List<Candidatura> resultado = candidaturaService.listarTodasCandidaturas();

        assertEquals(2, resultado.size());
        verify(candidaturaRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarCandidaturaPorId_Existente() {
        Long id = 1L;
        Candidatura candidatura = new Candidatura();
        candidatura.setId(id);

        when(candidaturaRepository.findById(id)).thenReturn(Optional.of(candidatura));

        Candidatura resultado = candidaturaService.buscarCandidaturaPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(candidaturaRepository, times(1)).findById(id);
    }

    @Test
    public void testBuscarCandidaturaPorId_NaoExistente() {
        Long id = 1L;
        when(candidaturaRepository.findById(id)).thenReturn(Optional.empty());

        Candidatura resultado = candidaturaService.buscarCandidaturaPorId(id);

        assertNull(resultado);
        verify(candidaturaRepository, times(1)).findById(id);
    }

    @Test
    public void testCriarCandidatura() {
        Candidatura candidatura = new Candidatura();
        candidatura.setId(1L);
        candidatura.setDataCandidatura(LocalDateTime.now());

        when(candidaturaRepository.save(any(Candidatura.class))).thenReturn(candidatura);

        Candidatura resultado = candidaturaService.criarCandidatura(candidatura);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertNotNull(resultado.getDataCandidatura());
        verify(candidaturaRepository, times(1)).save(any(Candidatura.class));
    }

    @Test
    public void testAtualizarCandidatura() {
        Candidatura candidaturaAtualizada = new Candidatura();
        candidaturaAtualizada.setId(1L);

        when(candidaturaRepository.save(any(Candidatura.class))).thenReturn(candidaturaAtualizada);

        Candidatura resultado = candidaturaService.atualizarCandidatura(candidaturaAtualizada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(candidaturaRepository, times(1)).save(any(Candidatura.class));
    }

    @Test
    public void testDeletarCandidatura() {
        Long id = 1L;
        candidaturaService.deletarCandidatura(id);
        verify(candidaturaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testCandidatarSeAVaga_Sucesso() {
        Long vagaId = 1L;
        Long candidatoId = 1L;

        Candidato candidato = new Candidato();
        candidato.setId(candidatoId);

        Vaga vaga = new Vaga();
        vaga.setId(vagaId);

        when(candidatoRepository.findById(candidatoId)).thenReturn(Optional.of(candidato));
        when(vagaRepository.findById(vagaId)).thenReturn(Optional.of(vaga));
        when(candidaturaRepository.save(any(Candidatura.class))).thenAnswer(invocation -> {
            Candidatura c = invocation.getArgument(0);
            c.setId(1L);
            c.setDataCandidatura(LocalDateTime.now());
            return c;
        });

        Candidatura resultado = candidaturaService.candidatarSeAVaga(vagaId, candidatoId);

        assertNotNull(resultado);
        assertEquals(vaga, resultado.getVaga());
        assertEquals(candidato, resultado.getCandidato());
        assertNotNull(resultado.getDataCandidatura());
    }

    @Test
    public void testCandidatarSeAVaga_CandidatoOuVagaNaoEncontrado() {
        Long vagaId = 1L;
        Long candidatoId = 1L;

        when(candidatoRepository.findById(candidatoId)).thenReturn(Optional.empty());
        when(vagaRepository.findById(vagaId)).thenReturn(Optional.empty());

        Candidatura resultado = candidaturaService.candidatarSeAVaga(vagaId, candidatoId);

        assertNull(resultado);
    }

    @Test
    public void testBuscarCandidaturaPorVagaECandidato() {
        Long vagaId = 1L;
        Long candidatoId = 1L;
        Candidatura candidatura = new Candidatura();
        candidatura.setId(1L);

        when(candidaturaRepository.findByVagaIdAndCandidatoId(vagaId, candidatoId)).thenReturn(candidatura);

        Candidatura resultado = candidaturaService.buscarCandidaturaPorVagaECandidato(vagaId, candidatoId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(candidaturaRepository, times(1)).findByVagaIdAndCandidatoId(vagaId, candidatoId);
    }
}
