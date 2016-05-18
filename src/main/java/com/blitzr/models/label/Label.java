package com.blitzr.models.label;

import com.blitzr.models.artist.Artist;
import com.blitzr.models.release.Release;
import com.blitzr.models.tag.Tag;
import com.blitzr.models.utils.Biography;
import com.blitzr.models.utils.Website;

import java.util.List;

public class Label {
    private String name;
    private String uuid;
    private String slug;
    private String image;
    private String thumb;
    private String thumb_300;
    private String location;
    private String location_code;
    private List<Label> sub_labels;
    private List<Website> websites;
    private List<Release> last_releases;
    private List<Artist> artists;
    private boolean has_duplicate;
    private List<Tag> tags;
    private Biography biography;

    public Label(String uuid, String slug) {
        this.uuid = uuid;
        this.slug = slug;
    }

    public Label(String uuid) {

        this.uuid = uuid;
    }

    public Label() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation_code() {
        return location_code;
    }

    public void setLocation_code(String location_code) {
        this.location_code = location_code;
    }

    public List<Label> getSub_labels() {
        return sub_labels;
    }

    public void setSub_labels(List<Label> sub_labels) {
        this.sub_labels = sub_labels;
    }

    public List<Website> getWebsites() {
        return websites;
    }

    public void setWebsites(List<Website> websites) {
        this.websites = websites;
    }

    public List<Release> getLast_releases() {
        return last_releases;
    }

    public void setLast_releases(List<Release> last_releases) {
        this.last_releases = last_releases;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public boolean isHas_duplicate() {
        return has_duplicate;
    }

    public void setHas_duplicate(boolean has_duplicate) {
        this.has_duplicate = has_duplicate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }

    public String getThumb_300() {
        return thumb_300;
    }

    public void setThumb_300(String thumb_300) {
        this.thumb_300 = thumb_300;
    }
}
