package com.project.computingconcept.searchengine.ServiceImpl;

import com.project.computingconcept.searchengine.Constants.CreateTrie;
import com.project.computingconcept.searchengine.Service.WordPredictService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WordPredictServiceImpl implements WordPredictService {
    @Override
    public List<String> autoCompleteWord(String key) {
        List<String> top5predictedWords = new ArrayList<>();
        Iterable<String> allPredictedStrings = CreateTrie.trie.keysWithPrefix(key);
        int count = 0;
        for (String word : allPredictedStrings) {
            top5predictedWords.add(word);
            count++;
            if (count == 5) break;
        }
        return top5predictedWords;
    }
}
