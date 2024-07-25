package gabrielleal.recrutamentointerno.repositories;

import gabrielleal.recrutamentointerno.models.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findByStatusTrue(); // Buscar vagas ativas
    List<Vaga> findByStatusFalse(); // Buscar vagas inativas
}