package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RStream;
import cz.RSS.archive.springbootcrudjpaPostgre.service.StreamService;
import cz.RSS.archive.springbootcrudjpaPostgre.service.UpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/streams")
public class RssStreamController {
    private final StreamService streamService;

    @GetMapping(value = "/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<RStream> getAll(){
        log.info("getAll called");
        return streamService.getAll();
    }
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public RStream getStream(@PathVariable int id){
        log.info("getStream called. ID={} ", id);
        return streamService.getStream(id);
    }
    @PostMapping(value = "/addstream")
    public int addStream(@RequestParam("name") String name, @RequestParam("url") String url){
        log.info("addStream called. name={} url={}", name, url);
        return streamService.addStream(name,url);

    }
    @PostMapping (value = "/removestream") //Post because basic HTML form does not support delete
    public int removeStream(@RequestParam("id") int id){
        log.info("removeStream called. id={}", id);
        streamService.removeStream(id);
        return 200;
    }
    @PostMapping (value = "/teststream")
    public String testStream(@RequestParam("url") String url){
        log.info("testStream called. url={}", url);
        return UpdateService.returnRawFeed(url);
    }
}
