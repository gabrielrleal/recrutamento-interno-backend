package gabrielleal.recrutamentointerno.facades;

import gabrielleal.recrutamentointerno.dtos.VagaDTO;
import gabrielleal.recrutamentointerno.models.Vaga;
import gabrielleal.recrutamentointerno.services.VagaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VagaFacadeTests {

    @Mock
    private VagaService vagaService;

    @InjectMocks
    private VagaFacade vagaFacade;

    private Vaga vaga;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        vaga = new Vaga();
        vaga.setId(1L);
        vaga.setTitulo("Test Vaga");
        vaga.setDescricao("Descrição da Vaga");
    }

    @Test
    public void testListarTodasVagas() {
        when(vagaService.listarTodasVagas()).thenReturn(Arrays.asList(vaga));

        List<VagaDTO> vagas = vagaFacade.listarTodasVagas();

        assertNotNull(vagas);
        assertEquals(1, vagas.size());
        assertEquals(1L, vagas.get(0).getId());
        assertEquals("Test Vaga", vagas.get(0).getTitulo());
        assertEquals("Descrição da Vaga", vagas.get(0).getDescricao());
    }

    @Test
    public void testBuscarVagaPorId() {
        when(vagaService.buscarVagaPorId(1L)).thenReturn(vaga);

        VagaDTO vagaDTO = vagaFacade.buscarVagaPorId(1L);

        assertNotNull(vagaDTO);
        assertEquals(1L, vagaDTO.getId());
        assertEquals("Test Vaga", vagaDTO.getTitulo());
        assertEquals("Descrição da Vaga", vagaDTO.getDescricao());
    }

    @Test
    public void testCriarVaga() {
        when(vagaService.criarVaga(any(Vaga.class))).thenReturn(vaga);

        VagaDTO vagaDTO = new VagaDTO();
        vagaDTO.setTitulo("Test Vaga");
        vagaDTO.setDescricao("Descrição da Vaga");

        VagaDTO novaVagaDTO = vagaFacade.criarVaga(vagaDTO);

        assertNotNull(novaVagaDTO);
        assertEquals(1L, novaVagaDTO.getId());
        assertEquals("Test Vaga", novaVagaDTO.getTitulo());
        assertEquals("Descrição da Vaga", novaVagaDTO.getDescricao());
    }

    @Test
    public void testAtualizarVaga() {
        when(vagaService.buscarVagaPorId(1L)).thenReturn(vaga);
        when(vagaService.atualizarVaga(eq(1L), any(Vaga.class))).thenAnswer(invocation -> {
            Vaga updatedVaga = invocation.getArgument(1);
            updatedVaga.setId(1L);
            return updatedVaga;
        });

        VagaDTO vagaDTO = new VagaDTO();
        vagaDTO.setTitulo("Updated Vaga");
        vagaDTO.setDescricao("Updated Descrição");

        VagaDTO vagaAtualizadaDTO = vagaFacade.atualizarVaga(1L, vagaDTO);

        assertNotNull(vagaAtualizadaDTO);
        assertEquals(1L, vagaAtualizadaDTO.getId());
        assertEquals("Updated Vaga", vagaAtualizadaDTO.getTitulo());
        assertEquals("Updated Descrição", vagaAtualizadaDTO.getDescricao());
    }

    @Test
    public void testDeletarVaga() {
        doNothing().when(vagaService).deletarVaga(1L);

        vagaFacade.deletarVaga(1L);

        verify(vagaService, times(1)).deletarVaga(1L);
    }
}