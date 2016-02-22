package com.blitzr;

import com.blitzr.models.artist.Artist;
import com.blitzr.models.event.Event;
import com.blitzr.models.label.Label;
import com.blitzr.models.release.Release;
import com.blitzr.models.release.ReleaseFormat;
import com.blitzr.models.shop.Product;
import com.blitzr.models.shop.ProductType;
import com.blitzr.models.track.Track;
import com.blitzr.models.utils.City;
import com.blitzr.models.utils.SearchResults;
import com.blitzr.models.utils.Service;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Client client = new Client("a5802057a696c5017645de719fbe8ef9");

        /*for (Event event: client.getEventsGenerator(null, null, null, "Budapest", null, null, date, null, null, null, null)) {
            System.out.println(event.getName());
        }*/

        // Generator<Artist> artists = client.searchArtistGenerator("emine", new ArrayList<>(), true, null, null);

        List<Product> products = client.shopArtist(ProductType.cd, "eminem", null);

        for(Product product : products) {
            System.out.println(product.getName());
        }
    }
}
