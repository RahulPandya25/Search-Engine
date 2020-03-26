package com.project.computingconcept.searchengine.Service;

import com.project.computingconcept.searchengine.Utility.TrieST;
import org.springframework.stereotype.Service;

@Service
public interface TrieService {
    public TrieST<Integer> createTrie(String Text);
}
