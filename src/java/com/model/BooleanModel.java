package com.model;

import com.porter.Stemmer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "booleanModel", eager = true)
@SessionScoped
public class BooleanModel {

    public final String DATA_ROOT_PATH = "../corpus/data/";
    public final String STOP_WORDS_PATH = "../stop_words.txt";

    private HashSet<String> stopWords;
    private List<String> documents;
    private HashMap<String, List<Document>> terms;

    private long duration;

    private String expression = "";

    // show results in html
    private ArrayList<Document> results = new ArrayList<>();

    public void initialize() {
        // 1. For each doc map of terms and their local occurencies
        // 2. Map of terms and their occurencies
        // 3. Map of terms and their computed weight
        loadStopWords();
        terms = new HashMap<>();
        documents = DocumentLoader.loadList(DATA_ROOT_PATH);

        HashMap<String, HashMap<String, Integer>> termsWithDocumentOccurencies = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> documentsWithTermOccurencies = new HashMap<>();

        documents.stream().forEach((path) -> {
            // 1.
            File file = new File(path);
            Scanner scn = null;
            try {
                scn = new Scanner(file).useDelimiter("[^a-zA-Z]+| <.*>");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            HashMap<String, Integer> termsInDoc = new HashMap<>();
            while (scn.hasNext()) {
                String word = scn.next();
                if (!isStopWord(word)) {
                    String term = Stemmer.stem(word);
                    if (termsInDoc.containsKey(term)) {
                        termsInDoc.replace(term, termsInDoc.get(term) + 1);
                    } else {
                        termsInDoc.put(term, 1);
                    }
                }
            }
            scn.close();
            documentsWithTermOccurencies.put(path, termsInDoc);
            // 2.
            termsInDoc.forEach((String t, Integer u) -> {
                if (termsWithDocumentOccurencies.containsKey(t)) {
                    HashMap<String, Integer> docsWithTerm = termsWithDocumentOccurencies.get(t);
                    docsWithTerm.put(path, u);
                } else {
                    HashMap<String, Integer> docsWithTerm = new HashMap<>();
                    docsWithTerm.put(path, u);
                    termsWithDocumentOccurencies.put(t, docsWithTerm);
                }
            });
        });

        // 3.
        // w = tf * idf = tf * log2(n / df);
        // tf = freq_term_in_the_doc / max_term_freq_in_all_docs;
        // df = num_of_docs_containing_term;
        // iterate over all terms
        termsWithDocumentOccurencies.forEach((String t, HashMap<String, Integer> u) -> {
            // todo
            
        });
        
    }

    public void evaulate(boolean lumberjack) {
        results.add(new Document("link1", -1));
        results.add(new Document("link2", -2));
    }

    // GETTERS AND SETTERS
    public ArrayList<Document> getResults() {
        return results;
    }

    public void setResults(ArrayList<Document> results) {
        this.results = results;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    private void loadStopWords() {
        File file = new File(STOP_WORDS_PATH);
        Scanner scn = null;
        try {
            scn = new Scanner(file).useDelimiter("[^a-zA-Z]+| <.*>");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        stopWords = new HashSet<>();
        while (scn.hasNextLine()) {
            stopWords.add(scn.nextLine());
        }
    }

    public boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

}
