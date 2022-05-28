package application.repository;

import application.entity.ClassRoom;
import application.entity.users.Student;
import application.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtendsUserRepository  {

    List<? extends User> findAllWhereClassRoomIsNull();
}
