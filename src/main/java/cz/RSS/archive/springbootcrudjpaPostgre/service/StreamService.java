package cz.RSS.archive.springbootcrudjpaPostgre.service;

import cz.RSS.archive.springbootcrudjpaPostgre.model.Stream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class StreamService {
    @Autowired
    private StreamRepository streamRepo;

    public List<Stream> getAll(){
        return streamRepo.findAll();
    }
    public Stream getStream(int id){
        return streamRepo.getReferenceById(id);
    }
    public int addStream(String name,String url){
        url = url.replaceFirst("^(https://www\\.|http://www\\.|http://|https://|www\\.)","");
        streamRepo.save(new Stream(name,url));
        return 201;
    }
    public int removeStream(int id){
        streamRepo.deleteById(id);
        return 200;
    }


}
