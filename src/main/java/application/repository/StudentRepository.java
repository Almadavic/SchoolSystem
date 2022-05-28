package application.repository;

import application.dto.StudentDto;
import application.entity.ClassRoom;
import application.entity.users.Student;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepository extends ExtendsUserRepository,JpaRepository<Student,Long> { // Repositorio de alunos


    @Cacheable(value = "studentsListByClassRoom")
    Page<Student> findListStudentsByClassRoomId(Long id, Pageable pagination);

    @Override
    @Cacheable(value = "studentsList")
    List<Student> findAll();

    @Override
    @Query(value = "select * from tb_students  " +
            "inner join tb_users on tb_students.id = tb_users.id " +
            "inner join tb_address on tb_users.id = tb_address.user_id " +
            "inner join tb_users_roles on tb_users_roles.users_id = tb_users_roles.roles_id " +
            "where tb_students.class_room_id is null",nativeQuery = true)
    List<Student> findAllWhereClassRoomIsNull();

}
