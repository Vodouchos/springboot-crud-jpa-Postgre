package cz.RSS.archive.springbootcrudjpaPostgre.model;

import com.rometools.rome.feed.synd.SyndEntry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@Table(name = "items")
public class RSSItem {
    @Id
    @GeneratedValue
    private int id;
    private int streamId;
    private String title;
    @Column(unique = true)
    private String permaLink;
    private String thumbLink;
    private Date pubDate;

    public RSSItem(int streamId, String title, String permaLink, String thumbLink, Date pubDate){
        this.streamId=streamId;
        this.title=title;
        this.permaLink=permaLink;
        this.thumbLink=thumbLink;
        this.pubDate=pubDate;
    }
    public RSSItem(int streamId, SyndEntry entry){
        this.streamId=streamId;
        this.title=entry.getTitle();
        this.permaLink=entry.getUri();
        this.thumbLink=entry.getEnclosures().get(0).getUrl();
        this.pubDate=entry.getPublishedDate();
    }
}
