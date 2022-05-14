package application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_students")
public class Student extends User{

    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double frequency;

    @JsonIgnore
    @ManyToOne
    private ClassRoom classRoom;

    public Student() {
         setZero();
    }

    public Student(String nome, String email, String senha,ClassRoom classRoom) {
        super(nome, email, senha);
        setZero();
        this.classRoom=classRoom;
    }

    public void setZero() {
        this.nota1=0.0;
        this.nota2=0.0;
        this.nota3=0.0;
        this.frequency=0.0;
    }

    public double getAverageStudent() {
        double average = (nota1+nota2+nota3)/3;
        return average;
    }

    public Double getNota1() {
        return nota1;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
    }

    public Double getNota2() {
        return nota2;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
    }

    public Double getNota3() {
        return nota3;
    }

    public void setNota3(Double nota3) {
        this.nota3 = nota3;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }
}
