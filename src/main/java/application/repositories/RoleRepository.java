package application.repositories;

import application.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> { // Repositorio de Roles
    Optional<Role> findByName(String email); // Método que retorna uma ROLE por nome.
}
