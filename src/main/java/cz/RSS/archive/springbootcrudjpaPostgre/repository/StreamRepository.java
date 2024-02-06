package cz.RSS.archive.springbootcrudjpaPostgre.repository;

import cz.RSS.archive.springbootcrudjpaPostgre.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamRepository extends JpaRepository<Stream, Long> {
}
