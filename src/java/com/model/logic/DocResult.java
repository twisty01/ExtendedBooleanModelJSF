package com.model.logic;

public class DocResult {

    private final String link;
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

}
