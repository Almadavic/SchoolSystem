package application.repository;

import application.domain.entity.user.Teacher;
import application.repository.interfaceRepository.ExtendsUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends ExtendsUserRepository, JpaRepository<Teacher, Long> { // Repositorio de professores!

    Optional<Teacher> findTeacherByClassRoomId(Long id);

    @Override
    @Query(value = "select * from tb_teachers  " +
            "inner join tb_users on tb_teachers.id = tb_users.id " +
            "inner join tb_users_roles on tb_users_roles.user_id = tb_users_roles.role_id " +
            "where tb_teachers.class_room_id is null", nativeQuery = true)
    List<Teacher> findAllWhereClassRoomIsNull();       // Retorna uma lista de professores que não tem classe.
}
