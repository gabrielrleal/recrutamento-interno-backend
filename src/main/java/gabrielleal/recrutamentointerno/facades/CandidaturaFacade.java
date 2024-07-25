package gabrielleal.recrutamentointerno.facades;

import gabrielleal.recrutamentointerno.dtos.CandidaturaDTO;
import gabrielleal.recrutamentointerno.models.Candidatura;
import gabrielleal.recrutamentointerno.services.CandidaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CandidaturaFacade {

    @Autowired
    private CandidaturaService candidaturaService;

    public List<CandidaturaDTO> listarTodasCandidaturas() {
        List<Candidatura> candidaturas = candidaturaService.listarTodasCandidaturas();
        return candidaturas.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    public CandidaturaDTO buscarCandidaturaPorId(Long id) {
        Candidatura candidatura = candidaturaService.buscarCandidaturaPorId(id);
        return converterParaDTO(candidatura);
    }
    public List<CandidaturaDTO> buscarCandidaturasPorVagaId(Long vagaId) {
        List<Candidatura> candidaturas = candidaturaService.buscarCandidaturasPorVagaId(vagaId);
        return candidaturas.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    public List<CandidaturaDTO> listarCandidaturasPorCandidato(Long candidatoId) {
        List<Candidatura> candidaturas = candidaturaService.buscarCandidaturasPorCandidatoId(candidatoId);
        return candidaturas.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }


    public CandidaturaDTO criarCandidatura(CandidaturaDTO candidaturaDTO) {
        Candidatura candidatura = candidaturaService.candidatarSeAVaga(candidaturaDTO.getVagaId(), candidaturaDTO.getCandidatoId());
        return converterParaDTO(candidatura);
    }
    public CandidaturaDTO criarCandidatura2(CandidaturaDTO candidaturaDTO) {
        Candidatura candidatura = converterParaEntidade(candidaturaDTO);
        Candidatura candidaturaCriada = candidaturaService.criarCandidatura(candidatura);
        return converterParaDTO(candidaturaCriada);
    }

    public CandidaturaDTO atualizarCandidatura(Long id, CandidaturaDTO candidaturaDTO) {
        Candidatura candidaturaExistente = candidaturaService.buscarCandidaturaPorId(id);
        if (candidaturaExistente != null) {
            // Atualizar os campos da candidaturaExistente com os dados de candidaturaDTO
            // (Vaga e Candidato devem ser buscados pelos IDs)
            return converterParaDTO(candidaturaService.atualizarCandidatura(candidaturaExistente));
        }
        return null; // Ou lançar uma exceção caso a candidatura não exista
    }

    public void deletarCandidatura(Long id) {
        candidaturaService.deletarCandidatura(id);
    }
    private CandidaturaDTO converterParaDTO(Candidatura candidatura) {
        CandidaturaDTO candidaturaDTO = new CandidaturaDTO();
        candidaturaDTO.setId(candidatura.getId());
        candidaturaDTO.setVagaId(candidatura.getVaga().getId());
        candidaturaDTO.setCandidatoId(candidatura.getCandidato().getId());
        candidaturaDTO.setDataCandidatura(candidatura.getDataCandidatura());
        return candidaturaDTO;
    }

    private Candidatura converterParaEntidade(CandidaturaDTO candidaturaDTO) {
        Candidatura candidatura = new Candidatura();
        candidatura.setId(candidaturaDTO.getId());
        return candidatura;
    }
}