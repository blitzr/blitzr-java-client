package com.blitzr;

import com.blitzr.models.artist.Artist;
import com.blitzr.models.artist.ArtistExtras;
import com.blitzr.models.artist.ArtistFilters;
import com.blitzr.models.event.Event;
import com.blitzr.models.harmonia.HarmoniaProvider;
import com.blitzr.models.label.Label;
import com.blitzr.models.label.LabelArtistsOrder;
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
import com.blitzr.models.utils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <h2>BlitzrClient</h2>
 *
 * <p>This is the only object you need to call the Blitzr API.</p>
 * <p>Create it by the constructor with your API and call the API by the provided methods.</p>
 *
 * <p>Most of parameters are optionals, just fill them by nulls to use default values.</p>
 * <p>All methods does not return the same properties in an similar Object.
 * For example, if you call getArtist(), you will get most of artists fields filled in the Artist object returned.
 * But if you call searchArtist(), you will only fetch a part of those information, but the property 'disambiguation' will be set.
 * You can refer to the methods documentation to know the filled fields based on context.</p>
 */
public class BlitzrClient {
    private static final String API_URL = "https://api.blitzr.com/";
    private static String mApiKey;

    /**
     * Create a BlitzrClient with your API Key
     *
     * @param mApiKey Your Blitzr API Key
     */
    public BlitzrClient(String mApiKey) {
        BlitzrClient.mApiKey = mApiKey;
    }

    /**
     * Get the API base URL
     *
     * @return API Base URL as a String
     */
    public static String getApiUrl() {
        return API_URL;
    }

    /**
     * Get the API key
     *
     * @return The currently set API key.
     */
    public static String getApiKey() {
        return mApiKey;
    }

