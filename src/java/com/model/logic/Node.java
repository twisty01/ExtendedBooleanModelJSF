 package com.model.logic; 

import java.util.LinkedList;
import java.util.List;

 /** * * @author Adam */

public class Node {
    
    public Node parent;
    public List<Node> children;
    
    public boolean negation;
    public boolean conjunction; // x disjucntion
    public boolean completed;
    public double value;
    
    void complete(){
        value = 0;
        for ( Node n : children ){
            if ( ! n.completed ){
                n.complete();
            }
            value+= n.value;
        }
        
    }
    
    
}