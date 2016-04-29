package com.model;

import java.util.List;


public class ExpressionEvaluator  {

    private String expression = "go and flower";        
    private List<String> list;
    
    public List getList( String expression ) {
       String[] terms;
       
       terms = expression.split(" ");
       
        for (String term : terms) {
            
        }
       
       return list;
    }
    
    
}
