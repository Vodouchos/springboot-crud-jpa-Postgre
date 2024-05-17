package cz.RSS.archive.springbootcrudjpaPostgre.resource;

import cz.RSS.archive.springbootcrudjpaPostgre.model.Stream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import cz.RSS.archive.springbootcrudjpaPostgre.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/streams")
public class RssStreamResource {
    @Autowired
    private StreamRepository streamRepo;
    @Autowired
    private StreamService streamService;
    @GetMapping(value = "/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<Stream> getAll(){
        return streamService.getAll();
    }
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public Stream getStream(@PathVariable int id){
        return streamService.getStream(id);
    }
    @PostMapping(value = "/addstream")
    public int addStream(@RequestParam("name") String name, @RequestParam("url") String url){
        streamService.addStream(name,url);
        return 201;
    }
    @DeleteMapping (value = "/removestream")
    public int removeStream(@RequestParam("id") int id){
        streamService.removeStream(id);
        return 200;
    }
}
