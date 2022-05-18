package application.dto;

import application.entity.ReportCard;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"frequency","grade1","grade2","grade3","averageGrade"})
public class ReportCardDto {

    @JsonProperty("frequency")
    private Double frequency;
    @JsonProperty("grade1")
    private Double grade1;
    @JsonProperty("grade2")
    private Double grade2;
    @JsonProperty("grade3")
    private Double grade3;

    public ReportCardDto() {

    }

    public ReportCardDto(ReportCard reportCard) {
        this.frequency=reportCard.getFrequency();
        this.grade1= reportCard.getGrade1();
        this.grade2= reportCard.getGrade2();
        this.grade3= reportCard.getGrade3();
    }

    @JsonProperty("averageGrade")
    public Double getAverageGrade() {
        double average = (grade1+grade2+grade3)/3;
        return Math.round(average*10.0)/10.0;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public Double getGrade1() {
        return grade1;
    }

    public void setGrade1(Double grade1) {
        this.grade1 = grade1;
    }

    public Double getGrade2() {
        return grade2;
    }

    public void setGrade2(Double grade2) {
        this.grade2 = grade2;
    }

    public Double getGrade3() {
        return grade3;
    }

    public void setGrade3(Double grade3) {
        this.grade3 = grade3;
    }

}
