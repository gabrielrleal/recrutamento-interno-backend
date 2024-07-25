package gabrielleal.recrutamentointerno.facades;

import gabrielleal.recrutamentointerno.dtos.VagaDTO;
import gabrielleal.recrutamentointerno.models.Vaga;
import gabrielleal.recrutamentointerno.services.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VagaFacade {

    @Autowired
    private VagaService vagaService;
    
    public List<VagaDTO> listarTodasVagas() {
        List<Vaga> vagas = vagaService.listarTodasVagas();
        return vagas.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    public List<VagaDTO> listarVagasAtivas() {
        return vagaService.listarVagasAtivas().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    public List<VagaDTO> listarVagasInativas() {
        return vagaService.listarVagasInativas().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    public void desativarVaga(Long id) {
        vagaService.desativarVaga(id);
    }

    public void ativarVaga(Long id) {
        vagaService.ativarVaga(id);
    }

    public VagaDTO buscarVagaPorId(Long id) {
        Vaga vaga = vagaService.buscarVagaPorId(id);
        return converterParaDTO(vaga);
    }

    public VagaDTO criarVaga(VagaDTO vagaDTO) {
        Vaga vaga = converterParaEntidade(vagaDTO);
        Vaga vagaCriada = vagaService.criarVaga(vaga);
        return converterParaDTO(vagaCriada);
    }

    public VagaDTO atualizarVaga(Long id, VagaDTO vagaDTO) {
        Vaga vagaExistente = vagaService.buscarVagaPorId(id);
        if (vagaExistente != null) {
            vagaExistente.setTitulo(vagaDTO.getTitulo());
            vagaExistente.setDescricao(vagaDTO.getDescricao());
            return converterParaDTO(vagaService.atualizarVaga(id, vagaExistente)); // Passando o ID
        }
        return null;
    }

    public void deletarVaga(Long id) {
        vagaService.deletarVaga(id);
    }
    private VagaDTO converterParaDTO(Vaga vaga) {
        VagaDTO vagaDTO = new VagaDTO();
        vagaDTO.setId(vaga.getId());
        vagaDTO.setTitulo(vaga.getTitulo());
        vagaDTO.setDescricao(vaga.getDescricao());
        vagaDTO.setStatus(vaga.getStatus());
        return vagaDTO;
    }

    private Vaga converterParaEntidade(VagaDTO vagaDTO) {
        Vaga vaga = new Vaga();
        vaga.setId(vagaDTO.getId());
        vaga.setTitulo(vagaDTO.getTitulo());
        vaga.setDescricao(vagaDTO.getDescricao());
        return vaga;
    }
}