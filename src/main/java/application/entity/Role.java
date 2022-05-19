package application.entity;


import javax.persistence.*;

import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tb_roles")
public class Role implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToMany(mappedBy="roles")
    private List<User> users = new ArrayList<>();

    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name=name;
    }


    public Long getId() {

        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

}