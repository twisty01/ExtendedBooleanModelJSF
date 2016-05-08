package com.model.logic;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Evaluator {

    private final String and = Pattern.quote("&&");
    private final String or = Pattern.quote("||");
    private final String openPar = Pattern.quote("(");
    private final String closePar = Pattern.quote(")");

    private HashMap<String, List<DocTermValue>> terms;
    private List<DocResult> results;

    public Evaluator(HashMap<String, List<DocTermValue>> terms, List<DocResult> results) {
        this.terms = terms;
        this.results = results;
    }

    public void evaluate(String expression) {
        expression = expression.toLowerCase();
        expression = expression.replaceAll(" ", "");
        
        Pattern pattern = Pattern.compile(openPar+"[a-z]+(("+and+'|'+or+")[a-z]+)*"+closePar);// find inner subExp

        Matcher matcher = pattern.matcher(expression);
        while(matcher.find()){
            String subExp = matcher.group();
    }
        
    }

    private void computeInner(String subExp) {

    }

    public List<DocResult> getResults() {
        return results;
    }

}
