package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UpdateController {
    @Autowired
    private UpdateService updateService;

    @GetMapping(value = "/update")
    public void updateAll(){
        updateService.updateRSSItemRepository();
    }
    @GetMapping(value = "/update/{id}")
    public void updateId(@PathVariable int id){
        updateService.updateRSSItemRepository(id);
    }
}
