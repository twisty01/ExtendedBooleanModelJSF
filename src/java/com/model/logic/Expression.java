 package com.model.logic; 

import java.util.LinkedList;
import java.util.List;

 /** * * @author Adam */

public class Expression {
    
    public List<Expression> children;
    public boolean negation;
    public boolean conjunction; // x disjucntion
    public boolean completed;
    public double value;
    
    
}