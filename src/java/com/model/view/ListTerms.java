package com.model.view;

import com.model.logic.TermLoader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "listTerms", eager = true)
@SessionScoped
public class ListTerms implements Serializable {

    private List<String> list;

    @ManagedProperty("#{termLoader}")
    private TermLoader termLoader;

    public void loadList(String from, String to) {
        List<String> terms = termLoader.getListTerms();
        int i1 = Collections.binarySearch(terms, from), i2 = Collections.binarySearch(terms, to);
        if (i1 < 0) {
            i1 = -i1 + 1;
        }
        if (i2 < 0) {
            i2 = -i2 + 1;
        }
        if (to.equals("")) {
            i2 = terms.size();
        }
        if ( i2 > terms.size()){
            i2 = terms.size();
        }
        if ( i1 > i2 ){
            i1 = i2;
        }
        list = terms.subList(i1, i2);
    }

    // managedProperty == must povide the setter method
    public void setTermLoader(TermLoader termLoader) {
        this.termLoader = termLoader;
    }

    public List<String> getList() {
        return list;
    }

}
