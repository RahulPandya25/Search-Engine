package com.project.computingconcept.searchengine.Service;

import com.project.computingconcept.searchengine.Model.Result;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface WebCrawlerService {
       /**
     *
     * @param word
     * @param url
     */
    public List<Result> search(String word, String url) throws IOException;

   
}
