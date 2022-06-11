package application.integrationTests.repositories;

import application.entities.Responsibility;
import application.repositories.ResponsibilityRepository;
import application.integrationTests.repositories.interfaces.GeneralExtendsRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;


@SpringBootTest
@Profile(value = "test")
public class ResponsibilityRepositoryTest implements GeneralExtendsRepositoryTest { // TESTE DE INTEGRAÇÃO

    @Autowired
    private ResponsibilityRepository responsibilityRepository;

    @Test
    @Override
    public void save() { // Salva uma responsibility no banco.

        Responsibility responsibility = new Responsibility("Testing Responsibility");
        responsibilityRepository.save(responsibility);
        Long idResponsibility = responsibility.getId();
        Responsibility responsibilityDataBase = responsibilityRepository.findById(idResponsibility).get();
        Assertions.assertEquals(idResponsibility, responsibilityDataBase.getId());
        responsibilityRepository.delete(responsibilityDataBase);
    }
}
