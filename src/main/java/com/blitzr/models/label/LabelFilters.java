package com.blitzr.models.label;

import com.blitzr.BlitzrOptions;
import com.blitzr.models.tag.Tag;

import java.util.HashMap;

public class LabelFilters implements BlitzrOptions {
    private String location;
    private String tag;

    public LabelFilters() {
    }

    public LabelFilters(String location, String tag) {
        this.location = location;
        this.tag = tag;
    }

    public LabelFilters(String location) {

        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void apply(HashMap<String, Object> params) {
        if (this.location != null) {
            params.put("filters[location]", this.location);
        }

        if (this.tag != null) {
            params.put("filters[tag]", this.tag);
        }
    }
}
