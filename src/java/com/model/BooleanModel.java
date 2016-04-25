package com.model;

import com.porter.Stemmer;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "booleanModel", eager = true)
@SessionScoped
public class BooleanModel {
    public final String DATA_ROOT_PATH = "../corpus/data/";
    public final String STOP_WORDS_PATH = "../stop_words.txt";

    public ArrayList<String> stopWords = new ArrayList();

    public BooleanExpressionParser parser = new BooleanExpressionParser();
    public Stemmer stemmer = new Stemmer();
    
    public String expression = "";
    public boolean lumberjack = false;
    
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
        System.out.println("EXPRESSION SET TO = "+expression);
        this.expression = expression;
    }
    
    public void evaulate() {
        results.add(new Document("placeholder","link",-1));
        System.out.println("EVALUATED");
    }

}
