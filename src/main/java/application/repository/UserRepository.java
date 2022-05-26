package application.repository;

import application.entity.users.Teacher;
import application.entity.users.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // Repositorio de Usuários ( No geral)
    Optional<User> findByEmail(String email);

    Page<User> findByRolesName(Pageable pagination, String rolesName);

    @Cacheable(value = "usersList")
    Page<User> findAll(Pageable pagination);

}
