package application.form;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateClassForm { // Form que recebe o turno da classe para criar uma classe nova!

    @NotBlank
    private String classShift;


}
