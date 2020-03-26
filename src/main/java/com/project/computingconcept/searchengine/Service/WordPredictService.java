package com.project.computingconcept.searchengine.Service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WordPredictService {
    List<String> autoCompleteWord(String key);
}
