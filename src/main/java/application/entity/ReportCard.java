package application.entity;


import application.entity.users.Student;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "tb_report_card")
public class ReportCard implements Serializable {  // Classe do Banco - > ReportCard |  Representa os boletim  de cada aluno |
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double grade1;
    private Double grade2;
    private Double grade3;


    @OneToOne
    @MapsId
    private Student student; // Um boletim tem um aluno associado á ele.

    @Enumerated(EnumType.STRING)
    private Situation situation; // Um boletim tem um status : APROVADO/REPROVADO


    public ReportCard() {
        setInitialInformations();
    }


    public double getAverageStudent() {
        double average = (grade1 + grade2 + grade3) / 3;
        return average;
    }

    private void setInitialInformations() {
        this.grade1 = 0.0;
        this.grade2 = 0.0;
        this.grade3 = 0.0;
        this.situation = Situation.DISAPPROVED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Double getGrade1() {
        return grade1;
    }

    public void setGrade1(Double grade1) {
        this.grade1 = grade1;
    }

    public Double getGrade2() {
        return grade2;
    }

    public void setGrade2(Double grade2) {
        this.grade2 = grade2;
    }

    public Double getGrade3() {
        return grade3;
    }

    public void setGrade3(Double grade3) {
        this.grade3 = grade3;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

}
