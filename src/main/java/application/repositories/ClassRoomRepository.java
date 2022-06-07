package application.repositories;

import application.entities.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom,Long> { // Repositorio de ClassRoom

}
