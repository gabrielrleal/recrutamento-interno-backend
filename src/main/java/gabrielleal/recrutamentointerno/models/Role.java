package gabrielleal.recrutamentointerno.models;

import gabrielleal.recrutamentointerno.enums.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleEnum nome;


    public RoleEnum getNome() {
        return nome;
    }

    public void setNome(RoleEnum nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
