package application.entity;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_classes")
public class ClassRoom implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Character letter;

    @OneToOne(mappedBy="classRoom",cascade = CascadeType.ALL)
    private Teacher teacher;

    @OneToMany(mappedBy = "classRoom")
    public List<Student> students = new ArrayList<>();

    public ClassRoom() {

    }

    public ClassRoom(Character letter) {
        this.letter = letter;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public double getAverageClass() {
        double average = 0;
        for(Student student : students) {
            average = average+student.getReportCard().getAverageStudent();
        }
        return average;
    }

    public Long getId() {
        return id;
    }


    public Teacher getTeacher() {
        return teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }
}
