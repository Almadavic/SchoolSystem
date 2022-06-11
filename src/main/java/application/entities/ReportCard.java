package application.entities;


import application.entities.enums.Situation;
import application.entities.users.Student;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "tb_report_card")
public class ReportCard implements Serializable {  // Classe do Banco - > ReportCard |  Representa os boletim  de cada aluno |
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
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
        setInitialInformation();
    }


    public double getAverageStudent() {
        return (grade1 + grade2 + grade3) / 3;
    }

    private void setInitialInformation() {
        this.grade1 = 0.0;
        this.grade2 = 0.0;
        this.grade3 = 0.0;
        this.situation = Situation.DISAPPROVED;
    }

}
