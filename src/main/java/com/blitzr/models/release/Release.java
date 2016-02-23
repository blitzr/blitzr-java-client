package com.blitzr.models.release;

import com.blitzr.models.label.Label;
import com.blitzr.models.artist.Artist;
import com.blitzr.models.tag.Tag;
import com.blitzr.models.track.Track;
import com.blitzr.models.utils.Identifier;

import java.util.Date;
import java.util.List;

public class Release {
    private String type;
    private String format;
    private String image;
    private String thumb;
    private String thumb_300;
    private String uuid;
    private String name;
    private Date release_date;
    private String slug;
    private int tracks_count;
    private List<Artist> artists;
    private List<Track> tracklist;
    private List<Identifier> identifiers;
    private List<Label> labels;
    private List<Tag> tags;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getTracks_count() {
        return tracks_count;
    }

    public void setTracks_count(int tracks_count) {
        this.tracks_count = tracks_count;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<Track> getTracklist() {
        return tracklist;
    }

    public void setTracklist(List<Track> tracklist) {
        this.tracklist = tracklist;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public String getThumb_300() {
        return thumb_300;
    }

    public void setThumb_300(String thumb_300) {
        this.thumb_300 = thumb_300;
    }
}
