package application.repository;

import application.dto.UserDto;
import application.entity.users.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // Repositorio de Usuários ( No geral)
    Optional<User> findByEmail(String email);

    List<User> findByRolesName(String rolesName);

    @Override
    @Cacheable(value = "usersList")
    List<User> findAll();

}
