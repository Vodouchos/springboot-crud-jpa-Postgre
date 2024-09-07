package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.service.UpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UpdateController {
    @Autowired
    private UpdateService updateService;
    Logger logger = LoggerFactory.getLogger(RssStreamController.class);
    @Scheduled(cron = "0 0/5 * * * ?")
    public void autoUpdate(){
        logger.info("CRON Update");
        updateService.updateRSSItemRepository();
    }
    @GetMapping(value = "/update")
    public void updateAll(){
        logger.info("Called updateAll");
        updateService.updateRSSItemRepository();
    }
    @GetMapping(value = "/update/{id}")
    public void updateId(@PathVariable int id){
        logger.info("Called update id: " + id);
        updateService.updateRSSItemRepository(id);
    }
}
