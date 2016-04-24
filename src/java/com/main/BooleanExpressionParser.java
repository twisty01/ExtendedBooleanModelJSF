package com.main;

import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.BracketPair;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;
import java.util.Iterator;

public class BooleanExpressionParser extends AbstractEvaluator<String> {

    final static Operator AND = new Operator("&&", 2, Operator.Associativity.LEFT, 2);
    final static Operator OR = new Operator("||", 2, Operator.Associativity.LEFT, 1);

    private static final Parameters parameters;

    static {
        parameters = new Parameters();  // Create the evaluator's parameters
        parameters.add(AND);            // Add the supported operators
        parameters.add(OR);             // Add the parentheses
        parameters.addExpressionBracket(BracketPair.PARENTHESES);
    }

    public BooleanExpressionParser() {
        super(parameters);
    }

    @Override
    protected String toValue(String literal, Object evaluationContext) {
        return literal;
    }

    @Override
    protected String evaluate(Operator operator, Iterator<String> operands, Object evaluationContext) {
        String o1 = operands.next();
        String o2 = operands.next();
        return "";
    }
    
}
