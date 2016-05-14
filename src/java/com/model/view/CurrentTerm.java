package com.model.view;

import com.model.logic.DocResult;
import com.model.logic.TermLoader;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name ="currentTerm",eager = true)
@SessionScoped
public class CurrentTerm implements Serializable{
    
    @ManagedProperty("#{termLoader}")
    private TermLoader termLoader;
    
    private String term = "";
    
    private List<DocResult> documentsByTerm;

    public void setTerm(String term) {
        this.term = term;
        fill();
    }
    
    public void fill(){
        documentsByTerm = termLoader.getDocResByTerm(term);
    }
    
    public void setTermLoader(TermLoader termLoader) {
        this.termLoader = termLoader;
    }

    public String getTerm() {
        return term;
    }

    public List<DocResult> getDocumentsByTerm() {
        return documentsByTerm;
    }
    
}