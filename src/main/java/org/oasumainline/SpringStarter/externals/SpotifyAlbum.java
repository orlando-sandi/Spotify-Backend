package org.oasumainline.SpringStarter.externals;

import java.time.LocalDate;
import java.util.List;

@lombok.Data
public class SpotifyAlbum {
    private String albumType;
    private long totalTracks;
    private List<String> availableMarkets;
    private ExternalUrls externalUrls;
    private String href;
    private String id;
    private List<SpotifyImage> images;
    private String name;
    private LocalDate releaseDate;
    private String releaseDatePrecision;
    private String type;
    private String uri;
    private List<SpotifyArtist> artists;
    private boolean isPlayable;
}
