package application.forms;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class AddRemoveStudentForm { // Form Parent pois os 2 forms, de add e remover da lista precisam do msm atributo.
                                             // Só dividi as entidades para ficar mais claro(POR CONTA DO NOME) ! Sem muita precisão
    @NotNull
    private Long idStudent;


}
