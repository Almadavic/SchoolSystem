package application.repository;

import application.domain.entity.user.Student;
import application.repository.interfaceRepository.ExtendsUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudentRepository extends ExtendsUserRepository, JpaRepository<Student, Long> { // Repositorio de alunos

    Page<Student> findListStudentsByClassRoomId(Long id, Pageable pagination); // Retorna uma página de alunos passando o id da Classe.

    @Override
    @Query(value = "select * from tb_students  " +
            "inner join tb_users on tb_students.id = tb_users.id " +
            "inner join tb_users_roles on tb_users_roles.user_id = tb_users_roles.role_id " +
            "where tb_students.class_room_id is null", nativeQuery = true)
    List<Student> findAllWhereClassRoomIsNull();              // Retorna uma lista de alunos que não tem classe.

    @Query(value = "select * from tb_students " +
            "inner join tb_users on tb_students.id = tb_users.id " +
            "inner join tb_users_roles on tb_users_roles.user_id = tb_users_roles.role_id " +
            "where tb_students.class_room_id = :idClass AND tb_students.id = :idStudent", nativeQuery = true)
    Optional<Student> findStudentByClassId(Long idClass, Long idStudent); // Retorna um aluno associado a uma classe.

}
