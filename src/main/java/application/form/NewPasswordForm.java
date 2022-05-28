package application.form;

import javax.validation.constraints.NotBlank;

public class NewPasswordForm { // Form que recebe uma nova senha pelo estudante que queira mudá-lá. Mais para frente essa funcionalidade pode ser usada pelo professor também!

    @NotBlank
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
