package com.blitzr.models.track;

import com.blitzr.models.artist.Artist;
import com.blitzr.models.release.Release;
import com.blitzr.models.tag.Tag;

import java.util.List;

public class Track {
    private String uuid;
    private String title;
    private String duration;
    private String track_position_alpha;
    private int track_position_num;
    private List<Artist> credited_artists;

    // for Search purpose
    private List<Tag> tags;
    private List<Artist> artists;
    private Release release;

    private List<Source> sources;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTrack_position_alpha() {
        return track_position_alpha;
    }

    public void setTrack_position_alpha(String track_position_alpha) {
        this.track_position_alpha = track_position_alpha;
    }

    public int getTrack_position_num() {
        return track_position_num;
    }

    public void setTrack_position_num(int track_position_num) {
        this.track_position_num = track_position_num;
    }

    public List<Artist> getCredited_artists() {
        return credited_artists;
    }

    public void setCredited_artists(List<Artist> credited_artists) {
        this.credited_artists = credited_artists;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
