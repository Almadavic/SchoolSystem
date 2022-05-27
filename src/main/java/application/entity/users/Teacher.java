package application.entity.users;


import application.entity.ClassRoom;
import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "tb_teachers")
public class Teacher extends User { // Classe do Banco - > Teacher

    @JsonIgnore
    @OneToOne()
    private ClassRoom classRoom; // 1 Classe tem 1 professsor | Filha de User | Representam os Professores do sistema.

    public Teacher() {

    }

    public Teacher(String nome, String email, String senha) {
        super(nome, email, senha);

    }

    public Teacher(String nome, String email, String senha, ClassRoom classRoom) {
        super(nome, email, senha);
        this.classRoom = classRoom;
    }


    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    @Override
    public String toString() {
        return "Teacher : "+getEmail()+", Id : "+getId();
    }

}
