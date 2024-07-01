package cz.RSS.archive.springbootcrudjpaPostgre.service;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RStream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StreamService {
    @Autowired
    private StreamRepository streamRepo;

    public List<RStream> getAll(){
        return streamRepo.findAll();
    }
    public RStream getStream(int id){
        Optional<RStream> optrstream = streamRepo.findById(id);
        return optrstream.orElse(null);
    }
    public void addStream(String name, String url){
        //todo url testing
        streamRepo.save(new RStream(name,url));
    }
    public void removeStream(int id){
        streamRepo.deleteById(id);
    }


}
