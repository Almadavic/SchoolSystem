package application.repositories;

import application.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // Repositorio de Usuários ( No geral)
    Optional<User> findByEmail(String email);

    List<User> findByRolesName(String rolesName);

    User findByPassword(String krai);


}
