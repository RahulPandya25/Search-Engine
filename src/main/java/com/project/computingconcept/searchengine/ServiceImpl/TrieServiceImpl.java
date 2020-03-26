package com.project.computingconcept.searchengine.ServiceImpl;

import com.project.computingconcept.searchengine.Service.TrieService;
import com.project.computingconcept.searchengine.Utility.TrieST;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class TrieServiceImpl implements TrieService {


    @Override
    public TrieST<Integer> createTrie(String Text) {
        TrieST<Integer> trie = new TrieST<Integer>();
        String[] items = Pattern.compile("\\s").split(Text);
        for (int i = 0; i < items.length; i++)
            if (!Pattern.compile("[^a-z0-9 ]").matcher(items[i]).find() && items[i].length() > 0)
                trie.put(items[i], i);
        return trie;
    }
}
