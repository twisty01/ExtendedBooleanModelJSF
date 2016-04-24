package com.main;

import javax.faces.bean.ManagedProperty;


public class ResultsBean {

    @ManagedProperty(value="#{results}")
    private Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
    
    
}