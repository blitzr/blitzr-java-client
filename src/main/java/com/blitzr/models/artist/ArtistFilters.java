package com.blitzr.models.artist;

import com.blitzr.BlitzrOptions;
import com.blitzr.models.tag.Tag;

import java.util.HashMap;

public class ArtistFilters implements BlitzrOptions {
    private String location;
    private Tag tag;
    private ArtistType type;

    public ArtistFilters() {}

    public ArtistFilters(ArtistType type) {
        this.type = type;
    }

    public ArtistFilters(Tag tag) {

        this.tag = tag;
    }

    public ArtistFilters(String location) {

        this.location = location;
    }

    public ArtistFilters(String location, ArtistType type) {

        this.location = location;
        this.type = type;
    }

    public ArtistFilters(Tag tag, ArtistType type) {

        this.tag = tag;
        this.type = type;
    }

    public ArtistFilters(String location, Tag tag) {
        this.location = location;
        this.tag = tag;
    }

    public ArtistFilters(String location, Tag tag, ArtistType type) {
        this.location = location;
        this.tag = tag;
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
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
            params.put("filters[tag]", this.tag.getSlug());
        }

        if (this.type != null) {
            params.put("filters[type]", this.type);
        }
    }
}
