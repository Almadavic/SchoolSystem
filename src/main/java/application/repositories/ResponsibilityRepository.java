package application.repositories;

import application.entities.Responsibility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsibilityRepository extends JpaRepository<Responsibility,Long> { // Repositorio de Responsibility.

}
