package application.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "tb_teachers")
public class Teacher  extends User implements Serializable {

    @JsonIgnore
    @OneToOne()
    private ClassRoom classRoom;

    public Teacher() {

    }

    public Teacher(String nome, String email, String senha, ClassRoom classRoom) {
       super(nome, email, senha);
        this.classRoom=classRoom;
       }



    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }
}
