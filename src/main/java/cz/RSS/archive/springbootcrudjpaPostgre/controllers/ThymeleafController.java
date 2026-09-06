package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ThymeleafController {
    private final ItemService itemService;
    @GetMapping("/news")
    public String getSelected(Model model){
        model.addAttribute("items", itemService.getSelection(List.of(1)));
        return "viewNews";
    }
}
