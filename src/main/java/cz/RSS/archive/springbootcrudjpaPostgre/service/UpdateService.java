package cz.RSS.archive.springbootcrudjpaPostgre.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import cz.RSS.archive.springbootcrudjpaPostgre.model.RStream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UpdateService {
    private LocalDateTime lastUpdate;
    @Autowired
    private StreamRepository streamRepo;
    @Autowired
    private ItemRepository itemRepo;

    public void updateRSSItemRepository(){
        streamRepo.findAll().forEach(this::updateRSSItemRepository);
    };
    public void updateRSSItemRepository(int id){
        streamRepo.findById(id).ifPresent(this::updateRSSItemRepository);
    };
    public void updateRSSItemRepository(RStream stream){
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(stream.getUrl())));

            System.out.println(feed.getTitle());

        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage());
        }
    }

}
