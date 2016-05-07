package com.model.view;

import com.model.logic.TermLoader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "listTerms", eager = true)
@SessionScoped
public class ListTerms implements Serializable{

    private List<String> list;
    
    @ManagedProperty("#{termLoader}")
    private TermLoader termLoader;

    public void loadList(String from, String to) {
        List<String> terms = termLoader.getListTerms();
        int i1 = -1, i2 = -1;
        if ("".equals(from)) {
            i1 = 0;
        }
        if ("".equals(to)) {
            i2 = terms.size() - 1;
        }
        for (int i = 0; i < terms.size(); i++) {
            if (i1 == -1 && terms.get(i).matches(from + ".*")) {
                i1 = i;
            }
            if (i2 == -1 && terms.get(i).matches(to + ".*")) {
                i2 = i;
            }
            if (i2 != -1 && i2 != -1) {
                break;
            }
        }
        if (i2 <= i1 || i2 < 0 || i1 < 0) {
            list = new ArrayList<>();
        }
        list = terms.subList(i1, i2+1);
    }

    // managedProperty == must povide the setter method
    public void setTermLoader(TermLoader termLoader) {
        this.termLoader = termLoader;
    }

    public List<String> getList() {
        return list;
    } 
    
}
