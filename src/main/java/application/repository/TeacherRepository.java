package application.repository;

import application.entity.users.Student;
import application.entity.users.Teacher;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> { // Repositorio de professores!

    @Cacheable(value = "teachersList")
    Page<Teacher> findAll(Pageable pagination);

    Optional<Teacher> findByClassRoomId(Long id);
}
