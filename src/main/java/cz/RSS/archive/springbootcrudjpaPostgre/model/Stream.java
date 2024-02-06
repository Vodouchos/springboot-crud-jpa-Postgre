package cz.RSS.archive.springbootcrudjpaPostgre.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "streams")
public class Stream extends RepresentationModel {
    @Id
    private Long id;
    private String name;
    private String link;
}
