package cz.RSS.archive.springbootcrudjpaPostgre.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import cz.RSS.archive.springbootcrudjpaPostgre.controllers.RssStreamController;
import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import cz.RSS.archive.springbootcrudjpaPostgre.model.RStream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
public class UpdateService {
    @Autowired
    private StreamRepository streamRepo;
    @Autowired
    private ItemRepository itemRepo;
    Logger logger = LoggerFactory.getLogger(RssStreamController.class);

    public static boolean validURL(String url){
        try {
            new SyndFeedInput().build(new XmlReader(new URL(url)));
            return true;
        } catch (Exception ex){
            return false;
        }
    }
    public static String returnRawFeed(String url){
        try {
            return new SyndFeedInput().build(new XmlReader(new URL(url))).toString();
        } catch (Exception ex){
            return "Url invalid as RSS";
        }
    }
    public void updateRSSItemRepository(){
        streamRepo.findAll().forEach(this::updateRSSItemRepository);
    }
    public void updateRSSItemRepository(int id){
        streamRepo.findById(id).ifPresent(this::updateRSSItemRepository);
    }
    private void updateRSSItemRepository(RStream rStream){
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(rStream.getUrl())));

            int streamId = rStream.getId();
            Date newestDBEntry = itemRepo.findFirstByStreamIdOrderByPubDateDesc(streamId)
                    .map(RSSItem::getPubDate)//publication date of newest item
                    .orElse(new Date(0L));// if there is no item set to zero

            logger.info("RSS " + rStream.getName() + " loaded. Initializing update. StreamId: " +streamId + " newestEntry: " + newestDBEntry);
            feed.getEntries()
                    .stream()
                    .takeWhile(x -> x.getPublishedDate().after(newestDBEntry))
                    .forEach(x -> itemRepo.save(new RSSItem(streamId,x)));
            logger.info("New entries of RSS " + rStream.getName() + " saved to DB.");

        }
        catch (Exception ex) {
            logger.error("RSS id: "+ rStream.getId() + " failed to load feed. Error: "+ex.getMessage(), ex);
        }
    }

}
