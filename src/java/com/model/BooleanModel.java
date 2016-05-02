package com.model;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "booleanModel", eager = true)
@SessionScoped
public class BooleanModel {

    @ManagedProperty("#{termContainer}")
    private TermContainer termContainer;

    //must povide the setter method
    public void setTermContainer(TermContainer termContainer) {
        this.termContainer = termContainer;
    }

    private ExpressionEvaluator evaluator = new ExpressionEvaluator();

    private long duration = 0;
    private String expression = "";

    // to show results in html
    private ArrayList<Document> results = new ArrayList<>();

    public void evaulate(boolean lumberjack) {
        results.clear();
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

}
