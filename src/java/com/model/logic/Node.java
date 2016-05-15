package com.model.logic;

import java.util.List;

public class Node {

    public Node parent;
    public List<Node> children;

    public boolean negation;
    public boolean completed;
    public double value;

    public Node(Node parent, List<Node> children, boolean negation) {
        this.parent = parent;
        this.children = children;
        this.negation = negation;
        completed = false;
    }

    public Node(Node parent, double value, boolean negation) {
        this.parent = parent;
        this.value = value;
        completed = true;
    }

    void complete() {
        value = 0;
        for (Node n : children) {
            if (!n.completed) {
                n.complete();
            }
            value += n.value;
        }

    }

}
