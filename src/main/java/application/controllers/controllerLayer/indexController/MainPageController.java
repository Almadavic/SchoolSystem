package application.controllers.controllerLayer.indexController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController { // Controller apenas para fazer a página principal (HOME) da aplicação ser o swagger : (host/)

    @RequestMapping("/")
    public String index() {
        return "redirect:/swagger-ui/index.html";
    }
}
