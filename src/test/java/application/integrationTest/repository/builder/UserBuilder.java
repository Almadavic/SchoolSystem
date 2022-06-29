package application.integrationTest.repository.builder;

import application.entity.ClassRoom;
import lombok.Getter;


@Getter
public abstract class UserBuilder<T> { // Classe Parent ! Cria um usuário. || Cadeia de métodos.

    private String name;
    private String email;
    private String password;

    private ClassRoom classRoom;

    public UserBuilder setNome(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
        return this;
    }

    public abstract T create(); // Método abstract, as classes que herdam vão ter que implementar.

}
