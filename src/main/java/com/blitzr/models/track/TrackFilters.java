package com.blitzr.models.track;

import com.blitzr.BlitzrOptions;

import java.util.HashMap;

public class TrackFilters implements BlitzrOptions {
    private String artist;
    private String artistUuid;
    private String release;
    private String releaseUuid;
    private String location;
    private Integer year;

    public TrackFilters() {
    }

    public TrackFilters(String artist, String artistUuid, String release, String releaseUuid, String location, Integer year) {

        this.artist = artist;
        this.artistUuid = artistUuid;
        this.release = release;
        this.releaseUuid = releaseUuid;
        this.location = location;
        this.year = year;
    }

    public TrackFilters(String artist, String artistUuid, String release, String releaseUuid, String location) {

        this.artist = artist;
        this.artistUuid = artistUuid;
        this.release = release;
        this.releaseUuid = releaseUuid;
        this.location = location;
    }

    public TrackFilters(String artist, String artistUuid, String release, String releaseUuid) {

        this.artist = artist;
        this.artistUuid = artistUuid;
        this.release = release;
        this.releaseUuid = releaseUuid;
    }

    public TrackFilters(String artist, String artistUuid, String release) {

        this.artist = artist;
        this.artistUuid = artistUuid;
        this.release = release;
    }

    public TrackFilters(String artist, String artistUuid) {

        this.artist = artist;
        this.artistUuid = artistUuid;
    }

    public TrackFilters(String artist) {

        this.artist = artist;
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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getReleaseUuid() {
        return releaseUuid;
    }

    public void setReleaseUuid(String releaseUuid) {
        this.releaseUuid = releaseUuid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void apply(HashMap<String, Object> params) {
        if (this.location != null) {
            params.put("filters[location]", this.location);
        }

        if (this.artist != null) {
            params.put("filters[artist]", this.artist);
        }

        if (this.artistUuid != null) {
            params.put("filters[artist.uuid]", this.artistUuid);
        }

        if (this.release != null) {
            params.put("filters[release]", this.release);
        }

        if (this.releaseUuid != null) {
            params.put("filters[release.uuid]", this.releaseUuid);
        }

        if (this.year != null) {
            params.put("filters[year]", this.year);
        }
    }
}
