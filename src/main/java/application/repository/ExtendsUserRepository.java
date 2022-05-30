package application.repository;

import application.entity.users.User;


import java.util.List;
import java.util.Optional;

public interface ExtendsUserRepository  {

    List<? extends User> findAllWhereClassRoomIsNull();

}
