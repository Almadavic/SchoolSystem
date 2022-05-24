package application.service.serviceLayer;

import application.dto.TeacherDto;
import application.entity.users.Teacher;
import application.repository.TeacherRepository;
import application.service.exception.database.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;


    public Page<TeacherDto> findAll(Pageable pagination) {
        Page<Teacher> teachers = teacherRepository.findAll(pagination);
        Page<TeacherDto> teacherDtos = teachers.map(TeacherDto::new);
        return teacherDtos;
    }


    public TeacherDto findByID(Long id) {
        Teacher teacher = errorNotFoundOrReturnEntity(id);
        return new TeacherDto(teacher);
    }

    private Teacher errorNotFoundOrReturnEntity(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()) {
            throw new ResourceNotFoundException("Student not found");
        }
        return teacher.get();
    }
}
