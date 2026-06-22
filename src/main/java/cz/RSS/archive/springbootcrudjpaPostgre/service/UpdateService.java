package cz.RSS.archive.springbootcrudjpaPostgre.service;

import com.rometools.rome.feed.synd.SyndEntry;
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private void updateRSSItemRepository(RStream rStream) {
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(rStream.getUrl())));

            int streamId = rStream.getId();
            Optional<RSSItem> newestItem = itemRepo.findFirstByStreamIdOrderByPubDateDesc(streamId);
            Date newestDBEntry = newestItem.map(RSSItem::getPubDate).orElse(new Date(0L));

            logger.info("RSS " + rStream.getName() + " loaded. Initializing update. StreamId: " + streamId + " newestEntry: " + newestDBEntry);

            List<SyndEntry> newEntries = new java.util.ArrayList<>(feed.getEntries().stream()
                    .filter(x -> x.getPublishedDate().after(newestDBEntry))
                    .sorted(Comparator.comparing(SyndEntry::getPublishedDate).reversed())
                    .toList());
            if (!newEntries.isEmpty() && newestItem.isPresent()){
                // newEntries.get(0).getUri() funguje pro ČTK
                if (newEntries.get(0).getUri().equals(newestItem.get().getPermaLink())){
                    newEntries.remove(0);
                }
            }

            for (SyndEntry entry : newEntries) {itemRepo.save(new RSSItem(streamId, entry));}

            for (SyndEntry entry : newEntries) {
                int retryCount = 0;
                int maxRetries = 100;
                boolean saved = false;
                Date pubDate = entry.getPublishedDate();

                while (!saved && retryCount < maxRetries) {
                    try {
                        itemRepo.save(new RSSItem(streamId, entry));
                        saved = true;
                    } catch (Exception ex) {
                        if (ex.getMessage().contains("duplicate key")) {
                            retryCount++;
                            entry.setPublishedDate(new Date(entry.getPublishedDate().getTime() + 1));
                            logger.info("Duplicate key for RSS item in stream " + rStream.getName() +
                                    ". Retrying with adjusted pubDate: " + entry.getPublishedDate() +
                                    ". Attempt: " + (retryCount + 1));
                        } else {
                            throw ex; // Rethrow if not a duplicate key or max retries reached
                        }
                    }
                }
                if (!saved) logger.error("Failed to save RSS item for stream " + rStream.getName() + " after " + maxRetries + " attempts with pubDate: " + pubDate);
            }

            logger.info("New entries of RSS " + rStream.getName() + " saved to DB. Count: " + newEntries.size());

        } catch (Exception ex) {
            logger.error("RSS id: " + rStream.getId() + " failed to load feed. Error: " + ex.getMessage(), ex);
        }
    }

}
