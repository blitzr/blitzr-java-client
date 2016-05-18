package com.blitzr.models.artist;

import com.blitzr.BlitzrOptions;
import com.blitzr.models.tag.Tag;

import java.util.HashMap;

public class ArtistFilters implements BlitzrOptions {
    private String location;
    private String tag;
    private ArtistType type;

    public ArtistFilters() {
    }

    public ArtistFilters(String location, String tag, ArtistType type) {

        this.location = location;
        this.tag = tag;
        this.type = type;
    }

    public ArtistFilters(String location, String tag) {

        this.location = location;
        this.tag = tag;
    }

    public ArtistFilters(String location) {

        this.location = location;
    }

    public String getTag() {

        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArtistType getType() {
        return type;
    }

    public void setType(ArtistType type) {
        this.type = type;
    }

    public void apply(HashMap<String, Object> params) {
        if (this.location != null) {
            params.put("filters[location]", this.location);
        }

        if (this.tag != null) {
            params.put("filters[tag]", this.tag);
        }

        if (this.type != null) {
            params.put("filters[type]", this.type);
        }
    }
}
