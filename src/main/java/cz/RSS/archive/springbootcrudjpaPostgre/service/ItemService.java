package cz.RSS.archive.springbootcrudjpaPostgre.service;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;

    public List<RSSItem> getAll(){
        log.info("getting all Items");
        return itemRepo.findAll();
    }
    public Page<RSSItem> getAll(Pageable pageable){
        log.info("getting page of all Items");
        return itemRepo.findAllByOrderByPubDateDesc(pageable);
    }
    public List<RSSItem> getSelection(List<Integer> streamIds){
        log.info("getting all items of streams={}", streamIds);
        return itemRepo.findByStreamIdInOrderByPubDateDesc(streamIds);
    }
    public Page<RSSItem> getSelection(List<Integer> streamIds, Pageable pageable){
        log.info("getting page of selected streams={}", streamIds);
        return itemRepo.findByStreamIdInOrderByPubDateDesc(streamIds, pageable);
    }

}
