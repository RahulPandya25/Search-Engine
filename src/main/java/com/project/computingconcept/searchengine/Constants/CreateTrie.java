package com.project.computingconcept.searchengine.Constants;

import com.project.computingconcept.searchengine.Utility.In;
import com.project.computingconcept.searchengine.Utility.TrieST;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.regex.Pattern;

public class CreateTrie {

    public static TrieST<Integer> trie = new TrieST<Integer>();

    public static void initTrie() {
        if (trie.isEmpty()) {

//            getting file names in this directory first
            String rootDir = "src/main/resources/html files/";
            String[] pathNames = new File(rootDir).list();

//            extract text from html file
            int trieVal = 0;
            for (int i = 0; i < pathNames.length; i++) {
                String extractedText;
                String fileName = pathNames[i];
                if (new File(rootDir + fileName).isFile()) {
                    extractedText = extractTextFromHTML(rootDir + fileName);

//                    using JAVA Regex and split function
                    String[] items = Pattern.compile("\\s").split(extractedText);

//                    inserting words in trie
                    for (String item : items)
//                        removing special char and adding words with length > 0
                        if (!Pattern.compile("[^a-z0-9 ]").matcher(item).find() && item.length() > 0)
                            trie.put(item, trieVal++);

                }
            }

            System.out.println("Trie initialized");
        } else {
            System.out.println("Trie already initialized");
        }
    }


    private static String extractTextFromHTML(String sourceAddress) {
        String dataFile = new In(sourceAddress).readAll();
        Document doc = Jsoup.parse(dataFile);
        return doc.text();
    }

}
