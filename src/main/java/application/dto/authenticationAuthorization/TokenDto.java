package application.dto.authenticationAuthorization;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TokenDto { // Esse DTO tem como objetivo retornar para o usuário o HASH token e o type pro usuario quando ele se autentica
	private String token;
	private String type;

	public TokenDto(String token, String type) {
		this.token = token;
		this.type = type;
	}

}
