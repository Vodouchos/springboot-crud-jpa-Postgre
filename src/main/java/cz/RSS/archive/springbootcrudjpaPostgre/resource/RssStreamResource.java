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
    public int addStream(@RequestParam("name") String name, @RequestParam("url") String url){
        url = url.replaceFirst("^(https://www\\.|http://www\\.|http://|https://|www\\.)","");
        streamRepo.save(new Stream(name,url));
        return 200;
    }
    @PostMapping(value = "/removestream")
    public int removeStream(@RequestParam("id") int id){
        streamRepo.deleteById(id);
        return 200;
    }
}
