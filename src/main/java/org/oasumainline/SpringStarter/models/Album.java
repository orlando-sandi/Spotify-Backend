package org.oasumainline.SpringStarter.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private List<Track> tracks;

    private String coverPath;

    public void addTrack(Track track) {
        if(this.tracks == null){
            this.tracks = new ArrayList<>();
        }
        if(!this.tracks.contains(track)){
            this.tracks.add(track);
        }
    }
}
