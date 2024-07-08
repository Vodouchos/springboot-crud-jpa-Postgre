package cz.RSS.archive.springbootcrudjpaPostgre.service;

import cz.RSS.archive.springbootcrudjpaPostgre.controllers.RssStreamController;
import cz.RSS.archive.springbootcrudjpaPostgre.model.RStream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StreamService {
    @Autowired
    private StreamRepository streamRepo;
    @Autowired
    private ItemRepository itemRepo;
    Logger logger = LoggerFactory.getLogger(RssStreamController.class);

    public List<RStream> getAll(){
        return streamRepo.findAll();
    }
    public RStream getStream(int id){
        Optional<RStream> optionalRStream = streamRepo.findById(id);
        return optionalRStream.orElse(null);
    }
    public int addStream(String name, String url){
        if (!UpdateService.validURL(url)) {
            logger.error("invalid URL: " + url);
            return 400;
        }
        try {
            streamRepo.save(new RStream(name,url));
        } catch (ConstraintViolationException ex) {
            logger.error("Duplicit url: " + url);
            return 400;
        }
        return 201; //case everything OK
    }
    public void removeStream(int id){
        logger.info("Delete Stream entries");
        itemRepo.deleteByStreamId(id);
        logger.info("Stream " + id + " entries deleted");
        streamRepo.deleteById(id);
        logger.info("Stream " + id + " deleted");
    }


}
