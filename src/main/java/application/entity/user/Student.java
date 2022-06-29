package application.entity.user;

import application.entity.ClassRoom;
import application.vo.ReportCardVO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tb_students")
public class Student extends User { // Classe do Banco -> Student | Filha de User | Representa os Alunos do sistema.

    @Embedded
    private ReportCardVO reportCard;         // Um Estudante tem um boletim.


    @ManyToOne
    private ClassRoom classRoom; // Uma Classe tem muitos estudantes.

    public Student() {
        instanceReportCard();
    }

    public Student(String nome, String email, String password, ClassRoom classRoom) {
        super(nome, email, password);
        this.classRoom = classRoom;
        instanceReportCard();
    }


    private void instanceReportCard() {
        this.reportCard = new ReportCardVO();
    }

    @Override
    public String toString() {
        return "Student : " + getEmail() + ", Id : " + getId();
    }

}
