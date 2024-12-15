package org.oasumainline.SpringStarter.dto;

import lombok.Data;
import org.oasumainline.SpringStarter.models.Track;

import java.time.LocalDateTime;

@Data
public class TrackDto {

    private String isrc;
    private String artistName;
    private String albumName;
    private Long playbackSeconds;
    private String name;
    private boolean isExplicit;
    private LocalDateTime createdAt;


    public TrackDto(Track track) {
        this.isrc = track.getIsrc();
        this.artistName = track.getArtist().getName();
        this.albumName = track.getAlbum().getName();
        this.isExplicit = track.isExplicit();
        this.createdAt = track.getCreatedAt();
        this.playbackSeconds = track.getPlaybackSeconds();
        this.name = track.getName();

    }
}
