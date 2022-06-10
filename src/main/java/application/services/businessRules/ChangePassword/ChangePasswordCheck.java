package application.services.businessRules.ChangePassword;

public interface ChangePasswordCheck { // Interface de regras de negócio relacionada ao alterar uma senha.

     void validation(String newPassword,String oldPassword); // Validação!
}
