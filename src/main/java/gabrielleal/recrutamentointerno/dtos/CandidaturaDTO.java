package gabrielleal.recrutamentointerno.dtos;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CandidaturaDTO {
    private Long id;
    @NotNull(message = "O ID da vaga é obrigatório")
    private Long vagaId;
    @NotNull(message = "O ID do candidato é obrigatório")
    private Long candidatoId;
    private LocalDateTime dataCandidatura;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVagaId() {
        return vagaId;
    }

    public void setVagaId(Long vagaId) {
        this.vagaId = vagaId;
    }

    public Long getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(Long candidatoId) {
        this.candidatoId = candidatoId;
    }

    public LocalDateTime getDataCandidatura() {
        return dataCandidatura;
    }

    public void setDataCandidatura(LocalDateTime dataCandidatura) {
        this.dataCandidatura = dataCandidatura;
    }
}