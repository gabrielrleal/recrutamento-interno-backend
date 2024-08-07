package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.models.Candidatura;
import gabrielleal.recrutamentointerno.models.Vaga;
import gabrielleal.recrutamentointerno.repositories.CandidatoRepository;
import gabrielleal.recrutamentointerno.repositories.CandidaturaRepository;
import gabrielleal.recrutamentointerno.repositories.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private VagaRepository vagaRepository;

    public List<Candidatura> listarTodasCandidaturas() {
        return candidaturaRepository.findAll();
    }

    public Candidatura buscarCandidaturaPorId(Long id) {
        return candidaturaRepository.findById(id).orElse(null);
    }

    public List<Candidatura> buscarCandidaturasPorVagaId(Long vagaId) {
        return candidaturaRepository.findByVagaId(vagaId);
    }
    public Candidatura criarCandidatura(Candidatura candidatura) {
        if (candidatura.getVaga() == null || candidatura.getCandidato() == null) {
            throw new IllegalArgumentException("Vaga e Candidato não podem ser nulos");
        }
        
        if (candidaturaRepository.existsByCandidatoIdAndVagaId(candidatura.getCandidato().getId(), candidatura.getVaga().getId())) {
            throw new IllegalStateException("O candidato já se candidatou para esta vaga");
        }
        candidatura.setDataCandidatura(LocalDateTime.now());
        return candidaturaRepository.save(candidatura);
    }

    public Candidatura atualizarCandidatura(Candidatura candidaturaAtualizada) {
        return candidaturaRepository.save(candidaturaAtualizada);
    }

    public void deletarCandidatura(Long id) {
        candidaturaRepository.deleteById(id);
    }

    public Candidatura candidatarSeAVaga(Long vagaId, Long candidatoId) {
        Candidato candidato = candidatoRepository.findById(candidatoId)
        .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado"));
        Vaga vaga = vagaRepository.findById(vagaId)
        .orElseThrow(() -> new IllegalArgumentException("Vaga não encontrada"));
        
        if (candidaturaRepository.existsByCandidatoIdAndVagaId(candidatoId, vagaId)) {
            throw new IllegalStateException("O candidato já se candidatou para esta vaga");
        }

        if (candidato != null && vaga != null) {
            Candidatura candidatura = new Candidatura();
            candidatura.setCandidato(candidato);
            candidatura.setVaga(vaga);
            return criarCandidatura(candidatura);
        }
        return null; // Ou lançar uma exceção caso o candidato ou a vaga não existam
    }

    public Candidatura buscarCandidaturaPorVagaECandidato(Long vagaId, Long candidatoId) {
        return candidaturaRepository.findByVagaIdAndCandidatoId(vagaId, candidatoId);
    }

    public List<Candidatura> buscarCandidaturasPorCandidatoId(Long candidatoId) {
        return candidaturaRepository.findByCandidatoId(candidatoId);
    }
}