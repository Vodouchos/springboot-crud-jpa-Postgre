package cz.RSS.archive.springbootcrudjpaPostgre.controllers;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import cz.RSS.archive.springbootcrudjpaPostgre.service.ItemService;
import cz.RSS.archive.springbootcrudjpaPostgre.service.UpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class RssItemsController {
    private final ItemService itemService;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public List<RSSItem> getItems(@RequestParam(name = "streamId", required = false) List<Integer> streamId){
        boolean all = CollectionUtils.isEmpty(streamId);
        log.info("Called getItems. Streams={} ", streamId);
        if (all) return itemService.getAll();
        return itemService.getSelection(streamId);
    }

}
