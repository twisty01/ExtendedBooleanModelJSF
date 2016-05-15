package com.model.logic;

import com.model.view.DocResult;
import java.util.HashMap;
import java.util.List;


public class ExpressionEvaluator  {

    private String expression = "go and flower";        
    private List<String> list;
    
    private HashMap<String, List<DocResult>> queryTerms;
    private List<String> myFinalDocuments; /*my documents potrebuju tam dostat vsechny dokumenty, 
                                           myslenka, na zacatku vsechny, postupne po prochazeni 
                                           dotazu -> vyhazuju nevyhovujici dokumenty*/
    
    
    public List getList( String expression ) {
       String[] terms;
       
       terms = expression.split(" ");
       
        for (String term : terms) {
            
        }
       
       return list;
    }
    
    
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
