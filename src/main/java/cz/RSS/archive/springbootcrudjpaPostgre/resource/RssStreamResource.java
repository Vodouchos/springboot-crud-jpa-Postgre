package cz.RSS.archive.springbootcrudjpaPostgre.resource;

import cz.RSS.archive.springbootcrudjpaPostgre.model.Stream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/streams")
public class RssStreamResource {
    @Autowired
    private StreamRepository streamRepo;
    @GetMapping(value = "/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<Stream> getAll(){
        return streamRepo.findAll();
    }
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public Stream getStream(@PathVariable int id){
        return streamRepo.getReferenceById(id);
    }
    @PostMapping(value = "/addstream")
    public void addStream(@RequestParam("name") String name, @RequestParam("url") String url){
        streamRepo.save(new Stream(name,url));
    }
    @PostMapping(value = "/removestream")
    public void removeStream(@RequestParam("id") int id){
        streamRepo.deleteById(id);
    }
}
