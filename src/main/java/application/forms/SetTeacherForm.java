package application.forms;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class SetTeacherForm { // Form que recebe o id de um Teacher para setar em uma sala específica!

    @NotNull
    private Long idTeacher;

}
