package com.blitzr.models.release;

import com.blitzr.BlitzrOptions;
import com.blitzr.models.artist.Artist;
import com.blitzr.models.label.Label;
import com.blitzr.models.tag.Tag;

import java.util.HashMap;

public class ReleaseFilters implements BlitzrOptions {
    private String artist;
    private String artistUuid;
    private String label;
    private String labelUuid;
    private String tag;
    private Integer year;
    private String location;

    public ReleaseFilters() {
    }

    public ReleaseFilters(String artist) {

        this.artist = artist;
    }

    public ReleaseFilters(String artist, String artistUuid) {

        this.artist = artist;
        this.artistUuid = artistUuid;
    }

    public ReleaseFilters(String artist, String artistUuid, String label) {

        this.artist = artist;
        this.artistUuid = artistUuid;
        this.label = label;
    }

    public ReleaseFilters(String artist, String artistUuid, String label, String labelUuid) {

        this.artist = artist;
        this.artistUuid = artistUuid;
        this.label = label;
        this.labelUuid = labelUuid;
    }

    public ReleaseFilters(String artist, String artistUuid, String label, String labelUuid, String tag) {

        this.artist = artist;
        this.artistUuid = artistUuid;
        this.label = label;
        this.labelUuid = labelUuid;
        this.tag = tag;
    }

    public ReleaseFilters(String artist, String artistUuid, String label, String labelUuid, String tag, Integer year) {

        this.artist = artist;
        this.artistUuid = artistUuid;
        this.label = label;
        this.labelUuid = labelUuid;
        this.tag = tag;
        this.year = year;
    }

    public ReleaseFilters(String artist, String artistUuid, String label, String labelUuid, String tag, Integer year, String location) {

        this.artist = artist;
        this.artistUuid = artistUuid;
        this.label = label;
        this.labelUuid = labelUuid;
        this.tag = tag;
        this.year = year;
        this.location = location;
    }

    public String getArtist() {

        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtistUuid() {
        return artistUuid;
    }

    public void setArtistUuid(String artistUuid) {
        this.artistUuid = artistUuid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelUuid() {
        return labelUuid;
    }

    public void setLabelUuid(String labelUuid) {
        this.labelUuid = labelUuid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void apply(HashMap<String, Object> params) {
        if (this.location != null) {
            params.put("filters[location]", this.location);
        }

        if (this.tag != null) {
            params.put("filters[tag]", this.tag);
        }

        if (this.artist != null) {
            params.put("filters[artist]", this.artist);
        }

        if (this.artistUuid != null) {
            params.put("filters[artist.uuid]", this.artistUuid);
        }

        if (this.label != null) {
            params.put("filters[label]", this.label);
        }

        if (this.labelUuid != null) {
            params.put("filters[label.uuid]", this.labelUuid);
        }

        if (this.year != null) {
            params.put("filters[year]", this.year);
        }
    }
}
