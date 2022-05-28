package application.form;

import javax.validation.constraints.NotBlank;

public class CreateClassForm { // Form que recebe o turno da classe para criar uma classe nova!

    @NotBlank
    private String classShift;

    public String getClassShift() {
        return classShift;
    }

    public void setClassShift(String classShift) {
        this.classShift = classShift;
    }
}
