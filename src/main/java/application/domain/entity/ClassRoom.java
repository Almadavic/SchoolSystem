package application.domain.entity;

import application.domain.enumerated.ClassShift;
import application.domain.entity.user.Student;
import application.domain.entity.user.Teacher;
import application.domain.vo.ReportCardVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_classes")
public class ClassRoom { // Classe do Banco - > ClassRoom | Entidade Principal | Representa as Salas de Aula do Sistema |

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private Character letter;
    @Enumerated(EnumType.STRING)
    private ClassShift classShift;  // Uma classe tem um turno.

    @OneToOne(mappedBy = "classRoom", cascade = CascadeType.ALL)
    private Teacher teacher;       // Uma classe tem 1 teacher


    @OneToMany(mappedBy = "classRoom")
    @Setter(AccessLevel.NONE)
    public List<Student> students = new ArrayList<>(); // Uma classe tem uma lista de alunos

    public ClassRoom(Character letter, ClassShift classShift) {
        this.letter = Character.toUpperCase(letter);
        this.classShift = classShift;
    }

    public void addStudent(Student student) { // Adiciona um estudante
        students.add(student);
    }

    public void removeStudent(Student student) {     // Remove um estudante
        students.remove(student);
    } // Remove um estudante

    public double getAverageClass() {          // Método que retorna a média total dos alunos da Classe !
        double sumStudentsAverage = students.stream().map(rc -> rc.getReportCard()).mapToDouble(ReportCardVO::getAverageStudent).sum();
        return sumStudentsAverage / students.size();
    }

}
