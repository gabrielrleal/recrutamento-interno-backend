package gabrielleal.recrutamentointerno.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VagaDTO {
    private Long id;
    @NotBlank(message = "O título da vaga é obrigatório")
    @Size(max = 255, message = "O título da vaga deve ter no máximo 255 caracteres")
    private String titulo;
    @NotBlank(message = "A descrição da vaga é obrigatória")
    private String descricao;
    private boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}