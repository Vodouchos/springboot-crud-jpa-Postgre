package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import cz.RSS.archive.springbootcrudjpaPostgre.service.ItemService;
import cz.RSS.archive.springbootcrudjpaPostgre.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class RssItemsController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UpdateService updateService;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public List<RSSItem> getItems(@RequestParam(name = "update", required = false) String updateString,
                                  @RequestParam(name = "streamId", required = false) List<Integer> streamId){
        boolean update = Boolean.parseBoolean(updateString);
        boolean all = CollectionUtils.isEmpty(streamId);
        if (update)
            if (all) updateService.updateRSSItemRepository(); //update all
                else streamId.forEach(updateService::updateRSSItemRepository); //partial update
        if (all) return itemService.getAll();
        return itemService.getSelection(streamId);
    }

}
