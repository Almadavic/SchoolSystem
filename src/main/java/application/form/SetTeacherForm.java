package application.form;

import com.sun.istack.NotNull;

public class SetTeacherForm {

    @NotNull
    private Long idTeacher;


    public Long getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(Long idTeacher) {
        this.idTeacher = idTeacher;
    }
}
