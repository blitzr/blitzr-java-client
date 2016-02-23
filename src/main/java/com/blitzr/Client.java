package com.blitzr;

import com.blitzr.models.artist.Artist;
import com.blitzr.models.artist.ArtistExtras;
import com.blitzr.models.artist.ArtistFilters;
import com.blitzr.models.event.Event;
import com.blitzr.models.label.Label;
import com.blitzr.models.label.LabelExtras;
import com.blitzr.models.label.LabelFilters;
import com.blitzr.models.release.Release;
import com.blitzr.models.release.ReleaseFilters;
import com.blitzr.models.release.ReleaseFormat;
import com.blitzr.models.release.ReleaseType;
import com.blitzr.models.shop.Product;
import com.blitzr.models.shop.ProductType;
import com.blitzr.models.tag.Tag;
import com.blitzr.models.track.Source;
import com.blitzr.models.track.SourceFilters;
import com.blitzr.models.track.Track;
import com.blitzr.models.track.TrackFilters;
import com.blitzr.models.utils.City;
import com.blitzr.models.utils.Country;
import com.blitzr.models.utils.SearchResults;
import com.blitzr.models.utils.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Client {
    private static final String API_URL = "https://api.blitzr.com/";
    private static String mApiKey;

    public Client(String mApiKey) {
        Client.mApiKey = mApiKey;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public static String getApiKey() {
        return mApiKey;
    }

    public Artist getArtist(String slug, String uuid, ArrayList<ArtistExtras> extras, Integer extras_limit) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("extras", (extras != null) ? Utils.concatOptionsWSep(extras, ",") : null);
        params.put("extras_limit", extras_limit);
        return ApiCaller.getApi("artist/", Artist.class, params);
    }

    public Artist getArtistBiography(String slug, String uuid, String lang, Boolean html, String url_scheme)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("lang", lang);
        params.put("format", html ? "html" : null);
        params.put("url_scheme", url_scheme);
        return ApiCaller.getApi("artist/biography/", Artist.class, params);
    }

    public Artist getArtistAliases(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("artist/aliases/", Artist.class, params);
    }

    public List<Artist> getArtistBands(String slug, String uuid, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("artist/bands/", Artist.class, params);
    }

    public Generator<Artist> getArtistBandsGenerator(final String slug, final String uuid, final Integer start, final Integer limit)
    {
        return new Generator<Artist>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Artist> artists = Client.this.getArtistBands(slug, uuid, tempStart, tempLimit);
                    for (Artist artist: artists) {
                        yield(artist);
                    }
                    tempStart += tempLimit;
                    if (artists.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Event> getArtistEvents(String slug, String uuid, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("artist/events/", Event.class, params);
    }

    public Generator<Event> getArtistEventsGenerator(final String slug, final String uuid, final Integer start, final Integer limit)
    {
        return new Generator<Event>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Event> events = Client.this.getArtistEvents(slug, uuid, tempStart, tempLimit);
                    for (Event event: events) {
                        yield(event);
                    }
                    tempStart += tempLimit;
                    if (events.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Artist> getArtistMembers(String slug, String uuid, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("artist/members/", Artist.class, params);
    }

    public Generator<Artist> getArtistMembersGenerator(final String slug, final String uuid, final Integer start, final Integer limit)
    {
        return new Generator<Artist>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Artist> artists = Client.this.getArtistMembers(slug, uuid, tempStart, tempLimit);
                    for (Artist artist: artists) {
                        yield(artist);
                    }
                    tempStart += tempLimit;
                    if (artists.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Artist> getArtistRelated(String slug, String uuid, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("artist/related/", Artist.class, params);
    }

    public Generator<Artist> getArtistRelatedGenerator(final String slug, final String uuid, final Integer start, final Integer limit)
    {
        return new Generator<Artist>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Artist> artists = Client.this.getArtistRelated(slug, uuid, tempStart, tempLimit);
                    for (Artist artist: artists) {
                        yield(artist);
                    }
                    tempStart += tempLimit;
                    if (artists.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Release> getArtistReleases(String slug, String uuid, Integer start, Integer limit, ReleaseType type, ReleaseFormat format, Boolean credited)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        params.put("type", type);
        params.put("format", format);
        params.put("credited", credited ? "true" : null);
        return ApiCaller.getApiList("artist/releases/", Release.class, params);
    }

    public Generator<Release> getArtistReleasesGenerator(final String slug, final String uuid, final Integer start, final Integer limit, final ReleaseType type, final ReleaseFormat format, final Boolean credited)
    {
        return new Generator<Release>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Release> releases = Client.this.getArtistReleases(slug, uuid, tempStart, tempLimit, type, format, credited);
                    for (Release release: releases) {
                        yield(release);
                    }
                    tempStart += tempLimit;
                    if (releases.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Artist> getArtistSimilar(String slug, String uuid, ArrayList<ArtistFilters> filters, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("extras", (filters != null) ? Utils.concatOptionsWSep(filters, ",") : null);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("artist/similar/", Artist.class, params);
    }

    public Generator<Artist> getArtistSimilarGenerator(final String slug, final String uuid, final ArrayList<ArtistFilters> filters, final Integer start, final Integer limit)
    {
        return new Generator<Artist>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Artist> artists = Client.this.getArtistSimilar(slug, uuid, filters, tempStart, tempLimit);
                    for (Artist artist: artists) {
                        yield(artist);
                    }
                    tempStart += tempLimit;
                    if (artists.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public Artist getArtistSummary(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("artist/summary/", Artist.class, params);
    }

    public Artist getArtistWebsites(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("artist/websites/", Artist.class, params);
    }

    public Event getEvent(String slug, String uuid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("event/", Event.class, params);
    }

    public List<Event> getEvents(String countryCode, Float latitude, Float longitude, String city, String venue, String tag, Date dateStart, Date dateEnd, Integer radius, Integer start, Integer limit) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("country_code", countryCode);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("city", city);
        params.put("venue", venue);
        params.put("tag", tag);
        params.put("date_start", dateStart);
        params.put("date_end", dateEnd);
        params.put("radius", radius);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("events/", Event.class, params);
    }

    public Generator<Event> getEventsGenerator(final String countryCode, final Float latitude, final Float longitude, final String city, final String venue, final String tag, final Date dateStart, final Date dateEnd, final Integer radius, final Integer start, final Integer limit)
    {
        return new Generator<Event>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Event> events = Client.this.getEvents(countryCode, latitude, longitude, city, venue, tag, dateStart, dateEnd, radius, tempStart, tempLimit);
                    for (Event event: events) {
                        yield(event);
                    }
                    tempStart += tempLimit;
                    if (events.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public Artist getHarmoniaArtist(String service, Object id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("service_name", service);
        params.put("service_id", id);
        return ApiCaller.getApi("harmonia/artist/", Artist.class, params);
    }

    public Label getHarmoniaLabel(String service, Object id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("service_name", service);
        params.put("service_id", id);
        return ApiCaller.getApi("harmonia/label/", Label.class, params);
    }

    public Release getHarmoniaRelease(String service, Object id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("service_name", service);
        params.put("service_id", id);
        return ApiCaller.getApi("harmonia/release/", Release.class, params);
    }

    public List<Track> getHarmoniaSearchBySource(String source, Object id, List<SourceFilters> source_filters, Boolean strict) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("source_name", source);
        params.put("source_id", id);
        params.put("source_filters", (source_filters != null) ? Utils.concatOptionsWSep(source_filters, ",") : null);
        params.put("strict", strict ? "true" : null);
        return ApiCaller.getApiList("harmonia/searchbysource/", Track.class, params);
    }

    public Label getLabel(String slug, String uuid, ArrayList<LabelExtras> extras, Integer extras_limit) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("extras", (extras != null) ? Utils.concatOptionsWSep(extras, ",") : null);
        params.put("extras_limit", extras_limit);
        return ApiCaller.getApi("label/", Label.class, params);
    }

    public List<Label> getLabelArtists(String slug, String uuid, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("label/artists/", Label.class, params);
    }

    public Generator<Label> getLabelArtistsGenerator(final String slug, final String uuid, final Integer start, final Integer limit)
    {
        return new Generator<Label>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Label> labels = Client.this.getLabelArtists(slug, uuid, tempStart, tempLimit);
                    for (Label label: labels) {
                        yield(label);
                    }
                    tempStart += tempLimit;
                    if (labels.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public Label getLabelBiography(String slug, String uuid, Boolean html, String url_scheme)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("format", html ? "html" : null);
        params.put("url_scheme", url_scheme);
        return ApiCaller.getApi("label/biography/", Label.class, params);
    }

    public List<Release> getLabelReleases(String slug, String uuid, ReleaseFormat format, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("format", format);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("label/releases/", Release.class, params);
    }

    public Generator<Release> getLabelReleasesGenerator(final String slug, final String uuid, final ReleaseFormat format, final Integer start, final Integer limit)
    {
        return new Generator<Release>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Release> releases = Client.this.getLabelReleases(slug, uuid, format, tempStart, tempLimit);
                    for (Release release: releases) {
                        yield(release);
                    }
                    tempStart += tempLimit;
                    if (releases.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Label> getLabelSimilar(String slug, String uuid, ArrayList<LabelFilters> filters, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("extras", (filters != null) ? Utils.concatOptionsWSep(filters, ",") : null);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("label/similar/", Label.class, params);
    }

    public Generator<Label> getLabelSimilarGenerator(final String slug, final String uuid, final ArrayList<LabelFilters> filters, final Integer start, final Integer limit)
    {
        return new Generator<Label>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Label> labels = Client.this.getLabelSimilar(slug, uuid, filters, tempStart, tempLimit);
                    for (Label label: labels) {
                        yield(label);
                    }
                    tempStart += tempLimit;
                    if (labels.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public Label getLabelWebsites(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("label/websites/", Label.class, params);
    }

    public List<Track> getRadioArtist(String slug, String uuid, Integer number)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("limit", number);
        return ApiCaller.getApiList("radio/artist/", Track.class, params);
    }

    public List<Track> getRadioArtistSimilar(String slug, String uuid, Integer number)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("limit", number);
        return ApiCaller.getApiList("radio/artist/similar/", Track.class, params);
    }

    public List<Track> getRadioLabel(String slug, String uuid, Integer number)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("limit", number);
        return ApiCaller.getApiList("radio/label/", Track.class, params);
    }

    public List<Track> getRadioTag(String slug, Integer number)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("limit", number);
        return ApiCaller.getApiList("radio/tag/", Track.class, params);
    }

    public Release getRelease(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("release/", Release.class, params);
    }

    public HashMap<String, Service> getReleaseSources(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiHashMap("release/sources/", String.class, Service.class, params);
    }

    public List<Artist> searchArtist(String query, ArrayList<ArtistFilters> filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("filters", (filters != null) ? Utils.concatOptionsWSep(filters, ",") : null);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("search/artist/", Artist.class, params);
    }

    public SearchResults<Artist> searchArtistWithExtras(String query, ArrayList<ArtistFilters> filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("filters", (filters != null) ? Utils.concatOptionsWSep(filters, ",") : null);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        params.put("extras", true);
        return ApiCaller.getApiParametricType("search/artist/", SearchResults.class, Artist.class, params);
    }

    public Generator<Artist> searchArtistGenerator(final String query, final ArrayList<ArtistFilters> filters, final Boolean autocomplete, final Integer start, final Integer limit)
    {
        return new Generator<Artist>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Artist> artists = Client.this.searchArtist(query, filters, autocomplete, tempStart, tempLimit);
                    for (Artist artist: artists) {
                        yield(artist);
                    }
                    tempStart += tempLimit;
                    if (artists.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<City> searchCity(String query, Boolean autocomplete, Float latitude, Float longitude, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("search/city/", City.class, params);
    }

    public Generator<City> searchCityGenerator(final String query, final Boolean autocomplete, final Float latitude, final Float longitude, final Integer start, final Integer limit)
    {
        return new Generator<City>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<City> cities = Client.this.searchCity(query, autocomplete, latitude, longitude, tempStart, tempLimit);
                    for (City city: cities) {
                        yield(city);
                    }
                    tempStart += tempLimit;
                    if (cities.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Country> searchCountry(String country_code, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("country_code", country_code);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("search/country/", Country.class, params);
    }

    public Generator<Country> searchCountryGenerator(final String country_code, final Integer start, final Integer limit)
    {
        return new Generator<Country>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Country> countries = Client.this.searchCountry(country_code, tempStart, tempLimit);
                    for (Country country: countries) {
                        yield(country);
                    }
                    tempStart += tempLimit;
                    if (countries.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Label> searchLabel(String query, ArrayList<LabelFilters> filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("filters", (filters != null) ? Utils.concatOptionsWSep(filters, ",") : null);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("search/label/", Label.class, params);
    }

    public SearchResults<Label> searchLabelWithExtras(String query, ArrayList<LabelFilters> filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("filters", (filters != null) ? Utils.concatOptionsWSep(filters, ",") : null);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        params.put("extras", true);
        return ApiCaller.getApiParametricType("search/label/", SearchResults.class, Label.class, params);
    }

    public Generator<Label> searchLabelGenerator(final String query, final ArrayList<LabelFilters> filters, final Boolean autocomplete, final Integer start, final Integer limit)
    {
        return new Generator<Label>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Label> labels = Client.this.searchLabel(query, filters, autocomplete, tempStart, tempLimit);
                    for (Label label: labels) {
                        yield(label);
                    }
                    tempStart += tempLimit;
                    if (labels.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Release> searchRelease(String query, ArrayList<ReleaseFilters> filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("filters", (filters != null) ? Utils.concatOptionsWSep(filters, ",") : null);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("search/release/", Release.class, params);
    }

    public SearchResults<Release> searchReleaseWithExtras(String query, ArrayList<ReleaseFilters> filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("filters", (filters != null) ? Utils.concatOptionsWSep(filters, ",") : null);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        params.put("extras", true);
        return ApiCaller.getApiParametricType("search/release/", SearchResults.class, Release.class, params);
    }

    public Generator<Release> searchReleaseGenerator(final String query, final ArrayList<ReleaseFilters> filters, final Boolean autocomplete, final Integer start, final Integer limit)
    {
        return new Generator<Release>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Release> releases = Client.this.searchRelease(query, filters, autocomplete, tempStart, tempLimit);
                    for (Release release: releases) {
                        yield(release);
                    }
                    tempStart += tempLimit;
                    if (releases.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Track> searchTrack(String query, ArrayList<TrackFilters> filters, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("filters", (filters != null) ? Utils.concatOptionsWSep(filters, ",") : null);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("search/track/", Track.class, params);
    }

    public SearchResults<Track> searchTrackWithExtras(String query, ArrayList<TrackFilters> filters, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("filters", (filters != null) ? Utils.concatOptionsWSep(filters, ",") : null);
        params.put("start", start);
        params.put("limit", limit);
        params.put("extras", true);
        return ApiCaller.getApiParametricType("search/track/", SearchResults.class, Track.class, params);
    }

    public Generator<Track> searchTrackGenerator(final String query, final ArrayList<TrackFilters> filters, final Integer start, final Integer limit)
    {
        return new Generator<Track>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Track> tracks = Client.this.searchTrack(query, filters, tempStart, tempLimit);
                    for (Track track: tracks) {
                        yield(track);
                    }
                    tempStart += tempLimit;
                    if (tracks.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Product> shopArtist(ProductType type, String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiList("buy/artist/" + type + "/", Product.class, params);
    }

    public List<Product> shopLabel(ProductType type, String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiList("buy/label/" + type + "/", Product.class, params);
    }

    public List<Product> shopRelease(ProductType type, String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiList("buy/release/" + type + "/", Product.class, params);
    }

    public List<Product> shopTrack(String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return ApiCaller.getApiList("buy/track/", Product.class, params);
    }

    public Tag getTag(String slug)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        return ApiCaller.getApi("tag/", Tag.class, params);
    }

    public List<Artist> getTagArtists(String slug, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("tag/artists/", Artist.class, params);
    }

    public Generator<Artist> getTagArtistsGenerator(final String slug, final Integer start, final Integer limit)
    {
        return new Generator<Artist>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Artist> artists = Client.this.getTagArtists(slug, tempStart, tempLimit);
                    for (Artist artist: artists) {
                        yield(artist);
                    }
                    tempStart += tempLimit;
                    if (artists.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public List<Release> getTagReleases(String slug, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("tag/releases/", Release.class, params);
    }

    public Generator<Release> getTagReleasesGenerator(final String slug, final Integer start, final Integer limit)
    {
        return new Generator<Release>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    List<Release> releases = Client.this.getTagReleases(slug, tempStart, tempLimit);
                    for (Release release: releases) {
                        yield(release);
                    }
                    tempStart += tempLimit;
                    if (releases.size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    public Track getTrack(String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return ApiCaller.getApi("track/", Track.class, params);
    }

    public List<Source> getTrackSources(String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return ApiCaller.getApiList("track/sources/", Source.class, params);
    }
}
