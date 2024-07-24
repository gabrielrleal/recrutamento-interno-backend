package gabrielleal.recrutamentointerno.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gabrielleal.recrutamentointerno.dtos.CandidatoDTO;
import gabrielleal.recrutamentointerno.exceptions.CandidatoNaoEncontradoException;
import gabrielleal.recrutamentointerno.facades.CandidatoFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CandidatoController.class)
public class CandidatoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidatoFacade candidatoFacade;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testBuscarCandidatoPorId() throws Exception {
        CandidatoDTO candidatoDTO = new CandidatoDTO();
        candidatoDTO.setId(1L);
        candidatoDTO.setNome("João Silva");
        candidatoDTO.setEmail("joao.silva@example.com");

        when(candidatoFacade.buscarCandidatoPorId(1L)).thenReturn(candidatoDTO);

        mockMvc.perform(get("/candidatos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(candidatoFacade, times(1)).buscarCandidatoPorId(1L);
    }

    @Test
    public void testBuscarCandidatoPorIdNaoExistente() throws Exception {
        when(candidatoFacade.buscarCandidatoPorId(1L)).thenThrow(CandidatoNaoEncontradoException.class);

        mockMvc.perform(get("/candidatos/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(candidatoFacade, times(1)).buscarCandidatoPorId(1L);
    }

    @Test
    public void testCriarCandidato() throws Exception {
        CandidatoDTO candidatoDTO = new CandidatoDTO();
        candidatoDTO.setNome("João Silva");
        candidatoDTO.setEmail("joao.silva@example.com");

        when(candidatoFacade.criarCandidato(any(CandidatoDTO.class))).thenReturn(candidatoDTO);

        mockMvc.perform(post("/candidatos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidatoDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(candidatoFacade, times(1)).criarCandidato(any(CandidatoDTO.class));
    }

    @Test
    public void testAtualizarCandidato() throws Exception {
        CandidatoDTO candidatoDTO = new CandidatoDTO();
        candidatoDTO.setId(1L);
        candidatoDTO.setNome("João Silva Atualizado");
        candidatoDTO.setEmail("joao.silva.atualizado@example.com");

        when(candidatoFacade.atualizarCandidato(eq(1L), any(CandidatoDTO.class))).thenReturn(candidatoDTO);

        mockMvc.perform(put("/candidatos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidatoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("João Silva Atualizado"))
                .andExpect(jsonPath("$.email").value("joao.silva.atualizado@example.com"));

        verify(candidatoFacade, times(1)).atualizarCandidato(eq(1L), any(CandidatoDTO.class));
    }

    @Test
    public void testAtualizarCandidatoNaoExistente() throws Exception {
        CandidatoDTO candidatoDTO = new CandidatoDTO();
        candidatoDTO.setId(1L);
        candidatoDTO.setNome("João Silva Atualizado");
        candidatoDTO.setEmail("joao.silva.atualizado@example.com");

        when(candidatoFacade.atualizarCandidato(eq(1L), any(CandidatoDTO.class))).thenThrow(CandidatoNaoEncontradoException.class);

        mockMvc.perform(put("/candidatos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidatoDTO)))
                .andExpect(status().isNotFound());

        verify(candidatoFacade, times(1)).atualizarCandidato(eq(1L), any(CandidatoDTO.class));
    }

    @Test
    public void testDeletarCandidato() throws Exception {
        mockMvc.perform(delete("/candidatos/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(candidatoFacade, times(1)).deletarCandidato(1L);
    }

    @Test
    public void testDeletarCandidatoNaoExistente() throws Exception {
        doThrow(CandidatoNaoEncontradoException.class).when(candidatoFacade).deletarCandidato(1L);

        mockMvc.perform(delete("/candidatos/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(candidatoFacade, times(1)).deletarCandidato(1L);
    }
}
