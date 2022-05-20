package application.repository;

import application.entity.ClassRoom;
import application.entity.users.Student;
import application.entity.users.Teacher;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Cacheable(value = "studentsListByClassRoom")
      Page<Student> findListStudentsByClassRoomId(Long id,Pageable pagination);

    @Cacheable(value = "studentsList")
    Page<Student> findAll(Pageable pagination);

}
