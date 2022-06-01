package application.entity.users;


import application.entity.ClassRoom;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_teachers")
public class Teacher extends User { // Classe do Banco - > Teacher

    @OneToOne()
    private ClassRoom classRoom; // 1 Classe tem 1 professsor | Filha de User | Representam os Professores do sistema.


    public Teacher(String nome, String email, String senha) {
        super(nome, email, senha);

    }

    public Teacher(String nome, String email, String senha, ClassRoom classRoom) {
        super(nome, email, senha);
        this.classRoom = classRoom;
    }


    @Override
    public String toString() {
        return "Teacher : "+getEmail()+", Id : "+getId();
    }

}
