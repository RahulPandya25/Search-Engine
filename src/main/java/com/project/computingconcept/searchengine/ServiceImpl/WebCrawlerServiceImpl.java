package com.project.computingconcept.searchengine.ServiceImpl;

import com.project.computingconcept.searchengine.Model.Result;
import com.project.computingconcept.searchengine.Service.TrieService;
import com.project.computingconcept.searchengine.Service.WebCrawlerService;
import com.project.computingconcept.searchengine.Utility.Sort;
import com.project.computingconcept.searchengine.Utility.TrieST;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class WebCrawlerServiceImpl implements WebCrawlerService {

    @Autowired
    TrieService trieService;

    private static final String STARTING_URL = "https://www.javatpoint.com/";
    private static final int MAX_PAGES_TO_SEARCH = 100;
    private static final int MAX_RANK_TO_BE_DISPLAYED = 5;
    private Set<String> pagesVisited;
    private List<String> pagesToVisit;
    private List<TrieST> trieSTList;
    private Document document;
    private List<Result> resultList;

    //to initialize the lists

    /**
     * @param word
     * @param url
     */
    @Override
    public List<Result> search(String word, String url) throws IOException {
        pagesVisited = new HashSet<String>();
        pagesToVisit = new LinkedList<String>();
        trieSTList = new ArrayList<TrieST>();
        resultList = new ArrayList<Result>();
        String currentUrl;

        while (pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
            if (pagesToVisit.isEmpty()) {
                currentUrl = url;
            } else {
                currentUrl = this.nextUrl();
            }
            this.pagesToVisit.addAll(crawl(currentUrl));

            System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
        }
        System.out.println(resultList);
        System.out.println("Size of data " + resultList.size());

        List<Result> resultsList = indexAndSortResultList(word);
        return resultsList;
    }

    private List<Result> indexAndSortResultList(String word) {
        Result[] results = new Result[MAX_RANK_TO_BE_DISPLAYED];
        Result[] allResults = new Result[resultList.size()];
        resultList.toArray(allResults);

//        sorting results
        Sort.quicksort(allResults, word);

//        reversing array
        if (allResults.length > MAX_RANK_TO_BE_DISPLAYED) {
            int j = allResults.length - 1;
            for (int i = 0; i < MAX_RANK_TO_BE_DISPLAYED; i++) {
                results[i] = allResults[j--];
            }
        }
        return Arrays.asList(results);
    }

    @Override
    public boolean searchForWord(String word) {
        if (this.document == null) {
            throw new RuntimeException("Error in fetching the Document");
        }
        System.out.println("Searching for the word " + word + "...");
        String bodyText = this.document.text();
        return bodyText.toLowerCase().contains(word.toLowerCase());
    }

    // to crawl through each url and get all the page links, create trie and add the result object in the list.
    private List<String> crawl(String currentUrl) throws IOException {
        List<String> links = new LinkedList<String>();
        Connection connection = Jsoup.connect(currentUrl);
        this.document = connection.get();
        if (connection.response().statusCode() == 200) {
            System.out.println("\n**Visiting** Received web page at " + currentUrl);
            TrieST<Integer> trie = trieService.createTrie(this.document.text());
            this.trieSTList.add(trie);
            Elements metaTags = document.getElementsByTag("meta");
            Result result = createResult(metaTags, trie);
            this.resultList.add(result);
            Elements numberOfLinks = document.select("a[href]");
            for (Element link : numberOfLinks) {
                links.add(link.absUrl("href"));
            }
        }
        return links;

    }

    //to fetch the next url from the pagesToVisit array.
    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }

    //to create result object from the document.
    private Result createResult(Elements metaTags, TrieST<Integer> trie) {
        Result result = new Result();
        for (Element meta : metaTags) {
            String property = meta.attr("property");
            String content = meta.attr("content");
            if ("og:url".equalsIgnoreCase(property)) {
                result.setUrl(content);
            }
            if ("og:title".equalsIgnoreCase(property)) {
                result.setTitle(content);
            }
            if ("og:description".equalsIgnoreCase(property)) {
                result.setDescription(content);
            }
        }
        result.setTrieST(trie);
        return result;


    }
}



