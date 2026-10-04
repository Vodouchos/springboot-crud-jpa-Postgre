package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.service.UpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class UpdateController {
    private final UpdateService updateService;
    @Scheduled(cron = "0 0/5 * * * ?")
    public void autoUpdate(){
        log.info("CRON Update");
        updateService.updateRSSItemRepository();
    }
/*
    @GetMapping(value = "/update")
    public void updateAll(){
        log.info("Called updateAll");
        updateService.updateRSSItemRepository();
    }
    @GetMapping(value = "/update/{id}")
    public void updateId(@PathVariable int id){
        log.info("Called update id={}",id);
        updateService.updateRSSItemRepository(id);
    }
*/
}
