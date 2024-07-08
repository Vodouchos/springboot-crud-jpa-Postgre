package cz.RSS.archive.springbootcrudjpaPostgre.service;

import cz.RSS.archive.springbootcrudjpaPostgre.controllers.RssStreamController;
import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepo;
    Logger logger = LoggerFactory.getLogger(RssStreamController.class);

    public List<RSSItem> getAll(){
        logger.info("getting all Items");
        return itemRepo.findAll();
    }
    public List<RSSItem> getSelection(List<Integer> streamIds){
        logger.info("getting items of streams: " + streamIds);
        return itemRepo.findByStreamIdIn(streamIds);
    }
}
