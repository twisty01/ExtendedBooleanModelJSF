package com.model;

import com.porter.Stemmer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
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
    private HashMap<String, List< Pair<String, Float> > > terms;
    
    private ExpressionEvaluator evaulator = new ExpressionEvaluator();
    private Stemmer stemmer = new Stemmer();

    private long duration;

    private String expression = "";

    // show results in html
    private ArrayList<Document> results = new ArrayList<>();
    
    public void initialize(){
        stopWords = new HashSet<>();
        terms = new HashMap<>();
        documents = DocumentLoader.listf(DATA_ROOT_PATH);
        for ( String s : documents){
            processDocument(s);
        }
    }
    
    private void processDocument(String s){
        
        
    }
    
    private void processTerm(String s){
        
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
    

}
