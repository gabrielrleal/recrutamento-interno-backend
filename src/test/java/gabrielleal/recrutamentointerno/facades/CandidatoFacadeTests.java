package gabrielleal.recrutamentointerno.facades;

import gabrielleal.recrutamentointerno.dtos.CandidatoDTO;
import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.services.CandidatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CandidatoFacadeTests {

    @Mock
    private CandidatoService candidatoService;

    @InjectMocks
    private CandidatoFacade candidatoFacade;

    private Candidato candidato;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        candidato = new Candidato();
        candidato.setId(1L);
        candidato.setNome("Test Candidato");
        candidato.setEmail("candidato@example.com");
    }

    @Test
    public void testListarTodosCandidatos() {
        when(candidatoService.listarTodosCandidatos()).thenReturn(Arrays.asList(candidato));

        List<CandidatoDTO> candidatos = candidatoFacade.listarTodosCandidatos();

        assertNotNull(candidatos);
        assertEquals(1, candidatos.size());
        assertEquals(1L, candidatos.get(0).getId());
        assertEquals("Test Candidato", candidatos.get(0).getNome());
        assertEquals("candidato@example.com", candidatos.get(0).getEmail());
    }

    @Test
    public void testBuscarCandidatoPorId() {
        when(candidatoService.buscarCandidatoPorId(1L)).thenReturn(candidato);

        CandidatoDTO candidatoDTO = candidatoFacade.buscarCandidatoPorId(1L);

        assertNotNull(candidatoDTO);
        assertEquals(1L, candidatoDTO.getId());
        assertEquals("Test Candidato", candidatoDTO.getNome());
        assertEquals("candidato@example.com", candidatoDTO.getEmail());
    }

    @Test
    public void testCriarCandidato() {
        when(candidatoService.criarCandidato(any(Candidato.class))).thenReturn(candidato);

        CandidatoDTO candidatoDTO = new CandidatoDTO();
        candidatoDTO.setNome("Test Candidato");
        candidatoDTO.setEmail("candidato@example.com");

        CandidatoDTO novoCandidatoDTO = candidatoFacade.criarCandidato(candidatoDTO);

        assertNotNull(novoCandidatoDTO);
        assertEquals(1L, novoCandidatoDTO.getId());
        assertEquals("Test Candidato", novoCandidatoDTO.getNome());
        assertEquals("candidato@example.com", novoCandidatoDTO.getEmail());
    }

    @Test
    public void testAtualizarCandidato() {
        when(candidatoService.atualizarCandidato(eq(1L), any(Candidato.class))).thenReturn(candidato);

        CandidatoDTO candidatoDTO = new CandidatoDTO();
        candidatoDTO.setNome("Updated Candidato");
        candidatoDTO.setEmail("updated@example.com");

        CandidatoDTO candidatoAtualizadoDTO = candidatoFacade.atualizarCandidato(1L, candidatoDTO);

        assertNotNull(candidatoAtualizadoDTO);
        assertEquals(1L, candidatoAtualizadoDTO.getId());
        assertEquals("Test Candidato", candidatoAtualizadoDTO.getNome());
        assertEquals("candidato@example.com", candidatoAtualizadoDTO.getEmail());
    }

    @Test
    public void testDeletarCandidato() {
        doNothing().when(candidatoService).deletarCandidato(1L);

        candidatoFacade.deletarCandidato(1L);

        verify(candidatoService, times(1)).deletarCandidato(1L);
    }
}