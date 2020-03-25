package com.project.computingconcept.searchengine.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {

    @GetMapping("/search/{key}")
    private String searchHTMLFiles(@PathVariable String key) {
        return "This Works!!";
    }
}
