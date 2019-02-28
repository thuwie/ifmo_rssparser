package thingies.impl;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import thingies.RssParser;

import java.io.IOException;
import java.io.InputStream;

public class RssParserImpl implements RssParser {
    public RssParserImpl() {
    }

    @Override
    public boolean checkSource(String url) {
        return true;
    }

    @Override
    public void fetchRssFeed(String url) {
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(stream));
                feed.getEntries().forEach(e -> System.out.println("WOW " + e));
            } catch (FeedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transformRssFeed() {

    }

    @Override
    public void shapeRssFeed() {

    }
}
