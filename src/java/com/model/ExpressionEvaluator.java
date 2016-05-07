package com.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.faces.bean.ManagedProperty;


public class ExpressionEvaluator  {

    @ManagedProperty("#{termContainer}")
    private TermContainer termContainer;
    
    //must povide the setter method
    public void setTermContainer(TermContainer termContainer) {
        this.termContainer = termContainer;
    }
    
    private HashMap<String, List<Document>> queryTerms;
    private List<String> myFinalDocuments; /*my documents potrebuju tam dostat vsechny dokumenty, 
                                           myslenka, na zacatku vsechny, postupne po prochazeni 
                                           dotazu -> vyhazuju nevyhovujici dokumenty*/
    
    private final String expression = "go and flower";        
    
    public void initializeTerms( String expression ) {
       queryTerms = new HashMap<>();
       if ( !correctExpression(expression)) {
           System.out.println("Wrong expression format.");
       } else {
           
       }
       
       //termContainer.getDocumentsByTerm(term);
    }
    public boolean correctExpression(  String query ) {
        for (int i = 0; i < query.length() ; ++i) {
            if ( query.charAt(i) < 32 || ( query.charAt(i) > 32 && query.charAt(i) < 40 ) 
                                      || ( query.charAt(i) > 41 && query.charAt(i) < 65 ) 
                                      || ( query.charAt(i) > 90 && query.charAt(i) < 97 )
                                      ||   query.charAt(i) > 122 ) return false;
            
        }
        return true;
    }
    
}
