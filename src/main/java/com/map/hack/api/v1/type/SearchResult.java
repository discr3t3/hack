package com.map.hack.api.v1.type;

import java.util.List;

public class SearchResult<T> {
    protected int count;
    protected List<T> results;

    public SearchResult(List<T> results) {
        this.count = results.size();
        this.results = results;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getResults() {
        return this.results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
