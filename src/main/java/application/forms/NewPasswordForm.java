package application.forms;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class NewPasswordForm { // Form que recebe uma nova senha pelo estudante que queira mudá-lá. Mais para frente essa funcionalidade pode ser usada pelo professor também!

    @NotBlank
    private String newPassword;


}
