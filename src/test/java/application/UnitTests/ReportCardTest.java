package application.UnitTests;

import application.entities.ReportCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReportCardTest { // TESTE DE UNIDADE da entidade ReportCard.


    private ReportCard rc;

    @BeforeAll
    public void instantiateReportCard() { // Instancia um ReportCard antes dos testes começarem.
        rc = new ReportCard();
        rc.setGrade1(6.0);
        rc.setGrade2(5.0);
        rc.setGrade3(4.0);

    }


    @Test
    public void averageStudent() { // Testa a média de notas de um determinado aluno.

        Assertions.assertEquals(5.0, rc.getAverageStudent()); // A média antes.
        rc.setGrade3(7.0); // Altera uma nota.
        Assertions.assertEquals(6, rc.getAverageStudent()); // A média depois.

    }


}
