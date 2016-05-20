package com.blitzr.models.utils;

import com.blitzr.models.artist.Artist;
import com.blitzr.models.label.Label;
import com.blitzr.models.release.Release;
import com.blitzr.models.track.Track;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

public class SearchResults<T> {
    private Integer total;
    private List<T> results;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    @JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="entity")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Artist.class, name = "artist"),
            @JsonSubTypes.Type(value = Release.class, name = "release"),
            @JsonSubTypes.Type(value = Label.class, name = "label"),
            @JsonSubTypes.Type(value = Track.class, name = "track")
    })
    public void setResults(List<T> results) {
        this.results = results;
    }
}
