package com.model;

import com.helpers.ResultsBean;
import com.porter.Stemmer;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "booleanModel", eager = true)
@SessionScoped
public class BooleanModel {
    private final String DATA_ROOT_PATH = "../corpus/data/";
    private final String STOP_WORDS_PATH = "../stop_words.txt";

    private ArrayList<String> stopWords = new ArrayList();

    private BooleanExpressionParser parser = new BooleanExpressionParser();
    private Stemmer stemmer = new Stemmer();
    private ResultsBean resultsBean = new ResultsBean();

    private String expression = "";

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
    
    public void evaulate(){
        
    }

    
}
