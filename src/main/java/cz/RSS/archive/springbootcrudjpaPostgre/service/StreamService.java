package cz.RSS.archive.springbootcrudjpaPostgre.service;

import cz.RSS.archive.springbootcrudjpaPostgre.model.Stream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StreamService {
    @Autowired
    private StreamRepository streamRepo;

    public List<Stream> getAll(){
        return streamRepo.findAll();
    }
    public Stream getStream(int id){
        return streamRepo.getReferenceById(id);
    }
    public void addStream(String name, String url){
        url = url.replaceFirst("^(https://www\\.|http://www\\.|http://|https://|www\\.)","");
        streamRepo.save(new Stream(name,url));
    }
    public void removeStream(int id){
        streamRepo.deleteById(id);
    }


}
