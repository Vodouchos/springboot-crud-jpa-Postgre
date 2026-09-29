package cz.RSS.archive.springbootcrudjpaPostgre.repository;

import cz.RSS.archive.springbootcrudjpaPostgre.model.RSSItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ItemRepository extends JpaRepository<RSSItem, Long> {
    Optional<RSSItem> findFirstByStreamIdOrderByPubDateDesc(int streamId);
    Page<RSSItem> findAllByOrderByPubDateDesc(Pageable pageable);
    List<RSSItem> findByStreamIdInOrderByPubDateDesc(List<Integer> streamId);
    Page<RSSItem> findByStreamIdInOrderByPubDateDesc(List<Integer> streamIds, Pageable pageable);

    void deleteByStreamId(int streamId);
}
