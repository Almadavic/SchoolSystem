package application.UnitTests;

import application.entities.ReportCard;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReportCardTest {


    private ReportCard rc;

    @BeforeAll
    public  void instantiateReportCard() {
        rc = new ReportCard();
        rc.setGrade1(6.0);
        rc.setGrade2(5.0);
        rc.setGrade3(4.0);

    }


    @Test
    public void averageStudent() {

        Assertions.assertEquals(5.0,rc.getAverageStudent());
        rc.setGrade3(7.0);
        Assertions.assertEquals(6,rc.getAverageStudent());

    }


}
