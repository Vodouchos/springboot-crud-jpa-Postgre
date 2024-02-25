package cz.RSS.archive.springbootcrudjpaPostgre.resource;

import cz.RSS.archive.springbootcrudjpaPostgre.model.Stream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/streams")
public class RssStreamResource {
    @Autowired
    private StreamRepository streamRepo;
    @GetMapping(value = "/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<Stream> getAll(){
        return streamRepo.findAll();
        /* Stream stream1 = new Stream(1,"teststream", "ccshmbhmb");
        Link link= WebMvcLinkBuilder.linkTo(RssStreamResource.class).slash(stream1.getName()).withSelfRel();
        stream1.add(link);
        Stream stream2 = new Stream(2,"second", "cgee");
        Link link2= WebMvcLinkBuilder.linkTo(RssStreamResource.class).slash(stream2.getName()).withSelfRel();
        stream2.add(link2);
        return Arrays.asList(stream1,stream2);*/
    }
    @GetMapping(value = "/1", produces = MediaTypes.HAL_JSON_VALUE)
    public Stream getStream(){
        Stream stream1 = new Stream(1,"teststream", "ccshmbhmb");
        Link link= WebMvcLinkBuilder.linkTo(RssStreamResource.class).slash(stream1.getName()).withSelfRel();
        stream1.add(link);
        return stream1;
    }
    @PostMapping(value = "/addstream")
    public void addStream(@RequestParam String name, @RequestParam String url){
        streamRepo.save(new Stream(name,url));
    }
}
