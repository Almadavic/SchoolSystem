package application.entity;


import javax.persistence.*;

import application.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_roles")
public class Role implements GrantedAuthority{ // A classe que representam os perfis, roles, permissões de cada Usuário!

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @Setter(AccessLevel.NONE)
    private List<User> users = new ArrayList<>(); // Um perfil tem uma lista de Usuários associados a ele.


    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }


}