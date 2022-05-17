package application.repository;

import application.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

         @Query(value = "SELECT * FROM tb_students s WHERE s.class_room_id = :id",nativeQuery = true)
         List<Student> findStudentsByClass(Long id);
}
