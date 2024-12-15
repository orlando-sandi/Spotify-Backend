package org.oasumainline.SpringStarter.services;

import org.oasumainline.SpringStarter.exceptions.SpotifyTrackNotFoundException;
import org.oasumainline.SpringStarter.externals.SpotifySearch;
import org.oasumainline.SpringStarter.externals.SpotifyTrackItem;
import org.oasumainline.SpringStarter.utils.SpotifyHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class SpotifyService {

    RestClient client;

    @Autowired
    SpotifyService(SpotifyHttpClient spotifyHttpClient) {
        this.client = spotifyHttpClient.getRestClient();
    }


    public SpotifyTrackItem getTrack(String isrc) {
        URI uri = UriComponentsBuilder.fromUriString("search")
                .queryParam("q", "isrc:{isrc}")
                .queryParam("type", "track")
                .encode()
                .buildAndExpand(isrc)
                .toUri();
        SpotifySearch searchResult =  client.get().uri(uri).retrieve().body(SpotifySearch.class);
        if(searchResult == null || searchResult.getTracks().getItems().isEmpty()){
            throw new SpotifyTrackNotFoundException("Could not find track with the provided isrc");
        }
        return searchResult.getTracks().getItems().get(0);
    }

}
