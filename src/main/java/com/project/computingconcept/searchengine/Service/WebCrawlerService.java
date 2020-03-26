package com.project.computingconcept.searchengine.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface WebCrawlerService {
    /**
     *
     * @param word
     * @return
     */
    public void search(String word) throws IOException;

    /**
     *
     * @param word
     * @param url
     */
    public void search(String word, String url) throws IOException;
}
