package application.entity.users;

import javax.persistence.*;

@Entity
@Table(name = "tb_principals")
public class Principal extends User { // Classe do Banco - > Principal| Filha de User | Representa os diretores no banco

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
