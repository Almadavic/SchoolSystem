package application.form;

import com.sun.istack.NotNull;

public class SetTeacherForm { // Form que recebe o id de um Teacher para setar em uma sala específica!

    @NotNull
    private Long idTeacher;


    public Long getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(Long idTeacher) {
        this.idTeacher = idTeacher;
    }
}
