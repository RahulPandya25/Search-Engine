package com.project.computingconcept.searchengine.ServiceImpl;

import com.project.computingconcept.searchengine.Service.WebCrawlerService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;

@Component
public class WebCrawlerServiceImpl implements WebCrawlerService {
    private static final String STARTING_URL = "https://www.javatpoint.com/";
    private static final int MAX_PAGES_TO_SEARCH = 20;
    private Set<String> pagesVisited;
    private List<String> pagesToVisit;
    private Document document;

    /**
     * @param word
     * @return
     */
    @Override
    public void search(String word) throws IOException {
        pagesVisited = new HashSet<String>();
        pagesToVisit = new LinkedList<String>();
        search(word, STARTING_URL);

    }

    /**
     * @param word
     * @param url
     */
    @Override
    public void search(String word, String url) throws IOException {

        String currentUrl;

        while (pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
            if (pagesToVisit.isEmpty()) {
                currentUrl = url;
            }
            else{
                currentUrl = this.nextUrl();
            }
            this.pagesToVisit.addAll(crawl(currentUrl));
            if(searchForWord(word)){
                System.out.println(String.format("**Success** Word %s found at %s", word, currentUrl));
            }
            System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
    }
}
    @Override
    public boolean searchForWord(String word) {
        if(this.document == null){
            throw new RuntimeException("Error in fetching the Document");
        }
        System.out.println("Searching for the word " + word + "...");
        String bodyText = this.document.text();
        return bodyText.toLowerCase().contains(word.toLowerCase());
    }

    private List<String> crawl(String currentUrl) throws IOException {
        List<String> links = new LinkedList<String>();
        Connection connection = Jsoup.connect(currentUrl);
        this.document = connection.get();
        if(connection.response().statusCode() == 200)
        {
            System.out.println("\n**Visiting** Received web page at " + currentUrl);
        }
        Elements numberOfLinks = document.select("a[href]");
        for(Element link : numberOfLinks){
            links.add(link.absUrl("href"));
        }
        return links;

    }

    private String nextUrl() {
        String nextUrl;
        do
        {
            nextUrl = this.pagesToVisit.remove(0);
        } while(this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
    }
