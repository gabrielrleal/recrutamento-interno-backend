package gabrielleal.recrutamentointerno.controllers;

import gabrielleal.recrutamentointerno.dtos.CandidaturaDTO;
import gabrielleal.recrutamentointerno.exceptions.CandidaturaNaoEncontradaException;
import gabrielleal.recrutamentointerno.facades.CandidaturaFacade;
import gabrielleal.recrutamentointerno.models.Candidatura;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaFacade candidaturaFacade;


    @Operation(summary = "Listar todas as candidaturas", description = "Retorna uma lista de todas as candidaturas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de candidaturas retornada com sucesso")
    })
    @GetMapping
    public List<CandidaturaDTO> listarTodasCandidaturas() {
        return candidaturaFacade.listarTodasCandidaturas();
    }

    @Operation(summary = "Buscar candidaturas por ID da vaga", description = "Retorna uma lista de candidaturas pelo ID da vaga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidaturas encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma candidatura encontrada para o ID da vaga fornecido")
    })
    @GetMapping("/vaga/{vagaId}")
    public ResponseEntity<List<CandidaturaDTO>> buscarCandidaturasPorVagaId(@PathVariable("vagaId") Long vagaId) {
        List<CandidaturaDTO> candidaturas = candidaturaFacade.buscarCandidaturasPorVagaId(vagaId);
        if (candidaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(candidaturas);
        }
    }

    @Operation(summary = "Buscar candidatura por ID", description = "Retorna uma candidatura pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidatura encontrada"),
            @ApiResponse(responseCode = "404", description = "Candidatura não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CandidaturaDTO> buscarCandidaturaPorId(@PathVariable("id") Long id) {
        CandidaturaDTO candidaturaDTO = candidaturaFacade.buscarCandidaturaPorId(id);
        if (candidaturaDTO == null) {
            throw new CandidaturaNaoEncontradaException("Candidatura não encontrada com o ID: " + id);
        } else {
            return ResponseEntity.ok(candidaturaDTO);
        }
    }

    @Operation(summary = "Criar uma nova candidatura", description = "Cria uma nova candidatura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Candidatura criada com sucesso"),
            @ApiResponse(responseCode = "409", description = "O candidato já se candidatou para esta vaga")
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> criarCandidatura(@Valid @RequestBody CandidaturaDTO candidaturaDTO) {
        CandidaturaDTO novaCandidaturaDTO = candidaturaFacade.criarCandidatura(candidaturaDTO);
        
        if (novaCandidaturaDTO == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "O candidato já se candidatou para esta vaga");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        URI uri = UriComponentsBuilder.fromPath("/candidaturas/{id}").buildAndExpand(novaCandidaturaDTO.getId()).toUri();
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Candidatura criada com sucesso!");
        response.put("id", novaCandidaturaDTO.getId());
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Atualizar uma candidatura", description = "Atualiza os dados de uma candidatura existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidatura atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Candidatura não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CandidaturaDTO> atualizarCandidatura(@PathVariable("id") Long id, @Valid @RequestBody CandidaturaDTO candidaturaDTO) {
        CandidaturaDTO candidaturaAtualizada = candidaturaFacade.atualizarCandidatura(id, candidaturaDTO);
        if (candidaturaAtualizada != null) {
            return ResponseEntity.ok(candidaturaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deletar uma candidatura", description = "Deleta uma candidatura pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Candidatura deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Candidatura não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCandidatura(@PathVariable("id") Long id) {
        candidaturaFacade.deletarCandidatura(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar candidaturas por ID do candidato", description = "Retorna uma lista de candidaturas pelo ID do candidato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidaturas encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma candidatura encontrada para o ID do candidato fornecido")
    })
    @GetMapping("/candidato/{candidatoId}")
    public ResponseEntity<List<CandidaturaDTO>> listarCandidaturasPorCandidato(@PathVariable("candidatoId") Long candidatoId) {
        List<CandidaturaDTO> candidaturas = candidaturaFacade.listarCandidaturasPorCandidato(candidatoId);
        if (candidaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(candidaturas);
        }
    }
}