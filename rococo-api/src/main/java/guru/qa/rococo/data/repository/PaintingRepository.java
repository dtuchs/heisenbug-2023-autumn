package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.PaintingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.annotation.Nonnull;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository<PaintingEntity, UUID> {

  @Nonnull
  Page<PaintingEntity> findAllByTitleContainsIgnoreCase(
      @Nonnull String title,
      @Nonnull Pageable pageable
  );

  @Nonnull
  Page<PaintingEntity> findAllByArtist(
      @Nonnull ArtistEntity artist,
      @Nonnull Pageable pageable
  );
}
