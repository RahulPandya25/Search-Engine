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


    private static final int MAX_PAGES_TO_SEARCH = 10;
    private static final int MAX_RANK_TO_BE_DISPLAYED = 7;
    private Set<String> pagesVisited;
    private Queue<String> pagesToVisit;
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

            System.out.println("Visited " + this.pagesVisited.size() + " web pages");
        }
        List<Result> resultsList = indexAndSortResultList(word);
        return resultsList;
    }

    private List<Result> indexAndSortResultList(String word) {
        Result[] results = new Result[MAX_RANK_TO_BE_DISPLAYED];
        Result[] allResults = new Result[resultList.size()];
        resultList.toArray(allResults);
        //        sorting results
        if (resultList.size() > 100) {
            Sort.quickSelect(allResults, MAX_RANK_TO_BE_DISPLAYED, word);
        } else {
            Sort.quicksort(allResults, word);
        }

//        reversing array
        if (allResults.length > MAX_RANK_TO_BE_DISPLAYED) {
            int j = allResults.length - 1;
            for (int i = 0; i < MAX_RANK_TO_BE_DISPLAYED; i++) {
                results[i] = allResults[j--];
            }
        }

        return Arrays.asList(results);
    }


    // to crawl through each url and get all the page links, create trie and add the result object in the list.
    private List<String> crawl(String currentUrl) throws IOException {
        List<String> links = new LinkedList<String>();
        Connection connection = Jsoup.connect(currentUrl);
        this.document = connection.get();
        if (connection.response().statusCode() == 200) {
            System.out.println("\nVisited web page at " + currentUrl);
            TrieST<Integer> trie = trieService.createTrie(this.document.text());
            this.trieSTList.add(trie);
            Elements metaTags = document.getElementsByTag("meta");
            Elements titleTag = document.getElementsByTag("title");
            Result result = new Result();
            for (Element meta : metaTags) {
                String property = meta.attr("name");
                String content = meta.attr("content");
                if ("description".equalsIgnoreCase(property)) {
                    result.setDescription(content);
                    break;
                }
            }
            result.setTrieST(trie);
            result.setTitle(titleTag.text());
            result.setUrl(currentUrl);
            this.resultList.add(result);

            if (currentUrl.contains("google")) {
                Elements numberOfLinks = document.getElementsByClass("r");
                for (Element l : numberOfLinks) {
                    Element link = l.select("a[href]").first();
                    links.add(link.absUrl("href"));

                }
            } else {
                Elements linksOnPage = document.select("a[href]");
                for (Element link : linksOnPage) {
                    links.add(link.absUrl("href"));
                }
            }

        }
        return links;
    }

    //to fetch the next url from the pagesToVisit array.
    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.poll();
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }


}



