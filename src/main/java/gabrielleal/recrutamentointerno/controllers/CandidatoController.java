package gabrielleal.recrutamentointerno.controllers;

import gabrielleal.recrutamentointerno.dtos.CandidatoDTO;
import gabrielleal.recrutamentointerno.exceptions.CandidatoNaoEncontradoException;
import gabrielleal.recrutamentointerno.facades.CandidatoFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoFacade candidatoFacade;


    @Operation(summary = "Listar todos os candidatos", description = "Retorna uma lista de todos os candidatos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de candidatos retornada com sucesso")
    })
    @GetMapping
    public List<CandidatoDTO> listarTodosCandidatos() {
        return candidatoFacade.listarTodosCandidatos();
    }

    @Operation(summary = "Buscar candidato por ID", description = "Retorna um candidato pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato encontrado"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CandidatoDTO> buscarCandidatoPorId(@PathVariable("id") Long id) {
        CandidatoDTO candidatoDTO = candidatoFacade.buscarCandidatoPorId(id);
        if (candidatoDTO == null) {
            throw new CandidatoNaoEncontradoException("Candidato não encontrado com o ID: " + id);
        } else {
            return ResponseEntity.ok(candidatoDTO);
        }
    }

    @Operation(summary = "Criar um novo candidato", description = "Cria um novo candidato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Candidato criado com sucesso")
    })
    @PostMapping
    public ResponseEntity<CandidatoDTO> criarCandidato(@RequestBody CandidatoDTO candidatoDTO, UriComponentsBuilder uriBuilder) {
        CandidatoDTO novoCandidato = candidatoFacade.criarCandidato(candidatoDTO);
        URI uri = uriBuilder.path("/candidatos/{id}").buildAndExpand(novoCandidato.getId()).toUri();
        return ResponseEntity.created(uri).body(novoCandidato);
    }

    @Operation(summary = "Atualizar um candidato", description = "Atualiza os dados de um candidato existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CandidatoDTO> atualizarCandidato(@PathVariable("id") Long id, @RequestBody CandidatoDTO candidatoDTO) {
        CandidatoDTO candidatoAtualizado = candidatoFacade.atualizarCandidato(id, candidatoDTO);
        if (candidatoAtualizado != null) {
            return ResponseEntity.ok(candidatoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Deletar um candidato", description = "Deleta um candidato pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Candidato deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCandidato(@PathVariable("id") Long id) {
        candidatoFacade.deletarCandidato(id);
        return ResponseEntity.noContent().build();
    }
}
