package com.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "booleanModel", eager = true)
@SessionScoped
public class BooleanModel implements Serializable{

    @ManagedProperty("#{termContainer}")
    private TermContainer termContainer;

    private ExpressionEvaluator evaluator = new ExpressionEvaluator();

    private long duration = 0;
    private String expression = "";

    // to show results in html
    private List<Document> results = new LinkedList<>();

    public void evaulate(boolean lumberjack) {
        results.clear();
    }

    // GETTERS AND SETTERS
    public List<Document> getResults() {
        return results;
    }

    public void setResults(List<Document> results) {
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
    
    // managedProperty == must povide the setter method
    public void setTermContainer(TermContainer termContainer) {
        this.termContainer = termContainer;
    }

}
