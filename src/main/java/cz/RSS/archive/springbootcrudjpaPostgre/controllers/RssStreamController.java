package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RStream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import cz.RSS.archive.springbootcrudjpaPostgre.service.StreamService;
import cz.RSS.archive.springbootcrudjpaPostgre.service.UpdateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/streams")
public class RssStreamController {
    private final StreamRepository streamRepo;
    private final StreamService streamService;
    Logger logger = LoggerFactory.getLogger(RssStreamController.class);

    @GetMapping(value = "/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<RStream> getAll(){
        logger.info("getAll called");
        return streamService.getAll();
    }
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public RStream getStream(@PathVariable int id){
        logger.info("getStream called. ID: " + id);
        return streamService.getStream(id);
    }
    @PostMapping(value = "/addstream")
    public int addStream(@RequestParam("name") String name, @RequestParam("url") String url){
        logger.info("addStream called. name: " + name + " url: " + url);
        return streamService.addStream(name,url);

    }
    @PostMapping (value = "/removestream") //Post because basic HTML form does not support delete
    public int removeStream(@RequestParam("id") int id){
        logger.info("removeStream called. id: " + id);
        streamService.removeStream(id);
        return 200;
    }
    @PostMapping (value = "/teststream")
    public String testStream(@RequestParam("url") String url){
        logger.info("testStream called. url: " + url);
        return UpdateService.returnRawFeed(url);
    }
}
