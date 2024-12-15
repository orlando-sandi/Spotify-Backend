package org.oasumainline.SpringStarter.controllers;
import org.oasumainline.SpringStarter.dto.PageDto;
import org.oasumainline.SpringStarter.dto.TrackDto;
import org.oasumainline.SpringStarter.exceptions.SpotifyTrackNotFoundException;
import org.oasumainline.SpringStarter.exceptions.TrackAlreadyExistsException;
import org.oasumainline.SpringStarter.externals.SpotifyTrackItem;
import org.oasumainline.SpringStarter.models.Album;
import org.oasumainline.SpringStarter.models.Artist;
import org.oasumainline.SpringStarter.models.Track;
import org.oasumainline.SpringStarter.services.SpotifyService;
import org.oasumainline.SpringStarter.services.TrackService;
import org.oasumainline.SpringStarter.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    @Autowired
    private StorageService storageService;

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private TrackService trackService;

    @Value("${app.config.page-limit}")
    private Integer pageLimit;
    @GetMapping("")
    public PageDto<TrackDto> getAllTracks(
            @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Track> trackPage =  trackService.findAllTracks(pageable);
        return new PageDto<>(trackPage.map(TrackDto::new));
    }
    @PostMapping("/{isrc}")
    public ResponseEntity<TrackDto> createTrack(@PathVariable String isrc) {
        if(trackService.trackExists(isrc)) {
            throw new TrackAlreadyExistsException();
        }
        try {
            SpotifyTrackItem spotifyTrack = spotifyService.getTrack(isrc);
            Path coverPath = storageService.store(spotifyTrack.getAlbum().getImages().get(0).getUrl());
            Track track = createTrack(isrc, spotifyTrack, coverPath);
            track = trackService.save(track);
            return ResponseEntity.ok(new TrackDto(track));
        } catch (SpotifyTrackNotFoundException ex) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "The provided ISRC was not found")).build();
        }
    }

    @GetMapping("/{isrc}")
    public ResponseEntity<TrackDto> getTrack(@PathVariable String isrc) {
        Track track = trackService.find(isrc);
        return ResponseEntity.ok(new TrackDto(track));
    }
    @GetMapping("/{isrc}/cover")
    public ResponseEntity<byte[]> getTrackCover(@PathVariable String isrc) {
        Track track = trackService.find(isrc);
        Resource resource = storageService.load(track.getAlbum().getCoverPath());
        try {
            Path filePath = resource.getFile().toPath();
            String contentType = Files.probeContentType(filePath);
            if(contentType == null) {
                contentType = MediaType.IMAGE_JPEG_VALUE;
            }
            MediaType parsedMediaType = MediaType.parseMediaType(contentType);
            byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
            return ResponseEntity.status(HttpStatus.OK).contentType(parsedMediaType).body(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Track createTrack(String isrc, SpotifyTrackItem spotifyTrack, Path coverPath) {
        Artist artist = new Artist();
        artist.setName(spotifyTrack.getArtists().get(0).getName());
        Album album = new Album();
        album.setName(spotifyTrack.getAlbum().getName());
        album.setCoverPath(coverPath.getFileName().toString());
        Track track = new Track();
        track.setIsrc(isrc);
        track.setName(spotifyTrack.getName());
        track.setExplicit(spotifyTrack.isExplicit());
        track.setPlaybackSeconds(spotifyTrack.getDurationMS());
        track.setAlbum(album);
        track.setArtist(artist);
        return track;
    }
}
