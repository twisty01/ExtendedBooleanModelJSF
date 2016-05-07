package com.model.view;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name ="currentTerm",eager = true)
@SessionScoped
public class CurrentTerm implements Serializable{
    private String term = "";

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }
}