package gabrielleal.recrutamentointerno.facades;

import gabrielleal.recrutamentointerno.dtos.CandidatoDTO;
import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.services.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CandidatoFacade {

    @Autowired
    private CandidatoService candidatoService;

    public List<CandidatoDTO> listarTodosCandidatos() {
        List<Candidato> candidatos = candidatoService.listarTodosCandidatos();
        return candidatos.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    public CandidatoDTO buscarCandidatoPorId(Long id) {
        Candidato candidato = candidatoService.buscarCandidatoPorId(id);
        return converterParaDTO(candidato);
    }

    public CandidatoDTO criarCandidato(CandidatoDTO candidatoDTO) {
        Candidato candidato = converterParaEntidade(candidatoDTO);
        Candidato candidatoCriado = candidatoService.criarCandidato(candidato);
        return converterParaDTO(candidatoCriado);
    }

    public CandidatoDTO atualizarCandidato(Long id, CandidatoDTO candidatoDTO) {
        Candidato candidato = converterParaEntidade(candidatoDTO);
        Candidato candidatoAtualizado = candidatoService.atualizarCandidato(id, candidato);
        return converterParaDTO(candidatoAtualizado);
    }

    public void deletarCandidato(Long id) {
        candidatoService.deletarCandidato(id);
    }

    private CandidatoDTO converterParaDTO(Candidato candidato) {
        CandidatoDTO candidatoDTO = new CandidatoDTO();
        candidatoDTO.setId(candidato.getId());
        candidatoDTO.setNome(candidato.getNome());
        candidatoDTO.setEmail(candidato.getEmail());
        return candidatoDTO;
    }

    private Candidato converterParaEntidade(CandidatoDTO candidatoDTO) {
        Candidato candidato = new Candidato();
        candidato.setId(candidatoDTO.getId());
        candidato.setNome(candidatoDTO.getNome());
        candidato.setEmail(candidatoDTO.getEmail());
        return candidato;
    }
}
