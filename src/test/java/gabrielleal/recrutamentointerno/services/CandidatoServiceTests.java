package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.repositories.CandidatoRepository;
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

@ExtendWith(MockitoExtension.class) // Habilita o uso do Mockito
public class CandidatoServiceTests {

    @Mock // Cria um mock do CandidatoRepository
    private CandidatoRepository candidatoRepository;

    @InjectMocks // Injeta o mock no CandidatoService
    private CandidatoService candidatoService;

    @Test
    public void testListarTodosCandidatos() {
        Candidato candidato1 = new Candidato();
        candidato1.setId(1L);
        candidato1.setNome("João Silva");
        candidato1.setEmail("joao.silva@example.com");

        Candidato candidato2 = new Candidato();
        candidato2.setId(2L);
        candidato2.setNome("Maria Souza");
        candidato2.setEmail("maria.souza@example.com");

        List<Candidato> listaDeCandidatos = Arrays.asList(candidato1, candidato2);

        when(candidatoRepository.findAll()).thenReturn(listaDeCandidatos);

        List<Candidato> resultado = candidatoService.listarTodosCandidatos();

        assertEquals(listaDeCandidatos, resultado); // Verifica se as listas são iguais
        verify(candidatoRepository, times(1)).findAll(); // Verifica se o método findAll foi chamado uma vez
    }

    @Test
    public void testBuscarCandidatoPorId_Existente() {
        Long id = 1L;
        Candidato candidato = new Candidato();
        candidato.setId(id);
        candidato.setNome("João Silva");
        candidato.setEmail("joao.silva@example.com");

        when(candidatoRepository.findById(id)).thenReturn(Optional.of(candidato));

        Candidato resultado = candidatoService.buscarCandidatoPorId(id);

        assertEquals(candidato, resultado); // Verifica se os candidatos são iguais
        verify(candidatoRepository, times(1)).findById(id); // Verifica se o método findById foi chamado uma vez
    }

    @Test
    public void testBuscarCandidatoPorId_NaoExistente() {
        Long id = 1L;

        when(candidatoRepository.findById(id)).thenReturn(Optional.empty());

        Candidato resultado = candidatoService.buscarCandidatoPorId(id);

        assertNull(resultado); // Verifica se o resultado é nulo
        verify(candidatoRepository, times(1)).findById(id);
    }

    @Test
    public void testCriarCandidato() {
        Candidato candidato = new Candidato();
        candidato.setNome("João Silva");
        candidato.setEmail("joao.silva@example.com");

        when(candidatoRepository.save(candidato)).thenReturn(candidato);

        Candidato resultado = candidatoService.criarCandidato(candidato);

        assertEquals(candidato, resultado); // Verifica se os candidatos são iguais
        verify(candidatoRepository, times(1)).save(candidato);
    }

    @Test
    public void testAtualizarCandidato_Existente() {
        Long id = 1L;
        Candidato candidatoExistente = new Candidato();
        candidatoExistente.setId(id);
        candidatoExistente.setNome("João Silva");
        candidatoExistente.setEmail("joao.silva@example.com");

        Candidato candidatoAtualizado = new Candidato();
        candidatoAtualizado.setId(id);
        candidatoAtualizado.setNome("João Santos");
        candidatoAtualizado.setEmail("joao.santos@example.com");

        when(candidatoRepository.findById(id)).thenReturn(Optional.of(candidatoExistente));
        when(candidatoRepository.save(candidatoExistente)).thenReturn(candidatoAtualizado);

        Candidato resultado = candidatoService.atualizarCandidato(id, candidatoAtualizado);

        assertEquals(candidatoAtualizado, resultado); // Verifica se os candidatos são iguais
        verify(candidatoRepository, times(1)).findById(id);
        verify(candidatoRepository, times(1)).save(candidatoExistente);
    }

    @Test
    public void testAtualizarCandidato_NaoExistente() {
        Long id = 1L;
        Candidato candidatoAtualizado = new Candidato();
        candidatoAtualizado.setId(id);
        candidatoAtualizado.setNome("João Santos");
        candidatoAtualizado.setEmail("joao.santos@example.com");

        when(candidatoRepository.findById(id)).thenReturn(Optional.empty());

        Candidato resultado = candidatoService.atualizarCandidato(id, candidatoAtualizado);

        assertNull(resultado); // Verifica se o resultado é nulo
        verify(candidatoRepository, times(1)).findById(id);
        verify(candidatoRepository, never()).save(any(Candidato.class)); // Verifica se o método save não foi chamado
    }

    @Test
    public void testDeletarCandidato() {
        Long id = 1L;

        candidatoService.deletarCandidato(id);

        verify(candidatoRepository, times(1)).deleteById(id); // Verifica se o método deleteById foi chamado uma vez
    }
}
