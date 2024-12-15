package org.oasumainline.SpringStarter.repositories;

import org.oasumainline.SpringStarter.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Album findOneByName(String name);
}
