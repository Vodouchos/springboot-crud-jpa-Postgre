package cz.RSS.archive.springbootcrudjpaPostgre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "streams")
public class RStream {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Column(unique = true)
    private String url;

    public RStream(String name, String url){
        this.name=name;
        this.url=url;
    }
}
