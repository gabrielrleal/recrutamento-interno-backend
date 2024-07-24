    package gabrielleal.recrutamentointerno.services;

    import gabrielleal.recrutamentointerno.models.Candidato;
    import gabrielleal.recrutamentointerno.repositories.CandidatoRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class CandidatoService {

        @Autowired
        private CandidatoRepository candidatoRepository;

        public List<Candidato> listarTodosCandidatos() {
            return candidatoRepository.findAll();
        }

        public Candidato buscarCandidatoPorId(Long id) {
            return candidatoRepository.findById(id).orElse(null);
        }

        public Candidato criarCandidato(Candidato candidato) {
            return candidatoRepository.save(candidato);
        }

        public Candidato atualizarCandidato(Long id, Candidato candidatoAtualizado) {
            Candidato candidatoExistente = candidatoRepository.findById(id).orElse(null);
            if (candidatoExistente != null) {
                candidatoExistente.setNome(candidatoAtualizado.getNome());
                candidatoExistente.setEmail(candidatoAtualizado.getEmail());
                return candidatoRepository.save(candidatoExistente);
            }
            return null; // Ou lançar uma exceção caso o candidato não exista
        }

        public void deletarCandidato(Long id) {
            candidatoRepository.deleteById(id);
        }
    }