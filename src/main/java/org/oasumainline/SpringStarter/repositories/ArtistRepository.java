package org.oasumainline.SpringStarter.repositories;

import org.oasumainline.SpringStarter.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findOneByName(String name);
}
