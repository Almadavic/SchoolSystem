package application.dto.authenticationAuthorization;

public class TokenDto {

	private String token;
	private String type;

	public TokenDto() {

	}

	public TokenDto(String token, String type) {
		this.token = token;
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public String getType() {
		return type;
	}

}
