package org.oasumainline.SpringStarter.services;

import org.oasumainline.SpringStarter.exceptions.TrackNotFoundException;
import org.oasumainline.SpringStarter.models.Album;
import org.oasumainline.SpringStarter.models.Artist;
import org.oasumainline.SpringStarter.models.Track;
import org.oasumainline.SpringStarter.repositories.AlbumRepository;
import org.oasumainline.SpringStarter.repositories.ArtistRepository;
import org.oasumainline.SpringStarter.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrackService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private AlbumRepository albumRepository;


    public Track save(Track track) {
        track.setCreatedAt(LocalDateTime.now());
        Artist artist = artistRepository.findOneByName(track.getArtist().getName());
        if(artist == null){
            artist = track.getArtist();
            artist.addTrack(track);
        }
        Album album = albumRepository.findOneByName(track.getArtist().getName());
        if(album == null) {
            album = track.getAlbum();
            album.addTrack(track);
            album.setArtist(artist);
        }
        track.setArtist(artistRepository.save(artist));
        track.setAlbum(albumRepository.save(album));
        return trackRepository.save(track);
    }

    public Track find(String isrc) {
        return trackRepository.findById(isrc).orElseThrow(() -> new TrackNotFoundException(isrc));
    }

    public boolean trackExists(String isrc){
        return trackRepository.existsById(isrc);
    }

    public Page<Track> findAllTracks(Pageable pageable) {
        return trackRepository.findAll(pageable);
    }
}
