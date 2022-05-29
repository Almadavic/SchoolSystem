package application.repository;

import application.entity.Responsibility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsibilityRepository extends JpaRepository<Responsibility,Long> {

}
