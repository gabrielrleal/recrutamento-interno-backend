package gabrielleal.recrutamentointerno.controllers;

import gabrielleal.recrutamentointerno.dtos.VagaDTO;
import gabrielleal.recrutamentointerno.exceptions.VagaNaoEncontradaException;
import gabrielleal.recrutamentointerno.facades.VagaFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private VagaFacade vagaFacade;

    @Operation(summary = "Listar todas as vagas", description = "Retorna uma lista de todas as vagas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vagas retornada com sucesso")
    })
    @GetMapping
    public List<VagaDTO> listarTodasVagas() {
        return vagaFacade.listarTodasVagas();
    }

    @Operation(summary = "Buscar vaga por ID", description = "Retorna uma vaga pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga encontrada"),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VagaDTO> buscarVagaPorId(@PathVariable("id") Long id) {
        VagaDTO vagaDTO = vagaFacade.buscarVagaPorId(id);
        if (vagaDTO == null) {
            throw new VagaNaoEncontradaException("Vaga não encontrada com o ID: " + id);
        } else {
            return ResponseEntity.ok(vagaDTO);
        }
    }
    @Operation(summary = "Listar todas as vagas ativas", description = "Retorna uma lista de todas as vagas ativas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vagas ativas retornada com sucesso")
    })
    @GetMapping("/ativas")
    public ResponseEntity<List<VagaDTO>> listarVagasAtivas() {
        List<VagaDTO> vagas = vagaFacade.listarVagasAtivas();
        return ResponseEntity.ok(vagas);
    }

    @Operation(summary = "Listar todas as vagas inativas", description = "Retorna uma lista de todas as vagas inativas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vagas inativas retornada com sucesso")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/inativas")
    public ResponseEntity<List<VagaDTO>> listarVagasInativas() {
        List<VagaDTO> vagas = vagaFacade.listarVagasInativas();
        return ResponseEntity.ok(vagas);
    }

    @Operation(summary = "Criar uma nova vaga", description = "Cria uma nova vaga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vaga criada com sucesso")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<VagaDTO> criarVaga(@RequestBody VagaDTO vagaDTO) {
        VagaDTO novaVaga = vagaFacade.criarVaga(vagaDTO);
        return ResponseEntity.created(URI.create("/vagas/" + novaVaga.getId())).body(novaVaga);
    }

    @Operation(summary = "Atualizar uma vaga", description = "Atualiza os dados de uma vaga existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<VagaDTO> atualizarVaga(@PathVariable("id") Long id, @RequestBody VagaDTO vagaDTO) {
        VagaDTO vagaAtualizada = vagaFacade.atualizarVaga(id, vagaDTO);
        if (vagaAtualizada != null) {
            return ResponseEntity.ok(vagaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Deletar uma vaga", description = "Deleta uma vaga pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vaga deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVaga(@PathVariable("id") Long id) {
        vagaFacade.deletarVaga(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/desativar/{id}")
    public ResponseEntity<Void> desativarVaga(@PathVariable("id") Long id) {
        vagaFacade.desativarVaga(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/ativar/{id}")
    public ResponseEntity<Void> ativarVaga(@PathVariable("id") Long id) {
        vagaFacade.ativarVaga(id);
        return ResponseEntity.noContent().build();
    }
}
