package org.oasumainline.SpringStarter.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class Track {
    @Id
    private String isrc;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="album_id", nullable = false)
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="artist_id", nullable=false)
    private Artist artist;

    private Long playbackSeconds;
    private boolean isExplicit;
    private LocalDateTime createdAt;
}
