package application.form;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;


public class NewGradesForm { // Form que recebe as notas dos alunos(atualizada) para passar para um boletim monitorado pela JPA.

    @NotBlank
    private Double grade1;
    @NotBlank
    private Double grade2;
    @NotBlank
    private Double grade3;

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
}
