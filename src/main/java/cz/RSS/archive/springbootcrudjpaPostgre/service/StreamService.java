package cz.RSS.archive.springbootcrudjpaPostgre.service;

import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
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

import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
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
    public void addStream(String name, String url){
        if (!testRssUrlString(url)) {
            log.error("invalid RSS URL={}", url);
            throw new InvalidRssUrlException("Invalid URL");
        }
        try {
            streamRepo.save(new RStream(name,url));
        } catch (DataIntegrityViolationException ex) {
            log.error("Feed already exists URL={}", url);
            throw new DuplicateRssException("Feed already exists");
        }
    }

    public static boolean testRssUrlString(String urlString){
        try {
            URL url = new URL(urlString); //Mallformed URL throws
            URI uri = URI.create(urlString);
            String host = uri.getHost();

            if (!uri.getScheme().matches("(http|https)")) return false;
            if (host == null || host.isBlank()) return false;
            if (host.equalsIgnoreCase("localhost")) return false;

            InetAddress address = InetAddress.getByName(host);
            if (address.isAnyLocalAddress()
                || address.isLoopbackAddress()
                || address.isLinkLocalAddress()
                || address.isSiteLocalAddress()
                ) return false;

            new SyndFeedInput().build(new XmlReader(url));
            return true;
        } catch (Exception ex){
            return false;
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
