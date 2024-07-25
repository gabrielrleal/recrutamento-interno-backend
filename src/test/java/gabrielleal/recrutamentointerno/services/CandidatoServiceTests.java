package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.repositories.CandidatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CandidatoServiceTests {

    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private CandidatoService candidatoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodosCandidatos() {
        Candidato candidato1 = new Candidato();
        Candidato candidato2 = new Candidato();
        List<Candidato> candidatos = Arrays.asList(candidato1, candidato2);

        when(candidatoRepository.findAll()).thenReturn(candidatos);

        List<Candidato> result = candidatoService.listarTodosCandidatos();
        assertEquals(2, result.size());
        verify(candidatoRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarCandidatoPorId() {
        Candidato candidato = new Candidato();
        when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        Candidato result = candidatoService.buscarCandidatoPorId(1L);
        assertNotNull(result);
        verify(candidatoRepository, times(1)).findById(1L);
    }

    @Test
    public void testBuscarCandidatoPorId_NotFound() {
        when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());

        Candidato result = candidatoService.buscarCandidatoPorId(1L);
        assertNull(result);
        verify(candidatoRepository, times(1)).findById(1L);
    }

    @Test
    public void testCriarCandidato() {
        Candidato candidato = new Candidato();
        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);

        Candidato result = candidatoService.criarCandidato(candidato);
        assertNotNull(result);
        verify(candidatoRepository, times(1)).save(candidato);
    }

    @Test
    public void testAtualizarCandidato() {
        Candidato candidatoExistente = new Candidato();
        Candidato candidatoAtualizado = new Candidato();
        candidatoAtualizado.setNome("Nome Atualizado");
        candidatoAtualizado.setEmail("email@atualizado.com");

        when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidatoExistente));
        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidatoExistente);

        Candidato result = candidatoService.atualizarCandidato(1L, candidatoAtualizado);
        assertNotNull(result);
        assertEquals("Nome Atualizado", result.getNome());
        assertEquals("email@atualizado.com", result.getEmail());
        verify(candidatoRepository, times(1)).findById(1L);
        verify(candidatoRepository, times(1)).save(candidatoExistente);
    }

    @Test
    public void testAtualizarCandidato_NotFound() {
        Candidato candidatoAtualizado = new Candidato();
        when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());

        Candidato result = candidatoService.atualizarCandidato(1L, candidatoAtualizado);
        assertNull(result);
        verify(candidatoRepository, times(1)).findById(1L);
        verify(candidatoRepository, times(0)).save(any(Candidato.class));
    }

    @Test
    public void testDeletarCandidato() {
        doNothing().when(candidatoRepository).deleteById(1L);

        candidatoService.deletarCandidato(1L);
        verify(candidatoRepository, times(1)).deleteById(1L);
    }
}