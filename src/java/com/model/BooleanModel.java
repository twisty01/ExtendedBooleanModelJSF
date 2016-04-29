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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "booleanModel", eager = true)
@SessionScoped
public class BooleanModel {

    public final String DATA_ROOT_PATH = "../corpus/data/";
    public final String STOP_WORDS_PATH = "../stop_words.txt";

    private HashSet<String> stopWords;
    private List<String> documents;
    private HashMap<String, List< Pair<String, Integer>>> terms;

    private ExpressionEvaluator evaulator = new ExpressionEvaluator();
    private Stemmer stemmer = new Stemmer();

    private long duration;

    private String expression = "";

    // show results in html
    private ArrayList<Document> results = new ArrayList<>();

    public void initialize() {
        stopWords = new HashSet<>();
        terms = new HashMap<>();
        documents = DocumentLoader.listf(DATA_ROOT_PATH);
        for (String s : documents) {
            processDocument(s);
        }
    }

    private void processDocument(String path) {
        File file = new File(path);
        Scanner scn = null;
        try {
            scn = new Scanner(file).useDelimiter("[^a-zA-Z]+| <.*>");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        while (scn.hasNext()) {
            String term = scn.next();

        }
        scn.close();
        
        // w = tf * idf = tf * log2(n / df);
        // tf = freq_term_in_the_doc / max_term_freq_in_all_docs;
        // df = num_of_docs_containing_term;

    }

    public void evaulate(boolean lumberjack) {
        results.add(new Document("placeholder1", "link1", -1));
        results.add(new Document("placeholder2", "link2", -2));
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
    
    // -------------- process words -------------------
    public boolean isStopWord(String word){
        return stopWords.contains(word);
    }
    
    public String wordToTerm(String word){
        stemmer.add(word.toCharArray(), word.length());
        stemmer.stem();
        return stemmer.toString();
    }

}
