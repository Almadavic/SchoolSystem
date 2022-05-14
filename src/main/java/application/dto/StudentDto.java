package application.dto;

import application.entity.Student;
import application.entity.User;

public class StudentDto extends UserDto {

    private Double nota1;
    private Double nota2;
    private Double nota3;

    private Double averageGrade;

    private Double frequencia;





    public StudentDto() {

    }

    public StudentDto (Student student) {
        this.setId(student.getId());
        this.setUserName(student.getName());
        this.setEmail(student.getEmail());
        this.setNota1(student.getNota1());
        this.setNota2(student.getNota2());
        this.setNota3(student.getNota3());
        this.setAverageGrade(student.getAverageStudent());
        this.setFrequencia(student.getFrequency());
        convertList(student.getAuthorities());
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade=averageGrade;
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

    public Double getAverageStudent() {
        return averageGrade;
    }

    public Double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Double frequencia) {
        this.frequencia = frequencia;
    }
}
