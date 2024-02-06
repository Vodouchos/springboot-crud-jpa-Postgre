package cz.RSS.archive.springbootcrudjpaPostgre.resource;

import cz.RSS.archive.springbootcrudjpaPostgre.model.Stream;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/Streams")
public class RssStreamResource {

    @GetMapping(value = "/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<Stream> getAll(){
        Stream stream1 = new Stream(1L,"teststream", "ccshmbhmb");
        Link link= WebMvcLinkBuilder.linkTo(RssStreamResource.class).slash(stream1.getName()).withSelfRel();
        stream1.add(link);
        return Arrays.asList(stream1, stream1);
    }
}
