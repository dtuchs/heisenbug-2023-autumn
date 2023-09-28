package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.ArtistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtistRepository extends JpaRepository<ArtistEntity, UUID> {

  Page<ArtistEntity> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
}
