package gabrielleal.recrutamentointerno.repositories;
import gabrielleal.recrutamentointerno.models.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
}
