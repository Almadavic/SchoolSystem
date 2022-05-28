package application.entity.users;

import javax.persistence.*;

@Entity
@Table(name = "tb_principals")
public class Principal extends User { // Classe do Banco - > Principal| Filha de User | Representa os diretores no banco


    public Principal() {
    }

    public Principal(String nome, String email, String password) {
        super(nome, email, password);
    }
}
