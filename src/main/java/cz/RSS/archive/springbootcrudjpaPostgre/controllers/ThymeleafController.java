package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ThymeleafController {

    @Autowired
    private ItemService itemService;
    @GetMapping("/view")
    public String getSelected(Model model){
        model.addAttribute("items", itemService.getSelection(List.of(1)));
        return "viewItems";
    }
}
