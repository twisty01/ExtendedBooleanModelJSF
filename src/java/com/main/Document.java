package com.main;

public class Document implements Comparable<Document> {

    private String name;
    private String path;
    private int score;

    public Document(String name, String path, int score) {
        this.name = name;
        this.path = path;
        this.score = score;
    }

    public Document() {
        this("", "", 0);
    }

    @Override
    public int compareTo(Document o) {
        return score - o.getScore();
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
