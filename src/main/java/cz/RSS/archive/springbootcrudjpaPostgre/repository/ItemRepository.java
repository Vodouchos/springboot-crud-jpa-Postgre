package cz.RSS.archive.springbootcrudjpaPostgre.repository;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ItemRepository extends JpaRepository<RSSItem, Integer> {
    Optional<RSSItem> findFirstByStreamIdOrderByPubDateDesc(int streamId);
    List<RSSItem> findByStreamIdIn(List<Integer> streamId);

    void deleteByStreamId(int streamId);
}
