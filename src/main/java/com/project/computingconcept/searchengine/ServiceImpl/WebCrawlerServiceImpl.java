package com.project.computingconcept.searchengine.ServiceImpl;

import com.project.computingconcept.searchengine.Service.WebCrawlerService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

public class WebCrawlerServiceImpl implements WebCrawlerService {
    private static final String STARTING_URL = "https://www.w3.org/";
    private static final int MAX_PAGES_TO_SEARCH = 10;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();
    /**
     * @param word
     * @return
     */
    @Override
    public void search(String word) throws IOException {

            crawl(word, STARTING_URL);

    }

    /**
     * @param word
     * @param url
     */
    @Override
    public void crawl(String word, String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        Document document = connection.get();
        if(connection.response().statusCode() == 200) {
            if (document.text().contains(word)) {

            }
        }
    }
}
