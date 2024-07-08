package cz.RSS.archive.springbootcrudjpaPostgre.service;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import cz.RSS.archive.springbootcrudjpaPostgre.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepo;

    public List<RSSItem> getAll(){
        return itemRepo.findAll();
    }
    public List<RSSItem> getSelection(List<Integer> streamIds){
        return itemRepo.findByStreamIdIn(streamIds);
    }
}
