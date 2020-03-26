package com.project.computingconcept.searchengine.Controller;

import com.project.computingconcept.searchengine.Service.WordPredictService;
import com.project.computingconcept.searchengine.ServiceImpl.WordPredictServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {

    private WordPredictService predictService = new WordPredictServiceImpl();

    @GetMapping("/search/{key}")
    private String searchHTMLFiles(@PathVariable String key) {
        return "This Works!!";
    }

    @GetMapping("/autocomplete/{key}")
    private List<String> autocomplete(@PathVariable String key) {
        List<String> predictedWords;
        predictedWords = predictService.autoCompleteWord(key);
        return predictedWords;
    }

}
