 package com.model.logic; 

 /** * * @author Adam */

public class DocTermValue implements Comparable<DocTermValue>{ 

    private final int idx;
    private double weight = 0;

    public DocTermValue(int idx, double weight) {
        this.idx = idx;
        this.weight = weight;
    }
    
    public int getIdx() {
        return idx;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(DocTermValue o) {
        return idx - o.idx;
    }
    
}