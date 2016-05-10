package com.model.logic;

public class DocResult implements Comparable<DocResult> {

    private final String link;
    
    public Node expression;
    private double relevance;

    public DocResult(String link, double relevance) {
        this.link = link;
        this.relevance = relevance;
    }

    public DocResult(DocResult d) {
        this.link = d.getLink();
        this.relevance = 0;
    }

    public String getLink() {
        return link;
    }

    public double getRelevance() {
        return relevance;
    }

    public void setRelevance(double relevance) {
        this.relevance = relevance;
    }

    public static String pathToLink(String path) {
        String split[] = path.split("\\web");
        String link = split[split.length - 1].replace('\\', '/');
        return link;
    }

    @Override
    public int compareTo(DocResult o) {
        if (relevance - o.getRelevance() > 0) {
            return 1;
        } else if (relevance - o.getRelevance() < 0) {
            return -1;
        } else {
            return 0;
        }
    }

}
