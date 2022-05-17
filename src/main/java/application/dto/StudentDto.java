package application.dto;

import application.entity.Student;
import application.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id","name","email","reportCard","roles"})
public class StudentDto extends UserDto {

    @JsonProperty(value = "reportCard")
    private ReportCardDto reportCardDto;

    public StudentDto() {

    }

    public StudentDto(Student student) {
        this.setId(student.getId());
        this.setName(student.getName());
        this.setEmail(student.getEmail());
        this.reportCardDto= new ReportCardDto(student.getReportCard());
        this.setAddressDto(new AddressDto(student.getAddress()));
        convertList(student.getAuthorities());
    }


    public ReportCardDto getReportCardDto() {
        return reportCardDto;
    }

    public void setReportCardDto(ReportCardDto reportCardDto) {
        this.reportCardDto = reportCardDto;
    }
}
