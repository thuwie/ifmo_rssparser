import java.util.ArrayList;
import java.util.List;

public class RssFeed {
    final String title;
    final String link;

    final List<RssEntity> entries = new ArrayList<>();

    public RssFeed(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public List<RssEntity> getMessages() {
        return entries;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "RssFeed{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", entries=" + entries +
                '}';
    }
}
