package com.project.computingconcept.searchengine.ServiceImpl;

import com.project.computingconcept.searchengine.Service.TrieService;
import com.project.computingconcept.searchengine.Utility.TrieST;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

@Component
public class TrieServiceImpl implements TrieService {


    @Override
    public TrieST<Integer> createTrie(String text) {
        TrieST<Integer> trie = new TrieST<Integer>();
        StringTokenizer tokenizer = new StringTokenizer(text);
        int counter = 0;
        while (tokenizer.hasMoreElements()) {
            String s = tokenizer.nextToken();
            if (!Pattern.compile("[^a-z0-9 ]").matcher(s).find() && s.length() > 0) {
                trie.put(s, counter);
            }
            counter++;
        }
        return trie;
    }
}
