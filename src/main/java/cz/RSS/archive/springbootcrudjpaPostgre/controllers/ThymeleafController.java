package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import cz.RSS.archive.springbootcrudjpaPostgre.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ThymeleafController {
    private final ItemService itemService;
    @GetMapping("/news-all")
    public String getSelected(Model model){
        //TODO: all instead of 1
        model.addAttribute("items", itemService.getSelection(List.of(1)));
        return "viewNews";
    }
    @GetMapping("/news")
    public String getSelectedPaged(Model model, @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "15") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);//page 1 has index of 0

        //TODO: selected instead list of 1
        Page<RSSItem> itemsPage = itemService.getSelection(List.of(1), pageable);

        model.addAttribute("items", itemsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", itemsPage.getTotalPages());
        return "viewNews";
    }
}
