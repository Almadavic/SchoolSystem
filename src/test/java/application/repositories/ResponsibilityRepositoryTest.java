package application.repositories;

import application.entities.Responsibility;
import application.repositories.interfaces.GeneralExtendsRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class ResponsibilityRepositoryTest implements GeneralExtendsRepositoryTest {

    @Autowired
    private ResponsibilityRepository responsibilityRepository;

    @Test
    @Override
    public void  save() {

        Responsibility responsibility = new Responsibility("Testing Responsibility");
        responsibilityRepository.save(responsibility);
        Long idResponsibility = responsibility.getId();
        Responsibility responsibilityDataBase = responsibilityRepository.findById(idResponsibility).get();
        Assertions.assertEquals(idResponsibility,responsibilityDataBase.getId());
        responsibilityRepository.delete(responsibilityDataBase);
    }
}
