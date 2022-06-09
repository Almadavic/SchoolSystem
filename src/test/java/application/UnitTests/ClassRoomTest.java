package application.UnitTests;

import application.entities.ClassRoom;
import application.entities.ReportCard;
import application.entities.enums.ClassShift;
import application.entities.users.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClassRoomTest {

    private ClassRoom classRoom;

    @BeforeAll
    public void instantiateClassRoom() {

        classRoom = new ClassRoom('B', ClassShift.AFTERNOON);
        addStudentPopulation();

    }

    @Test

    public void addStudent() {

        Student s3 = new Student("Guilherme", "guilherme@hotmail.com", "121456", null);
        ReportCard r3 = new ReportCard();
        r3.setGrade1(5.0);
        r3.setGrade2(6.0);
        r3.setGrade3(4.0);
        s3.setReportCard(r3);

        Assertions.assertEquals(1, classRoom.getStudents().size());
        classRoom.addStudent(s3);
        Assertions.assertEquals(2, classRoom.getStudents().size());
    }


    @Test
    public void removeStudent() {

        Student student = classRoom.getStudents().get(1);
        classRoom.removeStudent(student);
        Assertions.assertEquals(1, classRoom.getStudents().size());
    }


    @Test
    public void averageClass() {

        double averageClass = classRoom.getAverageClass();
        Assertions.assertEquals(5, averageClass);
    }


    private void addStudentPopulation() {
        Student s1 = new Student("Maria", "maria@hotmail.com", "121456", null);
        ReportCard r1 = new ReportCard();
        r1.setGrade1(5.0);
        r1.setGrade2(5.0);
        r1.setGrade3(5.0);
        s1.setReportCard(r1);
        Student s2 = new Student("Guilherme", "guilherme@hotmail.com", "121456", null);
        ReportCard r2 = new ReportCard();
        r2.setGrade1(5.0);
        r2.setGrade2(6.0);
        r2.setGrade3(4.0);
        s2.setReportCard(r2);
        classRoom.addStudent(s2);
        classRoom.addStudent(s2);
    }

}
