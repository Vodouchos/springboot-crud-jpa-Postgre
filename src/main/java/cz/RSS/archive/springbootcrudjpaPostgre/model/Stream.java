package cz.RSS.archive.springbootcrudjpaPostgre.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    private int id;
    private String name;
    private String source;
}
