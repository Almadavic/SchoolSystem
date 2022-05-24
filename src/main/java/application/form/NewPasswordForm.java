package application.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NewPasswordForm {

    @NotNull
    @NotEmpty
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
