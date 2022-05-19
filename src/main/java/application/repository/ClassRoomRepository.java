package application.repository;

import application.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom,Long> {

    //@Query("select s from students s join ")
   // Page<Student> findStudents(Long id)
}
