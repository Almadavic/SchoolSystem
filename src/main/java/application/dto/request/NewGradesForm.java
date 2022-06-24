package application.dto.request;

import lombok.Getter;

@Getter
public class NewGradesForm { // Form que recebe as notas dos alunos(atualizada) para passar para um boletim monitorado pela JPA.

    private Double grade1;

    private Double grade2;

    private Double grade3;

}
