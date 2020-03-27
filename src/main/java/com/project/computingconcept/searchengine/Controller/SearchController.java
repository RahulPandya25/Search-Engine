package com.project.computingconcept.searchengine.Controller;

import com.project.computingconcept.searchengine.Service.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.computingconcept.searchengine.Service.WordPredictService;
import com.project.computingconcept.searchengine.ServiceImpl.WordPredictServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {

    private static final String STARTING_URL = "https://www.javatpoint.com/";

    @Autowired
    private WebCrawlerService webCrawlerService;

    @Autowired
    private WordPredictService wordPredictService;

    @GetMapping("/search/{key}")
    private void searchHTMLFiles(@PathVariable String key) throws IOException {
        webCrawlerService.search(key,STARTING_URL);
    }

    @GetMapping("/autocomplete/{key}")
    private List<String> autocomplete(@PathVariable String key) {
        List<String> predictedWords;
        predictedWords = wordPredictService.autoCompleteWord(key);
        return predictedWords;
    }

}
