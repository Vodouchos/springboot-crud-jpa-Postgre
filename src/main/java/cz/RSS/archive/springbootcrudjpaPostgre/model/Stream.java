package cz.RSS.archive.springbootcrudjpaPostgre.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stream extends RepresentationModel {
    private String name;
}
