package cz.RSS.archive.springbootcrudjpaPostgre.service;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RStream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamService {
    private final StreamRepository streamRepo;
    private final ItemRepository itemRepo;

    public List<RStream> getAll(){
        return streamRepo.findAll();
    }
    public RStream getStream(int id){
        Optional<RStream> optionalRStream = streamRepo.findById(id);
        return optionalRStream.orElse(null);
    }
    public int addStream(String name, String url){
        if (!UpdateService.validURL(url)) {
            log.error("invalid URL={}", url);
            return 400;
        }
        try {
            streamRepo.save(new RStream(name,url));
        } catch (ConstraintViolationException ex) {
            log.error("Duplicit url={}", url);
            return 400;
        }
        return 201; //case everything OK
    }
    @Transactional
    public void removeStream(int id){
        log.info("Delete Stream entries");
        itemRepo.deleteByStreamId(id);
        log.info("Stream entries deleted streamId={}", id);
        streamRepo.deleteById(id);
        log.info("Stream deleted streamId={}", id);
    }


}
