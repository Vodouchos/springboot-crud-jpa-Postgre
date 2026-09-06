package cz.RSS.archive.springbootcrudjpaPostgre.model;

import com.rometools.rome.feed.synd.SyndEntry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class RSSItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int streamId;
    private String title;
    @Column(unique = true)
    private String permaLink;
    private String thumbLink;
    private String text;
    private Date pubDate;

    public RSSItem(int streamId, SyndEntry entry){
        this.streamId=streamId;
        this.title=entry.getTitle();
        this.permaLink=entry.getUri();
        this.thumbLink=entry.getEnclosures().get(0).getUrl();
        this.text=entry.getDescription().getValue();
        this.pubDate=entry.getPublishedDate();
    }
}
