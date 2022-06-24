package application.domain.vo;


import application.domain.enumerated.Situation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ReportCardVO {  //  Representa os boletim  de cada aluno |

    private Double grade1;
    private Double grade2;
    private Double grade3;

    @Enumerated(EnumType.STRING)
    private Situation situation; // Um boletim tem um status : APROVADO/REPROVADO


    public ReportCardVO() {
        setInitialInformation();
    }

    private void setInitialInformation() {
        this.grade1 = 0.0;
        this.grade2 = 0.0;
        this.grade3 = 0.0;
        this.situation = Situation.DISAPPROVED;
    }

    public double getAverageStudent() {
        return (grade1 + grade2 + grade3) / 3;
    }

}
