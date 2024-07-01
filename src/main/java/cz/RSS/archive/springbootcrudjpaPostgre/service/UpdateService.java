package cz.RSS.archive.springbootcrudjpaPostgre.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;

@Service
public class UpdateService {
    private LocalDateTime lastUpdate;
    @Autowired
    private StreamRepository streamRepo;
    @Autowired
    private ItemRepository itemRepo;

    public void updateRSSItemRepository(){
        updateRSSItemRepository(2);//todo update all
    };

    public void updateRSSItemRepository(int id){
        updateRSSItemRepository(id,streamRepo.getReferenceById(id).getUrl());
    };
    private void updateRSSItemRepository(int id, String url){
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(url)));

            System.out.println(feed.getLink());

        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage());
        }

    };
}
