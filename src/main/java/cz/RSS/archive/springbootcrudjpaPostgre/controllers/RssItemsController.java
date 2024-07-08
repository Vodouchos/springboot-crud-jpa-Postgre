package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class RssItemsController {
    @Autowired
    private ItemRepository itemRepo;
    @GetMapping(value = "/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<RSSItem> getAllItems(){
        return itemRepo.findAll();
    }
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public RSSItem getItem(@PathVariable int id){
        return itemRepo.getReferenceById(id);
    }

}
