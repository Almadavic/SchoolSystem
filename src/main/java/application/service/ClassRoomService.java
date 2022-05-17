package application.service;

import application.dto.ClassRoomDto;
import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.entity.ClassRoom;
import application.entity.Student;
import application.entity.Teacher;
import application.repository.ClassRoomRepository;
import application.repository.StudentRepository;
import application.repository.TeacherRepository;
import application.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService {

    @Autowired
    private ClassRoomRepository clasRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public Page<ClassRoomDto> findAll(Pageable pagination) {
        Page<ClassRoom> classes = clasRepository.findAll(pagination);
        Page<ClassRoomDto> classesRoomDtos = classes.map(ClassRoomDto::new);
        return classesRoomDtos;
    }

    public ClassRoomDto findById(Long id) {
        ClassRoom classRoom = errorNotFoundOrReturnEntity(id);
        ClassRoomDto classRoomDto = new ClassRoomDto(classRoom);
        return classRoomDto;
    }

    public List<StudentDto> findStudentsByClass(Long id) {
       List<Student> students = studentRepository.findStudentsByClass(id);
       List<StudentDto> studentDtos = convertListFromEntityToDto(students);
       return studentDtos;
    }

    public StudentDto findStudentById(Long idClass, Long idStudent) {
        return new StudentDto();
    }

    public TeacherDto findTeacher(Long idClass) {
        return new TeacherDto();
    }

    private List<StudentDto> convertListFromEntityToDto(List<Student> students) {
        List<StudentDto> studentsDtos = new ArrayList<>();
        for (Student student : students) {                              // Transformar em Stream
            studentsDtos.add(new StudentDto(student));
        }
        return studentsDtos;
    }

    private ClassRoom errorNotFoundOrReturnEntity(Long id) {
        Optional<ClassRoom> classRoom = clasRepository.findById(id);
        if (classRoom.isEmpty()) {
            throw new ResourceNotFoundException("Class not found");
        }
        return classRoom.get();
    }
}
