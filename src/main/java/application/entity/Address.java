package application.entity;

import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "tb_address")
public class Address implements Serializable { // Classe do Banco - > Address | Representa os endereços dos usuários

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String state;
    private String country;

    @JsonIgnore
    @OneToOne
    @MapsId
    private User user; // Um endereço tem 1 usuário associado

    public Address() {

    }

    public Address(String city, String state, String country, User user) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.user = user;
    }

}
