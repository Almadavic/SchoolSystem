package application.entities.users;

import application.entities.ClassRoom;
import application.entities.ReportCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "tb_students")
public class Student extends User { // Classe do Banco -> Student | Filha de User | Representa os Alunos do sistema.

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private ReportCard reportCard;         // Um Estudante tem um boletim.


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
        this.reportCard = new ReportCard();
        this.reportCard.setStudent(this);
    }

    @Override
    public String toString() {
        return "Student : "+getEmail()+", Id : "+getId();
    }

}
