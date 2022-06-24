package application.domain.entity.user;

import application.domain.entity.Role;
import application.domain.vo.AddressVO;
import application.domain.vo.RegistrationVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_users")
public abstract class User implements UserDetails { // Classe  do banco - > User | Classe PARENT | Representa os usuários do sistema.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Embedded
    private AddressVO address;                // Um usuário tem 1 endereço.

    @Embedded
    private RegistrationVO registration;     // Um usuário tem 1 cadastro associado quando foi cadastrado.

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Setter(AccessLevel.NONE)
    private List<Role> roles = new ArrayList<>();       // Muitos usuários tem muitas permissões


    public User(String nome, String email, String password) {
        this.name = nome;
        this.email = email;
        this.password = password;
    }

    public void addRole(Role role) {
        roles.add(role);
    }// Adicionar uma permissão(role).

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public List<Role> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Name : " + getName() + ", Email : " + email;
    }

}
