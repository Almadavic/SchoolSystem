package application.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateClassForm {

    @NotNull
    @NotEmpty
    private String classShift;

    public String getClassShift() {
        return classShift;
    }

    public void setClassShift(String classShift) {
        this.classShift = classShift;
    }
}
