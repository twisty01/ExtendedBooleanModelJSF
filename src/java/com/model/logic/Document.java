package com.model.logic;

import java.util.Objects;

public class Document implements Comparable<Document> {

    private final String path;
    private final String link;
    private double weight;

    public Document(String path, double weight) {
        this.path = path;
        this.weight = weight;
        String split[] = path.split("\\web");
        link = split[split.length - 1].replace('\\', '/');
    }

    @Override
    public int compareTo(Document o) {
        return (int) (weight - o.getWeight());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.path);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Document other = (Document) obj;
        return Objects.equals(this.path, other.path);
    }

    @Override
    public String toString() {
        return "Document{" + "path=" + path + ", weight=" + weight + '}';
    }

    public String getPath() {
        return path;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getLink() {
        return link;
    }
    
}
