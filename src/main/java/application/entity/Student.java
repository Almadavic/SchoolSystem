package application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serial;

@Entity
@Table(name="tb_students")
public class Student extends User{

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private ReportCard reportCard;

    @JsonIgnore
    @ManyToOne
    private ClassRoom classRoom;

    public Student() {

    }

    public Student(String nome, String email, String password,ClassRoom classRoom) {
        super(nome, email, password);
        this.classRoom=classRoom;
        this.reportCard=new ReportCard();
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
}
