package application.entities;

import application.entities.users.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_address")
public class Address implements Serializable { // Classe do Banco - > Address | Representa os endereços dos usuários

    @Serial
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String city;
    private String state;
    private String country;

    @OneToOne
    @MapsId
    private User user; // Um endereço tem 1 usuário associado.

    public Address(String city, String state, String country, User user) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.user = user;
    }

}
