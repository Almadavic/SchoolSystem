package application.dto;

import application.entity.ClassRoom;
import application.entity.ClassShift;
import application.entity.users.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = {"idClass", "letter", "classShift", "teacher", "numberStudents", "students", "averageClassGrade"})
public class ClassRoomDto {    // DTO da classe ClassRoom

    @JsonProperty(value = "idClass")
    private Long idClass;
    @JsonProperty(value = "letter")
    private Character letter;

    @JsonProperty(value = "classShift")
    private ClassShift classShift;
    @JsonProperty(value = "teacher")
    private TeacherDto teacherDto;

    @JsonProperty(value = "numberStudents")
    private Integer numberStudents;
    @JsonProperty(value = "students")
    private  List<StudentDto> studentsDto = new ArrayList<>();
    @JsonProperty(value = "averageClassGrade")
    private Double averageClassGrade;

    public ClassRoomDto(ClassRoom classRoom) {      // Transformando uma ClassRoom em ClassRoomDto
        this.idClass = classRoom.getId();
        this.letter = classRoom.getLetter();
        this.classShift = classRoom.getClassShift();
        this.studentsDto = convertList(classRoom.getStudents());
        Collections.sort(studentsDto);
        this.numberStudents = studentsDto.size();
        this.averageClassGrade = classRoom.getAverageClass();
        if (classRoom.getTeacher() != null) {
            this.teacherDto = new TeacherDto(classRoom.getTeacher());
        }
    }

    private List<StudentDto> convertList(List<Student> students) {
        return students.stream().map(StudentDto::new).collect(Collectors.toList());
    }


}