    /**
     * Fetch an Artist from Blitzr API. Slug or UUID are mandatory.
     *
     * @param slug The artist slug
     * @param uuid The artist uuid
     * @param extras A list of ArtistExtras. Only aliases, websites, biography, last_releases, next_events and relations
     *               are available here.
     * @param extras_limit An int to set the limit of the lists fetched by extras.
     * @return Artist
     */
    public Artist getArtist(String slug, String uuid, List<ArtistExtras> extras, Integer extras_limit) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("extras", (extras != null) ? Utils.concatOptionsWSep(extras, ",") : null);
        params.put("extras_limit", extras_limit);
        return ApiCaller.getApi("artist/", Artist.class, params);
    }

    /**
     * Fetch an Artist biography. Slug or UUID are mandatory.
     * This method returns an Artist with only two fields set : biography and available_languages.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param lang The biography language, "fr" or "en", more language could come later.
     * @param html Set to true if you want HTML markup in the Biography
     * @param url_scheme Urlencoded links format ( eg: '#\/{type}\/{slug}' : '%23%2F%7Btype%7D%2F%7Bslug%7D' )
     * @return Artist with only two fields set : biography and available_languages.
     */
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

    /**
     * Fetch an Artist Aliases List. Slug or UUID are mandatory.
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @return A list of Artist with fields : slug, uuid, name, image, thumb and thumb_300
     */
    public List<Artist> getArtistAliases(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiList("artist/aliases/", Artist.class, params);
    }

    /**
     * Return a list of bands (Artist) where played the given Artist. Slug or UUID are mandatory.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Artist with fields : slug, uuid, name, image, thumb, thumb_300 and tags
     */
    public List<Artist> getArtistBands(String slug, String uuid, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("artist/bands/", Artist.class, params);
    }

    /**
     * Return a Generator with the same data as getArtistBands method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return An Artist Generator, help for getArtistBand pagination
     */
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
                    List<Artist> artists = BlitzrClient.this.getArtistBands(slug, uuid, tempStart, tempLimit);
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

    /**
     * Return a list of Events where will play the given Artist. Slug or UUID are mandatory.
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Event
     */
    public List<Event> getArtistEvents(String slug, String uuid, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("artist/events/", Event.class, params);
    }

    /**
     * Return a Generator with the same data as getArtistEvents method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return An Event Generator, help for getArtistEvents pagination
     */
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
                    List<Event> events = BlitzrClient.this.getArtistEvents(slug, uuid, tempStart, tempLimit);
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

    /**
     * Get the identifiers of this artist in other databases.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @return A hash map of String and HarmoniaProvider, that identify the artist in other databases
     */
    public HashMap<String, HarmoniaProvider> getArtistHarmonia(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiHashMap("artist/harmonia/", String.class, HarmoniaProvider.class, params);
    }

    /**
     * Return a list of Artist who played in the given Band (Artist). Slug or UUID are mandatory.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Artist with fields : slug, uuid, name, real_name, image, thumb, thumb_300 and tags
     */
    public List<Artist> getArtistMembers(String slug, String uuid, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("artist/members/", Artist.class, params);
    }

    /**
     * Return a Generator with the same data as getArtistMembers method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return An Artist Generator, help for getArtistMembers pagination
     */
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
                    List<Artist> artists = BlitzrClient.this.getArtistMembers(slug, uuid, tempStart, tempLimit);
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

    /**
     * Return a list of Band (Artist) which have common Artists with the given Band (Artist). Slug or UUID are mandatory.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Artist with fields : slug, uuid, name, real_name, image, thumb, thumb_300 and tags
     */
    public List<Artist> getArtistRelated(String slug, String uuid, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("artist/related/", Artist.class, params);
    }

    /**
     * Return a Generator with the same data as getArtistRelated method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return An Artist Generator, help for getArtistRelated pagination
     */
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
                    List<Artist> artists = BlitzrClient.this.getArtistRelated(slug, uuid, tempStart, tempLimit);
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

    /**
     * Return a list of Releases by the given Artist. Slug or UUID are mandatory.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @param type The type of the Release (official|unofficial|all)
     * @param format The format of the Release (album|single|live|all)
     * @param credited True to get only Releases where artist is credited (not main releases)
     * @return A list of Release with fields : slug, uuid, name, release_date, image, thumb, thumb_300, type and format
     */
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

    /**
     * Return a Generator with the same data as getArtistReleases method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @param type The type of the Release (official|unofficial|all)
     * @param format The format of the Release (album|single|live|all)
     * @param credited True to get only Releases where artist is credited (not main releases)
     * @return A Release Generator, help for getArtistReleases pagination
     */
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
                    List<Release> releases = BlitzrClient.this.getArtistReleases(slug, uuid, tempStart, tempLimit, type, format, credited);
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

    /**
     * Return a list of Artist similar to the given Artist. Slug or UUID are mandatory.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param filters Filter the returned Artists. Only location is available here.
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Artist with fields : slug, uuid, name, disambiguation, image, thumb, thumb_300 and tags
     */
    public List<Artist> getArtistSimilar(String slug, String uuid, ArtistFilters filters, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        filters.apply(params);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("artist/similar/", Artist.class, params);
    }

    /**
     * Return a Generator with the same data as getArtistSimilar method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param filters Filter the returned Artists. Only location is available here.
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return An Artist Generator, help for getArtistSimilar pagination
     */
    public Generator<Artist> getArtistSimilarGenerator(final String slug, final String uuid, final ArtistFilters filters, final Integer start, final Integer limit)
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
                    List<Artist> artists = BlitzrClient.this.getArtistSimilar(slug, uuid, filters, tempStart, tempLimit);
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

    /**
     * Fetch an Artist Summary. Slug or UUID are mandatory.
     * This method returns an Artist with only one field set : summary.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @return Artist with only one field set : summary.
     */
    public Artist getArtistSummary(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("artist/summary/", Artist.class, params);
    }

    /**
     * Fetch the Artist Websites. Slug or UUID are mandatory.
     * This method returns an Artist with only one field set : websites.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @return Artist with only one field set : websites.
     */
    public Artist getArtistWebsites(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("artist/websites/", Artist.class, params);
    }

    /**
     * Fetch an Event from Blitzr API. Slug or UUID are mandatory.
     *
     * @param slug The Event slug
     * @param uuid The Event UUID
     * @return Event
     */
    public Event getEvent(String slug, String uuid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("event/", Event.class, params);
    }

    /**
     * Get an Event List filtered by date, tag, and geolocation.
     *
     * @param countryCode Official county code (fr, en)
     * @param latitude Latitude of a reference geopoint (use with radius)
     * @param longitude Longitude of a reference geopoint (use with radius)
     * @param city City where the event takes place (not compatible with country code)
     * @param venue Venue where the event takes place
     * @param tag Tag filter
     * @param dateStart Date minimum
     * @param dateEnd Date maximum
     * @param radius Distance max from the reference geopoint (in km)
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return Get an Event List filtered by date, tag, and geolocation
     */
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

    /**
     * Return a Generator with the same data as getEvents method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param countryCode Official county code (fr, en)
     * @param latitude Latitude of a reference geopoint (use with radius)
     * @param longitude Longitude of a reference geopoint (use with radius)
     * @param city City where the event takes place (not compatible with country code)
     * @param venue Venue where the event takes place
     * @param tag Tag filter
     * @param dateStart Date minimum
     * @param dateEnd Date maximum
     * @param radius Distance max from the reference geopoint (in km)
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return An Event Generator, help for getEvents pagination
     */
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
                    List<Event> events = BlitzrClient.this.getEvents(countryCode, latitude, longitude, city, venue, tag, dateStart, dateEnd, radius, tempStart, tempLimit);
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

    /**
     * Get Artist by different Service id.
     *
     * @param service Service name
     * @param id Artist Id on the given service
     * @return Artist
     */
    public Artist getHarmoniaArtist(ServiceName service, Object id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("service_name", service);
        params.put("service_id", id);
        return ApiCaller.getApi("harmonia/artist/", Artist.class, params);
    }

    /**
     * Get Label by different Service id.
     *
     * @param service Service name
     * @param id Label Id on the given service
     * @return Label
     */
    public Label getHarmoniaLabel(ServiceName service, Object id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("service_name", service);
        params.put("service_id", id);
        return ApiCaller.getApi("harmonia/label/", Label.class, params);
    }

    /**
     * Get Release by different Service id.
     *
     * @param service Service name
     * @param id Release Id on the given service
     * @return Release
     */
    public Release getHarmoniaRelease(ServiceName service, Object id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("service_name", service);
        params.put("service_id", id);
        return ApiCaller.getApi("harmonia/release/", Release.class, params);
    }

    /**
     * Get Track by different Service id
     *
     * @param source Source name
     * @param id Track Id on the given source
     * @param source_filters Filter the source
     * @param strict True if you want blitzr to guess the best result for you. False if you want all matched results
     * @return Track
     */
    public List<Track> getHarmoniaSearchBySource(SourceName source, Object id, List<SourceFilters> source_filters, Boolean strict) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("source_name", source);
        params.put("source_id", id);
        params.put("source_filters", (source_filters != null) ? Utils.concatOptionsWSep(source_filters, ",") : null);
        params.put("strict", strict ? "true" : null);
        return ApiCaller.getApiList("harmonia/searchbysource/", Track.class, params);
    }

    /**
     * Fetch a Label from Blitzr API. Slug or UUID are mandatory.
     *
     * @param slug The Label slug
     * @param uuid The Label uuid
     * @param extras A list of LabelExtras. Only biography, websites, artists, last_releases, and relations are
     *               available here.
     * @param extras_limit An int to set the limit of the lists fetched by extras.
     * @return Label
     */
    public Label getLabel(String slug, String uuid, ArrayList<LabelExtras> extras, Integer extras_limit) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("extras", (extras != null) ? Utils.concatOptionsWSep(extras, ",") : null);
        params.put("extras_limit", extras_limit);
        return ApiCaller.getApi("label/", Label.class, params);
    }

    /**
     * Return a list of Artist by the given Label. Slug or UUID are mandatory.
     *
     * @param slug The Label slug
     * @param uuid The Label UUID
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Artist with fields : slug, uuid, name, real_name, image, thumb and thumb_300
     */
    public List<Artist> getLabelArtists(String slug, String uuid, Integer start, Integer limit, LabelArtistsOrder order)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("start", start);
        params.put("limit", limit);
        params.put("order", order);
        return ApiCaller.getApiList("label/artists/", Artist.class, params);
    }

    /**
     * Return a Generator with the same data as getLabelArtists method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return An Artist Generator, help for getLabelArtists pagination
     */
    public Generator<Artist> getLabelArtistsGenerator(final String slug, final String uuid, final Integer start, final Integer limit, final LabelArtistsOrder order)
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
                    List<Artist> artists = BlitzrClient.this.getLabelArtists(slug, uuid, tempStart, tempLimit, order);
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

    /**
     * Fetch an Label biography. Slug or UUID are mandatory.
     * This method returns an Label with only one field set : biography.
     *
     * @param slug The Label slug
     * @param uuid The Label UUID
     * @param html Set to true if you want HTML markup in the Biography
     * @param url_scheme Urlencoded links format ( eg: '#\/{type}\/{slug}' : '%23%2F%7Btype%7D%2F%7Bslug%7D' )
     * @return Label with only one field set : biography.
     */
    public Label getLabelBiography(String slug, String uuid, Boolean html, String url_scheme)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("format", html ? "html" : null);
        params.put("url_scheme", url_scheme);
        return ApiCaller.getApi("label/biography/", Label.class, params);
    }

    /**
     * Get the identifiers of this label in other databases.
     *
     * @param slug The Label slug
     * @param uuid The Label UUID
     * @return A hash map of String and HarmoniaProvider, that identify the label in other databases
     */
    public HashMap<String, HarmoniaProvider> getLabelHarmonia(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiHashMap("label/harmonia/", String.class, HarmoniaProvider.class, params);
    }

    /**
     * Return a list of Releases by the given Label. Slug or UUID are mandatory.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @param format The format of the Release (album|single|live|all)
     * @return A list of Release with fields : slug, uuid, name, release_date, image, thumb, thumb_300, artists and format
     */
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

    /**
     * Return a Generator with the same data as getLabelReleases method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug The Label slug
     * @param uuid The Label UUID
     * @param format The format of the Release (album|single|live|all)
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return A Release Generator, help for getLabelReleases pagination
     */
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
                    List<Release> releases = BlitzrClient.this.getLabelReleases(slug, uuid, format, tempStart, tempLimit);
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

    /**
     * Return a list of Labels similar to the given Label. Slug or UUID are mandatory.
     *
     * @param slug The Label slug
     * @param uuid The Label UUID
     * @param filters Filter the returned Labels. Only location is available here.
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Label with fields : slug, uuid, name, image, thumb, thumb_300, location and tags
     */
    public List<Label> getLabelSimilar(String slug, String uuid, LabelFilters filters, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        filters.apply(params);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("label/similar/", Label.class, params);
    }

    /**
     * Return a Generator with the same data as getLabelSimilar method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug The Label slug
     * @param uuid The Label UUID
     * @param filters Filter the returned Labels. Only location is available here.
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return A Label Generator, help for getLabelSimilar pagination
     */
    public Generator<Label> getLabelSimilarGenerator(final String slug, final String uuid, final LabelFilters filters, final Integer start, final Integer limit)
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
                    List<Label> labels = BlitzrClient.this.getLabelSimilar(slug, uuid, filters, tempStart, tempLimit);
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

    /**
     * Fetch a List of Label Websites. Slug or UUID are mandatory.
     * This method returns a Label with only one field set : websites.
     *
     * @param slug The Label slug
     * @param uuid The Label UUID
     * @return Label with only one field set : websites.
     */
    public Label getLabelWebsites(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("label/websites/", Label.class, params);
    }

    /**
     * Get an Artist Radio, a List of Track from the given Artist discography.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param number The number of Tracks needed
     * @return A list of Track
     */
    public List<Track> getRadioArtist(String slug, String uuid, Integer number)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("limit", number);
        return ApiCaller.getApiList("radio/artist/", Track.class, params);
    }

    /**
     * Get an Artist Similar Radio, a List of Track from the Similar Artist discography.
     *
     * @param slug The Artist slug
     * @param uuid The Artist UUID
     * @param number The number of Tracks needed
     * @return A list of Track
     */
    public List<Track> getRadioArtistSimilar(String slug, String uuid, Integer number)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("limit", number);
        return ApiCaller.getApiList("radio/artist/similar/", Track.class, params);
    }

    /**
     * Get a Label Radio, a List of Track from the given Label discography.
     *
     * @param slug The Label slug
     * @param uuid The Label UUID
     * @param number The number of Tracks needed
     * @return A list of Track
     */
    public List<Track> getRadioLabel(String slug, String uuid, Integer number)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("limit", number);
        return ApiCaller.getApiList("radio/label/", Track.class, params);
    }

    /**
     * Get a Tag Radio, a List of Track from the given Tag catalog.
     *
     * @param slug The Artist slug
     * @param number The number of Tracks needed
     * @return A list of Track
     */
    public List<Track> getRadioTag(String slug, Integer number)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("limit", number);
        return ApiCaller.getApiList("radio/tag/", Track.class, params);
    }

    /**
     * Get an Event Radio, a List of Track from the given Event discography.
     *
     * @param slug The Event slug
     * @param uuid The Event UUID
     * @param number The number of Tracks needed
     * @return A list of Track
     */
    public List<Track> getRadioEvent(String slug, String uuid, Integer number)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        params.put("limit", number);
        return ApiCaller.getApiList("radio/event/", Track.class, params);
    }

    /**
     * Fetch a Release from Blitzr API. Slug or UUID are mandatory.
     *
     * @param slug The Release slug
     * @param uuid The Release uuid
     * @return Release
     */
    public Release getRelease(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApi("release/", Release.class, params);
    }

    /**
     * Get the Release Ids for other Services. Slug or UUID are mandatory.
     *
     * @param slug The Release slug
     * @param uuid The Release uuid
     * @return List of equivalence in other services
     */
    public HashMap<String, Service> getReleaseSources(String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiHashMap("release/sources/", String.class, Service.class, params);
    }

    /**
     * Search in multiple types of entities by query. Get the total number of results in the answer.
     *
     * @param query Your query
     * @param types List of EntityType. artist, release, label and track are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Entities depending on results
     */
    public SearchResults<SearchResult> search(String query, List<EntityType> types, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("type", (types != null) ? Utils.concatOptionsWSep(types, ",") : null);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        params.put("extras", true);
        return ApiCaller.getApiParametricType("search/", SearchResults.class, SearchResult.class, params);
    }

    /**
     * Return a Generator with the same data as searchArtist method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param query Your query
     * @param types List of EntityType. artist, release, label and track are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return An Artist Generator, help for searchArtist pagination
     */
    public Generator<SearchResult> searchGenerator(final String query, final List<EntityType> types, final Boolean autocomplete, final Integer start, final Integer limit)
    {
        return new Generator<SearchResult>() {
            @Override
            protected void run() throws InterruptedException {
                Integer tempStart = start;
                if (tempStart == null)
                    tempStart = 0;
                Integer tempLimit = limit;
                if (tempLimit == null)
                    tempLimit = 10;
                while(true) {
                    SearchResults<SearchResult> entities = BlitzrClient.this.search(query, types, autocomplete, tempStart, tempLimit);
                    for (SearchResult result: entities.getResults()) {
                        yield(result);
                    }
                    tempStart += tempLimit;
                    if (entities.getResults().size() < tempLimit) {
                        break;
                    }
                }
            }
        };
    }

    /**
     * Search Artist by query and filters.
     *
     * @param query Your query
     * @param filters List of ArtistFilters. Only location, tag and type are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Artist with fields : slug, uuid, name, image, thumb, thumb_300, disambiguation, location and tags
     */
    public List<Artist> searchArtist(String query, ArtistFilters filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        filters.apply(params);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("search/artist/", Artist.class, params);
    }

    /**
     * Search Artist by query and filters. Get the total number of results in the answer.
     *
     * @param query Your query
     * @param filters List of ArtistFilters. Only location, tag and type are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return The number of results and a list of Artist with fields : slug, uuid, name, image, thumb, thumb_300,
     * disambiguation, location and tags
     */
    public SearchResults<Artist> searchArtistWithExtras(String query, ArtistFilters filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        filters.apply(params);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        params.put("extras", true);
        return ApiCaller.getApiParametricType("search/artist/", SearchResults.class, Artist.class, params);
    }

    /**
     * Return a Generator with the same data as searchArtist method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param query Your query
     * @param filters List of ArtistFilters. Only location, tag and type are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return An Artist Generator, help for searchArtist pagination
     */
    public Generator<Artist> searchArtistGenerator(final String query, final ArtistFilters filters, final Boolean autocomplete, final Integer start, final Integer limit)
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
                    List<Artist> artists = BlitzrClient.this.searchArtist(query, filters, autocomplete, tempStart, tempLimit);
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

    /**
     * Search Label by query and filters.
     *
     * @param query Your query
     * @param filters List of LabelFilters. Only location and tag are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Label with fields : slug, uuid, name, image, thumb, thumb_300, location and tags
     */
    public List<Label> searchLabel(String query, LabelFilters filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        filters.apply(params);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("search/label/", Label.class, params);
    }

    /**
     * Search Label by query and filters. Get the total number of results in the answer.
     *
     * @param query Your query
     * @param filters List of LabelFilters. Only location and tag are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return The number of results and a list of Label with fields : slug, uuid, name, image, thumb, thumb_300,
     * location and tags
     */
    public SearchResults<Label> searchLabelWithExtras(String query, LabelFilters filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        filters.apply(params);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        params.put("extras", true);
        return ApiCaller.getApiParametricType("search/label/", SearchResults.class, Label.class, params);
    }

    /**
     * Return a Generator with the same data as searchLabel method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param query Your query
     * @param filters List of LabelFilters. Only location and tag are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return A Label Generator, help for searchLabel pagination
     */
    public Generator<Label> searchLabelGenerator(final String query, final LabelFilters filters, final Boolean autocomplete, final Integer start, final Integer limit)
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
                    List<Label> labels = BlitzrClient.this.searchLabel(query, filters, autocomplete, tempStart, tempLimit);
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

    /**
     * Search Release by query and filters.
     *
     * @param query Your query
     * @param filters List of ReleaseFilters. Only artist, tag, format_summary, label, year and location are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Release with fields : slug, uuid, name, release_date, artists, image, thumb, thumb_300,
     * location and tags
     */
    public List<Release> searchRelease(String query, ReleaseFilters filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        filters.apply(params);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("search/release/", Release.class, params);
    }

    /**
     * Search Release by query and filters. Get the total number of results in the answer.
     *
     * @param query Your query
     * @param filters List of ReleaseFilters. Only artist, tag, format_summary, year and location are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return The number of results and a list of Release with fields : slug, uuid, name, release_date, artists, image,
     * thumb, thumb_300, location and tags
     */
    public SearchResults<Release> searchReleaseWithExtras(String query, ReleaseFilters filters, Boolean autocomplete, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        filters.apply(params);
        params.put("autocomplete", autocomplete ? "true" : null);
        params.put("start", start);
        params.put("limit", limit);
        params.put("extras", true);
        return ApiCaller.getApiParametricType("search/release/", SearchResults.class, Release.class, params);
    }

    /**
     * Return a Generator with the same data as searchRelease method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param query Your query
     * @param filters List of ReleaseFilters. Only location and tag are available here.
     * @param autocomplete Enable predictive search
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return A Release Generator, help for searchRelease pagination
     */
    public Generator<Release> searchReleaseGenerator(final String query, final ReleaseFilters filters, final Boolean autocomplete, final Integer start, final Integer limit)
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
                    List<Release> releases = BlitzrClient.this.searchRelease(query, filters, autocomplete, tempStart, tempLimit);
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

    /**
     * Search Track by query and filters.
     *
     * @param query Your query
     * @param filters List of TrackFilters. Only artist, release, format_summary, year and location are available here.
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return A list of Track with fields : uuid, title, tags, artists, release
     */
    public List<Track> searchTrack(String query, TrackFilters filters, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        filters.apply(params);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("search/track/", Track.class, params);
    }

    /**
     * Search Track by query and filters. Get the total number of results in the answer.
     *
     * @param query Your query
     * @param filters List of TrackFilters. Only artist, release, format_summary, year and location are available here.
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return The number of results and a list of Track with fields : uuid, title, tags, artists, release
     */
    public SearchResults<Track> searchTrackWithExtras(String query, TrackFilters filters, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        filters.apply(params);
        params.put("start", start);
        params.put("limit", limit);
        params.put("extras", true);
        return ApiCaller.getApiParametricType("search/track/", SearchResults.class, Track.class, params);
    }

    /**
     * Return a Generator with the same data as searchTrack method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param query Your query
     * @param filters List of TrackFilters. Only artist, release, format_summary, year and location are available here.
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return A Track Generator, help for searchTrack pagination
     */
    public Generator<Track> searchTrackGenerator(final String query, final TrackFilters filters, final Integer start, final Integer limit)
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
                    List<Track> tracks = BlitzrClient.this.searchTrack(query, filters, tempStart, tempLimit);
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

    /**
     * Get Artist related products
     *
     * @param type Type of the products (cd|lp|mp3|merch)
     * @param slug Artist slug.
     * @param uuid Artist UUID
     * @return A list of Products
     */
    public List<Product> shopArtist(ProductType type, String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiList("buy/artist/" + type + "/", Product.class, params);
    }

    /**
     * Get Label related products
     *
     * @param type Type of the products (cd|lp|merch)
     * @param slug Label slug.
     * @param uuid Label UUID
     * @return A list of Products
     */
    public List<Product> shopLabel(ProductType type, String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiList("buy/label/" + type + "/", Product.class, params);
    }

    /**
     * Get Release related products
     *
     * @param type Type of the products (cd|lp|mp3)
     * @param slug Release slug.
     * @param uuid Release UUID
     * @return A list of Products
     */
    public List<Product> shopRelease(ProductType type, String slug, String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("uuid", uuid);
        return ApiCaller.getApiList("buy/release/" + type + "/", Product.class, params);
    }

    /**
     * Get Track related products
     *
     * @param uuid Track UUID
     * @return A list of Products
     */
    public List<Product> shopTrack(String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return ApiCaller.getApiList("buy/track/", Product.class, params);
    }

    /**
     * Get Tag
     *
     * @param slug Tag slug
     * @return Tag
     */
    public Tag getTag(String slug)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        return ApiCaller.getApi("tag/", Tag.class, params);
    }

    /**
     * Get Artists from a Tag
     *
     * @param slug Tag slug
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return Artist List with : uuid, slug, name, image, thumb, thumb_300, disambiguation, tags, location.
     */
    public List<Artist> getTagArtists(String slug, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("tag/artists/", Artist.class, params);
    }

    /**
     * Return a Generator with the same data as getTagArtists method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug Tag slug
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return A Artist Generator, help for getTagArtists pagination
     */
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
                    List<Artist> artists = BlitzrClient.this.getTagArtists(slug, tempStart, tempLimit);
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

    /**
     * Get Releases from a Tag
     *
     * @param slug Tag slug
     * @param start Offset for pagination
     * @param limit Limit for pagination
     * @return Release List with : uuid, slug, name, release_date, image, thumb, thumb_300, artists, tags.
     */
    public List<Release> getTagReleases(String slug, Integer start, Integer limit)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("slug", slug);
        params.put("start", start);
        params.put("limit", limit);
        return ApiCaller.getApiList("tag/releases/", Release.class, params);
    }

    /**
     * Return a Generator with the same data as getTagReleases method. Helps to paginate.
     * Slug or UUID are mandatory.
     * Refer to the Generator documentation to know how to use it.
     *
     * @param slug Tag slug
     * @param start Offset of the generator
     * @param limit Number of object to retrieve by batch
     * @return A Release Generator, help for getTagReleases pagination
     */
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
                    List<Release> releases = BlitzrClient.this.getTagReleases(slug, tempStart, tempLimit);
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

    /**
     * Get Track.
     *
     * @param uuid Track UUID
     * @return Track
     */
    public Track getTrack(String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return ApiCaller.getApi("track/", Track.class, params);
    }

    /**
     * Get Track Sources.
     *
     * @param uuid Track UUID
     * @return List of Source.
     */
    public List<Source> getTrackSources(String uuid)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return ApiCaller.getApiList("track/sources/", Source.class, params);
    }
}
