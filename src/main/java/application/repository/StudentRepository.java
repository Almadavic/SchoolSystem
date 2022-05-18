package application.repository;

import application.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

         @Query(value = "select tb_users.id, tb_users.email, tb_users.name, tb_users.password, tb_students.id, tb_students.class_room_id, " +
                 "tb_report_card.frequency,tb_report_card.grade1,tb_report_card.grade2,tb_report_card.grade3 " +
                 " from tb_users,tb_students, tb_report_card WHERE class_room_id = :id",nativeQuery = true)         // refatorar
         List<Student> findStudentsByClass(Long id);
}
