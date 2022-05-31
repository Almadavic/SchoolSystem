package application.entity.users;

import application.entity.Responsibility;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tb_principals")
public class Principal extends User { // Classe do Banco - > Principal| Filha de User | Representa os diretores no banco

    @ManyToMany()
    @JoinTable(name = "tb_principals_responsibilities",
            joinColumns = @JoinColumn(name = "principal_id"),
            inverseJoinColumns = @JoinColumn(name = "responsibility_id"))
    private List<Responsibility> responsibilities = new ArrayList<>();

    public void addResponsibility(Responsibility responsibility) {
        responsibilities.add(responsibility);
    }

    public Principal(String nome, String email, String password) {
        super(nome, email, password);
    }

}
