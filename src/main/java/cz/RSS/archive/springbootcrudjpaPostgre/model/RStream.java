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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String url;
    private String domain;

    public RStream(String name, String url,String domain){
        this.name=name;
        this.domain=domain;
        this.url=url;
    }
}
