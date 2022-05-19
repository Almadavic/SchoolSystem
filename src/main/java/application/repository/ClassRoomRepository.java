package application.repository;

import application.entity.ClassRoom;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom,Long> {
    @Cacheable(value = "classRoomList")
    Page<ClassRoom> findAll(Pageable pageable);
}
