package application.form;

import org.hibernate.validator.constraints.Range;



public class NewGradesForm {

    @Range(min = (long) 0.0, max = (long) 10.0)
    private Double grade1;
    @Range(min = (long) 0.0, max = (long) 10.0)
    private Double grade2;
    @Range(min = (long) 0.0, max = (long) 10.0)
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
