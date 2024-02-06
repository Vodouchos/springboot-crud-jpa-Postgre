package cz.RSS.archive.springbootcrudjpaPostgre.repository;

import cz.RSS.archive.springbootcrudjpaPostgre.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StreamRepository extends JpaRepository<Stream, Integer> {
}
