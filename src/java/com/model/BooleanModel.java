package com.model;

import com.porter.Stemmer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashSet;
import javafx.util.Pair;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "booleanModel", eager = true)
@SessionScoped
public class BooleanModel {

    public final String DATA_ROOT_PATH = "../corpus/data/";
    public final String STOP_WORDS_PATH = "../stop_words.txt";

    public ArrayList<String> stopWords = new ArrayList();
    
    public HashSet<String> documents;
    public Dictionary<String, Pair< String, Float > > terms;
    
    public BooleanExpressionParser parser = new BooleanExpressionParser();
    public Stemmer stemmer = new Stemmer();

    public Date startTime;
    
    public String expression = "";

    // show in html stuff
    public ArrayList<Document> results = new ArrayList<>();

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

    public void evaulate(boolean lumberjack) {
        results.add(new Document("placeholder1", "link1", -1));
        results.add(new Document("placeholder2", "link2", -2));
    }

}
