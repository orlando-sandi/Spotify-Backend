package org.oasumainline.SpringStarter.externals;

import java.util.List;

@lombok.Data
public class SpotifyTracks {
    private String href;
    private long limit;
    private Object next;
    private long offset;
    private Object previous;
    private long total;
    private List<SpotifyTrackItem> items;
}
