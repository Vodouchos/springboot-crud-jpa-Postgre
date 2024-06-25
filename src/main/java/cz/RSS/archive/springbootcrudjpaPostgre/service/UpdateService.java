package cz.RSS.archive.springbootcrudjpaPostgre.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class UpdateService {

    @Autowired
    private StreamRepository streamRepo;
    @Autowired
    private ItemRepository itemRepo;

    public void updateRSSItemRepository(){
        updateRSSItemRepository(1);//todo
    };

    public void updateRSSItemRepository(int id){
        updateRSSItemRepository(id,"https://www.ceskenoviny.cz/sluzby/rss/cr.php");//todo
    };
    private void updateRSSItemRepository(int id, String url){
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(url)));

            System.out.println(feed);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage());
        }

    };
}
