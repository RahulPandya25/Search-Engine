package com.project.computingconcept.searchengine.Controller;

import com.project.computingconcept.searchengine.Service.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {

    @Autowired
    private WebCrawlerService webCrawlerService;

    @GetMapping("/search/{key}")
    private void searchHTMLFiles(@PathVariable String key) throws IOException {
        webCrawlerService.search(key);
    }
}
