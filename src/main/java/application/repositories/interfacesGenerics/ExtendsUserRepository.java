package application.repositories.interfacesGenerics;

import application.entities.users.User;


import java.util.List;

public interface ExtendsUserRepository { // Interface onde os repositories de usuários vão herdar - > StudentRepository, TeacherRepository.

    List<? extends User> findAllWhereClassRoomIsNull(); // Método que será herdado.

}
