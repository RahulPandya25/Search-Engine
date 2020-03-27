package com.project.computingconcept.searchengine.Model;

import com.project.computingconcept.searchengine.Utility.TrieST;

public class Result {

    private String url;

    private String title;

    private String description;

    private TrieST<Integer> trieST;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TrieST<Integer> getTrieST() {
        return trieST;
    }

    public void setTrieST(TrieST<Integer> trieST) {
        this.trieST = trieST;
    }

    @Override
    public String toString() {
        return "Result{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", trieST=" + trieST +
                '}';
    }
}
