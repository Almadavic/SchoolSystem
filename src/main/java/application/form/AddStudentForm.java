package application.form;

import com.sun.istack.NotNull;

public class AddStudentForm {

    @NotNull
    private Long idStudent;

    public Long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }
}
