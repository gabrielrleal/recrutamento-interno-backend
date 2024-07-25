package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.exceptions.VagaNaoEncontradaException;
import gabrielleal.recrutamentointerno.models.Vaga;
import gabrielleal.recrutamentointerno.repositories.VagaRepository;
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

public class VagaServiceTests {

    @Mock
    private VagaRepository vagaRepository;

    @InjectMocks
    private VagaService vagaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodasVagas() {
        Vaga vaga1 = new Vaga();
        Vaga vaga2 = new Vaga();
        List<Vaga> vagas = Arrays.asList(vaga1, vaga2);

        when(vagaRepository.findAll()).thenReturn(vagas);

        List<Vaga> result = vagaService.listarTodasVagas();
        assertEquals(2, result.size());
        verify(vagaRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarVagaPorId() {
        Vaga vaga = new Vaga();
        when(vagaRepository.findById(1L)).thenReturn(Optional.of(vaga));

        Vaga result = vagaService.buscarVagaPorId(1L);
        assertNotNull(result);
        verify(vagaRepository, times(1)).findById(1L);
    }

    @Test
    public void testBuscarVagaPorId_NotFound() {
        when(vagaRepository.findById(1L)).thenReturn(Optional.empty());

        Vaga result = vagaService.buscarVagaPorId(1L);
        assertNull(result);
        verify(vagaRepository, times(1)).findById(1L);
    }

    @Test
    public void testCriarVaga() {
        Vaga vaga = new Vaga();
        when(vagaRepository.save(any(Vaga.class))).thenReturn(vaga);

        Vaga result = vagaService.criarVaga(vaga);
        assertNotNull(result);
        verify(vagaRepository, times(1)).save(vaga);
    }

    @Test
    public void testAtualizarVaga() {
        Vaga vagaExistente = new Vaga();
        Vaga vagaAtualizada = new Vaga();
        when(vagaRepository.findById(1L)).thenReturn(Optional.of(vagaExistente));
        when(vagaRepository.save(any(Vaga.class))).thenReturn(vagaExistente);

        Vaga result = vagaService.atualizarVaga(1L, vagaAtualizada);
        assertNotNull(result);
        verify(vagaRepository, times(1)).findById(1L);
        verify(vagaRepository, times(1)).save(vagaExistente);
    }

    @Test
    public void testAtualizarVaga_NotFound() {
        Vaga vagaAtualizada = new Vaga();
        when(vagaRepository.findById(1L)).thenReturn(Optional.empty());

        Vaga result = vagaService.atualizarVaga(1L, vagaAtualizada);
        assertNull(result);
        verify(vagaRepository, times(1)).findById(1L);
        verify(vagaRepository, times(0)).save(any(Vaga.class));
    }

    @Test
    public void testDeletarVaga() {
        doNothing().when(vagaRepository).deleteById(1L);

        vagaService.deletarVaga(1L);
        verify(vagaRepository, times(1)).deleteById(1L);
    }
}