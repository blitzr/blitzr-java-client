package com.blitzr.models.utils;

import com.blitzr.models.artist.Artist;
import com.blitzr.models.label.Label;
import com.blitzr.models.release.Release;
import com.blitzr.models.track.Track;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public abstract class SearchResult {
    protected String entity;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
