package org.oasumainline.SpringStarter.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Album> albums;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Track> tracks;


    public void addTrack(Track track) {
        if(this.tracks == null){
            this.tracks = new ArrayList<>();
        }
        if(!this.tracks.contains(track)) {
            this.tracks.add(track);

        }

    }
    public void addAlbum(Album album) {
        if(!this.albums.contains(album)){
            this.albums.add(album);
        }
    }
}
