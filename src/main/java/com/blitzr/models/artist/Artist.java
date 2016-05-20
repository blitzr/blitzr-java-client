package com.blitzr.models.artist;

import com.blitzr.models.release.Release;
import com.blitzr.models.utils.Biography;
import com.blitzr.models.event.Event;
import com.blitzr.models.tag.Tag;
import com.blitzr.models.utils.SearchResult;
import com.blitzr.models.utils.Service;
import com.blitzr.models.utils.Website;

import java.util.List;

public class Artist extends SearchResult {
    private String name;
    private String uuid;
    private String slug;
    private String image;
    private String thumb;
    private String thumb_300;
    private String real_name;
    private String location;
    private String location_code;
    private Integer begin_date;
    private Integer end_date;
    private String type;
    private List<Artist> in_bands;
    private List<Artist> members;
    private List<Tag> tags;
    private List<Website> websites;
    private List<Artist> aliases;
    private List<Release> last_releases;
    private List<String> available_languages;
    private Biography biography;
    private List<Event> next_events;
    private String disambiguation;
    private String summary;
    private List<Service> services;

    public Artist(String uuid) {
        this.uuid = uuid;
    }

    public Artist(String uuid, String slug) {
        this.uuid = uuid;
        this.slug = slug;
    }

    public Artist() {

    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
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

    public Integer getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(Integer begin_date) {
        this.begin_date = begin_date;
    }

    public Integer getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Integer end_date) {
        this.end_date = end_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Artist> getIn_bands() {
        return in_bands;
    }

    public void setIn_bands(List<Artist> in_bands) {
        this.in_bands = in_bands;
    }

    public List<Artist> getMembers() {
        return members;
    }

    public void setMembers(List<Artist> members) {
        this.members = members;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Website> getWebsites() {
        return websites;
    }

    public void setWebsites(List<Website> websites) {
        this.websites = websites;
    }

    public List<Artist> getAliases() {
        return aliases;
    }

    public void setAliases(List<Artist> aliases) {
        this.aliases = aliases;
    }

    public List<Release> getLast_releases() {
        return last_releases;
    }

    public void setLast_releases(List<Release> last_releases) {
        this.last_releases = last_releases;
    }

    public List<String> getAvailable_languages() {
        return available_languages;
    }

    public void setAvailable_languages(List<String> available_languages) {
        this.available_languages = available_languages;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }

    public List<Event> getNext_events() {
        return next_events;
    }

    public void setNext_events(List<Event> next_events) {
        this.next_events = next_events;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getThumb_300() {
        return thumb_300;
    }

    public void setThumb_300(String thumb_300) {
        this.thumb_300 = thumb_300;
    }
}
