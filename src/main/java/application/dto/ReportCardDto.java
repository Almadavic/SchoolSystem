package application.dto;

import application.entity.ReportCard;
import application.entity.Situation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = {"grade1", "grade2", "grade3", "averageGrade", "situation"})
public class ReportCardDto { // Dto da classe ReportCard


    @JsonProperty(value = "grade1")
    private Double grade1;
    @JsonProperty(value = "grade2")
    private Double grade2;
    @JsonProperty(value = "grade3")
    private Double grade3;
    @JsonProperty(value = "situation")
    private Situation situation;

    public ReportCardDto(ReportCard reportCard) {    // Transformando um ReportCard em ReportCardDto
        this.grade1 = reportCard.getGrade1();
        this.grade2 = reportCard.getGrade2();
        this.grade3 = reportCard.getGrade3();
        this.situation = reportCard.getSituation();
    }

    @JsonProperty("averageGrade")
    private Double getAverageGrade() {
        double average = (grade1 + grade2 + grade3) / 3;     // Método que retorna a média de notas do boletim de um aluno!
        double decimal = Math.round(average * 10.0) / 10.0; // Feito isso para ficar em decimal!
        return decimal;
    }

}
