package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.models.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {


    Candidatura findByVagaIdAndCandidatoId(Long vagaId, Long candidatoId);
    List<Candidatura> findByVagaId(Long vagaId);

}