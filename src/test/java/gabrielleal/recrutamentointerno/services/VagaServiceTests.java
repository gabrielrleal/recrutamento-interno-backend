package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.models.Vaga;
import gabrielleal.recrutamentointerno.repositories.VagaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VagaServiceTests {

    @Mock
    private VagaRepository vagaRepository;

    @InjectMocks
    private VagaService vagaService;

    @Test
    public void testListarTodasVagas() {
        Vaga vaga1 = new Vaga();
        vaga1.setId(1L);
        vaga1.setTitulo("Desenvolvedor Java");

        Vaga vaga2 = new Vaga();
        vaga2.setId(2L);
        vaga2.setTitulo("Desenvolvedor Front-end");

        List<Vaga> listaVagas = Arrays.asList(vaga1, vaga2);
        when(vagaRepository.findAll()).thenReturn(listaVagas);

        List<Vaga> resultado = vagaService.listarTodasVagas();

        assertEquals(2, resultado.size());
        verify(vagaRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarVagaPorId_Existente() {
        Long id = 1L;
        Vaga vaga = new Vaga();
        vaga.setId(id);
        vaga.setTitulo("Desenvolvedor Java");

        when(vagaRepository.findById(id)).thenReturn(Optional.of(vaga));

        Vaga resultado = vagaService.buscarVagaPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Desenvolvedor Java", resultado.getTitulo());
        verify(vagaRepository, times(1)).findById(id);
    }

    @Test
    public void testBuscarVagaPorId_NaoExistente() {
        Long id = 1L;
        when(vagaRepository.findById(id)).thenReturn(Optional.empty());

        Vaga resultado = vagaService.buscarVagaPorId(id);

        assertNull(resultado);
        verify(vagaRepository, times(1)).findById(id);
    }

    @Test
    public void testCriarVaga() {
        Vaga vaga = new Vaga();
        vaga.setTitulo("Desenvolvedor Java");
        vaga.setDescricao("Vaga para desenvolvedor Java pleno");

        when(vagaRepository.save(vaga)).thenReturn(vaga);

        Vaga resultado = vagaService.criarVaga(vaga);

        assertNotNull(resultado);
        assertEquals("Desenvolvedor Java", resultado.getTitulo());
        assertEquals("Vaga para desenvolvedor Java pleno", resultado.getDescricao());
        verify(vagaRepository, times(1)).save(vaga);
    }

    @Test
    public void testAtualizarVaga_Existente() {
        Long id = 1L;
        Vaga vagaExistente = new Vaga();
        vagaExistente.setId(id);
        vagaExistente.setTitulo("Desenvolvedor Java");

        Vaga vagaAtualizada = new Vaga();
        vagaAtualizada.setId(id);
        vagaAtualizada.setTitulo("Desenvolvedor Java Sênior");

        when(vagaRepository.findById(id)).thenReturn(Optional.of(vagaExistente));
        when(vagaRepository.save(vagaExistente)).thenReturn(vagaAtualizada); // Simula a atualização

        Vaga resultado = vagaService.atualizarVaga(id, vagaAtualizada);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Desenvolvedor Java Sênior", resultado.getTitulo());
        verify(vagaRepository, times(1)).findById(id);
        verify(vagaRepository, times(1)).save(vagaExistente);
    }

    @Test
    public void testAtualizarVaga_NaoExistente() {
        Long id = 1L;
        Vaga vagaAtualizada = new Vaga();
        vagaAtualizada.setId(id);
        vagaAtualizada.setTitulo("Desenvolvedor Java Sênior");

        when(vagaRepository.findById(id)).thenReturn(Optional.empty());

        Vaga resultado = vagaService.atualizarVaga(id, vagaAtualizada);

        assertNull(resultado);
        verify(vagaRepository, times(1)).findById(id);
        verify(vagaRepository, never()).save(any(Vaga.class));
    }

    @Test
    public void testDeletarVaga() {
        Long id = 1L;

        vagaService.deletarVaga(id);

        verify(vagaRepository, times(1)).deleteById(id);
    }
}
