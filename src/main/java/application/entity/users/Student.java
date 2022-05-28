package application.entity.users;

import application.entity.ClassRoom;
import application.entity.ReportCard;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "tb_students")
public class Student extends User { // Classe do Banco -> Student | Filha de User | Representa os Alunos do sistema.

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private ReportCard reportCard;         // Um Estudante tem um boletim.

    @JsonIgnore
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

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public ReportCard getReportCard() {
        return reportCard;
    }

    public void setReportCard(ReportCard reportCard) {
        this.reportCard = reportCard;
    }

    @Override
    public String toString() {
        return "Student : "+getEmail()+", Id : "+getId();
    }

}
