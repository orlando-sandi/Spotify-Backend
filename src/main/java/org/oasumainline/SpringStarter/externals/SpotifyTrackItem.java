package org.oasumainline.SpringStarter.externals;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

@lombok.Data
public class SpotifyTrackItem {
    private SpotifyAlbum album;
    private List<SpotifyArtist> artists;
    private List<String> availableMarkets;
    private long discNumber;
    @JsonAlias("duration_ms")
    private long durationMS;
    private boolean explicit;
    private ExternalIDS externalIDS;
    private ExternalUrls externalUrls;
    private String href;
    private String id;
    private boolean isPlayable;
    private String name;
    private long popularity;
    private Object previewURL;
    private long trackNumber;
    private String type;
    private String uri;
    private boolean isLocal;
}
