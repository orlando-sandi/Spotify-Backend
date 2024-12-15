package org.oasumainline.SpringStarter.repositories;

import org.oasumainline.SpringStarter.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, String> {


}
