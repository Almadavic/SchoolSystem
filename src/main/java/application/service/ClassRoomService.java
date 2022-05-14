package application.service;

import application.dto.ClassRoomDto;
import application.entity.ClassRoom;
import application.repository.ClassRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClassRoomService {

    @Autowired
    private ClassRoomRepository clasRepository;

    public Page<ClassRoomDto> findAll(Pageable pagination) {
        Page<ClassRoom> classes = clasRepository.findAll(pagination);
        Page<ClassRoomDto> classesRoomDtos = classes.map(ClassRoomDto::new);
        return classesRoomDtos;
    }


}
