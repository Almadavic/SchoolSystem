package application.form;

import com.sun.istack.NotNull;

public class AddRemoveStudentParentForm { // Form Parent pois os 2 forms, de add e remover da lista precisam do msm atributo.
                                             // Só dividi as entidades para ficar mais claro(POR CONTA DO NOME) ! Sem muita precisão
    @NotNull
    private Long idStudent;

    public Long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }
}
