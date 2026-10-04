package cz.RSS.archive.springbootcrudjpaPostgre.service;

import cz.RSS.archive.springbootcrudjpaPostgre.exeption.DuplicateRssException;
import cz.RSS.archive.springbootcrudjpaPostgre.exeption.InvalidRssUrlException;
import cz.RSS.archive.springbootcrudjpaPostgre.model.RStream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamService {
    private final StreamRepository streamRepo;
    private final ItemRepository itemRepo;
    private final UpdateService updateService;

    public List<RStream> getAll(){
        return streamRepo.findAll();
    }
    public RStream getStream(int id){
        Optional<RStream> optionalRStream = streamRepo.findById(id);
        return optionalRStream.orElse(null);
    }
    public void addStream(String name, String url){
        if (updateService.isRssUrlStringNotValid(url)) {
            log.error("invalid RSS URL={}", url);
            throw new InvalidRssUrlException("Invalid URL");
        }
        try {
            String domain = URI.create(url).getHost();
            RStream stream = streamRepo.save(new RStream(name,url,domain));
            log.info("RSS Stream saved into DB. Starting initial update. streamId={}", stream.getId());
            updateService.updateRSSItemRepository(stream.getId());
        } catch (DataIntegrityViolationException ex) {
            log.error("Feed already exists URL={}", url);
            throw new DuplicateRssException("Feed already exists");
        }
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
