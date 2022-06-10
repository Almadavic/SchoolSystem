package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport // Aceita informações vinda do FRONT ( COMO AS CONF DA PAGE)
@SpringBootApplication // APONTA QUE É A CLASSE PRINCIPAL, QUE TEM O MÉTODO MAIN
@EnableCaching   // ATIVA O CACHE !
public class Program {

    public static void main(String[] args) {
        SpringApplication.run(Program.class, args);
    }
}
