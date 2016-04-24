package com.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "results", eager = true)
@SessionScoped
public class Results implements Serializable{
    private static final long serialVersionUID = 1L;

    // show in html stuff
    private String name = "";
    private String path = "";
    private int score = -1;
    private ArrayList<Document> results = new ArrayList<>(/*Arrays.asList(new Document("Placeholder","nowhere",-1))*/);    

    public ArrayList<Document> getResults() {
        return results;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void setResults(ArrayList<Document> results) {
        this.results = results;
    }
    
    public void addDocument(){
        results.add(new Document(name,path,score));
    }

    
    
    
    
}