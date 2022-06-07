package application.repositories.interfacesGenerics;

import application.entities.users.User;


import java.util.List;

public interface ExtendsUserRepository  {

    List<? extends User> findAllWhereClassRoomIsNull();

}
