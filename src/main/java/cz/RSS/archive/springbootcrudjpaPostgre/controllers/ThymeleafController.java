package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    @GetMapping("/view")
    public String getSelected(Model model){
        return "viewItems";
    }
}
