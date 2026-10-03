package cz.RSS.archive.springbootcrudjpaPostgre.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import cz.RSS.archive.springbootcrudjpaPostgre.model.RStream;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateService {
    private final StreamRepository streamRepo;
    private final ItemRepository itemRepo;

    public static String returnRawFeed(String url){//TODO remove - only for testing
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
            if (!isRssUrlStringValid(rStream.getUrl())){
                log.error("RSS {} loaded. Initializing update. streamId={}", rStream.getName(), rStream.getId());
                return;
            }
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(rStream.getUrl())));
            int streamId = rStream.getId();
            Optional<RSSItem> newestItem = itemRepo.findFirstByStreamIdOrderByPubDateDesc(streamId);
            Date newestDBEntry = newestItem.map(RSSItem::getPubDate).orElse(new Date(0L));

            log.info("RSS {} loaded. Initializing update. streamId={} newestEntry={}",
                     rStream.getName(), streamId, newestDBEntry);
            List<SyndEntry> newEntries = new java.util.ArrayList<>(feed.getEntries().stream()
                    .filter(x -> !x.getPublishedDate().before(newestDBEntry))
                    .sorted(Comparator.comparing(SyndEntry::getPublishedDate).reversed())
                    .toList());
            if (!newEntries.isEmpty() && newestItem.isPresent()){
                // newEntries.get(0).getUri() works for ČTK other may be different
                if (newEntries.get(0).getUri().equals(newestItem.get().getPermaLink())){
                    newEntries.remove(0);
                }
            }

            for (SyndEntry entry : newEntries) {
                try {
                    itemRepo.save(new RSSItem(streamId, entry));
                } catch (DataIntegrityViolationException ex){
                    //Duplicit item may rarely slip by (same Permalink - unique col)
                    log.info("Duplicit item skipped: {}", entry.getUri());
                }
            }
            log.info("New entries of RSS {} saved to DB. Count: {}", rStream.getName(), newEntries.size());
        } catch (Exception e) {
            log.error("RSS id={} failed to load feed", rStream.getId(),e);
        }
    }
    public boolean isRssUrlStringValid(String urlString){
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
    @EventListener(ApplicationReadyEvent.class)
    public void updateOnStartup() {
        log.info("Starting update of all streams on aplication startup.");
        updateRSSItemRepository();
    }

}
