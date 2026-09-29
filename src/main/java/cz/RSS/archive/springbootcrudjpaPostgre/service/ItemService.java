package cz.RSS.archive.springbootcrudjpaPostgre.service;

import cz.RSS.archive.springbootcrudjpaPostgre.controllers.RssStreamController;
import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;
    Logger logger = LoggerFactory.getLogger(RssStreamController.class);

    public List<RSSItem> getAll(){
        logger.info("getting all Items");
        return itemRepo.findAll();
    }
    public Page<RSSItem> getAll(Pageable pageable){
        logger.info("getting page of all Items");
        return itemRepo.findAllByOrderByPubDateDesc(pageable);
    }
    public List<RSSItem> getSelection(List<Integer> streamIds){
        logger.info("getting all items of streams: " + streamIds);
        return itemRepo.findByStreamIdInOrderByPubDateDesc(streamIds);
    }
    public Page<RSSItem> getSelection(List<Integer> streamIds, Pageable pageable){
        logger.info("getting page of selected streams: " + streamIds);
        return itemRepo.findByStreamIdInOrderByPubDateDesc(streamIds, pageable);
    }

}
