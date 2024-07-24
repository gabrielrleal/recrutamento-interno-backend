package gabrielleal.recrutamentointerno.services;


import gabrielleal.recrutamentointerno.models.Vaga;
import gabrielleal.recrutamentointerno.repositories.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    public List<Vaga> listarTodasVagas() {
        return vagaRepository.findAll();
    }

    public Vaga buscarVagaPorId(Long id) {
        return vagaRepository.findById(id).orElse(null);
    }

    public Vaga criarVaga(Vaga vaga) {
        return vagaRepository.save(vaga);
    }

    public Vaga atualizarVaga(Long id, Vaga vagaAtualizada) {
        Vaga vagaExistente = vagaRepository.findById(id).orElse(null);
        if (vagaExistente != null) {
            // Atualizar os campos da vagaExistente com os dados de vagaAtualizada
            return vagaRepository.save(vagaExistente);
        }
        return null;
    }

    public void deletarVaga(Long id) {
        vagaRepository.deleteById(id);
    }
}