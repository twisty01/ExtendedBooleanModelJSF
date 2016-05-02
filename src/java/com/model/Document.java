package com.model;

import java.util.Objects;

public class Document implements Comparable<Document> {

    private String path;
    private double weight;

    public Document(String path, double weight) {
        this.path = path;
        this.weight = weight;
    }

    public Document() {
        this("", 0);
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
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        return true;
    }

    public String getPath() {
        return path;
    }

    public double getWeight() {
        return weight;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Document{" + "path=" + path + ", weight=" + weight + '}';
    }

}
