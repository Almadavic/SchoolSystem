package application.entities;

import application.entities.enums.ClassShift;
import application.entities.users.Student;
import application.entities.users.Teacher;
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
public class ClassRoom implements Serializable { // Classe do Banco - > ClassRoom | Entidade Principal | Representa as Salas de Aula do Sistema |

    @Serial
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private Character letter;
    @Enumerated(EnumType.STRING)
    private ClassShift classShift;

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
    }

    public double getAverageClass() {          // Método que retorna a média total dos alunos da Classe !
        Double sumStudentsAverage = students.stream().map(rc -> rc.getReportCard()).mapToDouble(ReportCard::getAverageStudent).sum();
        double averageClass = sumStudentsAverage/students.size();
        return averageClass;
    }

}
