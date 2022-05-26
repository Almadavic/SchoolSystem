package application.form.authenticationAuthorization;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


// Form é praticamente a mesma coisa que DTO porém os Form são das requisições que chegam, e o DTOs são as respostas que voltam!
public class LoginForm { // Form que representa o Login do usuário, email e senha!

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsernamePasswordAuthenticationToken toConvert() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}

}
