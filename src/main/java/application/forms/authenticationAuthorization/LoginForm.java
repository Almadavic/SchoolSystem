package application.forms.authenticationAuthorization;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;


// Form é praticamente a mesma coisa que DTO porém os Form são das requisições que chegam, e o DTOs são as respostas que voltam!
@Getter
public class LoginForm { // Form que representa o Login do usuário, email e senha!

	@NotBlank
	private String email;

	@NotBlank
	private String password;


	public UsernamePasswordAuthenticationToken toConvert() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}

}
