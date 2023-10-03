package guru.qa.rococo.service;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.PaintingEntity;
import guru.qa.rococo.data.repository.ArtistRepository;
import guru.qa.rococo.data.repository.MuseumRepository;
import guru.qa.rococo.data.repository.PaintingRepository;
import guru.qa.rococo.exception.NotFoundException;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.model.util.StringAsBytes;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class PaintingService {
  private final PaintingRepository paintingRepository;
  private final MuseumRepository museumRepository;
  private final ArtistRepository artistRepository;

  @Autowired
  public PaintingService(PaintingRepository paintingRepository,
                         MuseumRepository museumRepository,
                         ArtistRepository artistRepository) {
    this.paintingRepository = paintingRepository;
    this.museumRepository = museumRepository;
    this.artistRepository = artistRepository;
  }

  @Transactional(readOnly = true)
  public @Nonnull Page<PaintingJson> getAll(@Nullable String title, @Nonnull Pageable pageable) {
    Page<PaintingEntity> paintings = (title == null)
        ? paintingRepository.findAll(pageable)
        : paintingRepository.findAllByTitleContainsIgnoreCase(title, pageable);
    return paintings.map(PaintingJson::fromEntity);
  }

  @Transactional(readOnly = true)
  public @Nonnull PaintingJson findArtistById(@Nonnull String id) {
    return PaintingJson.fromEntity(
        paintingRepository.findById(
            UUID.fromString(id)
        ).orElseThrow(NotFoundException::new)
    );
  }

  @Transactional
  public @Nonnull PaintingJson update(@Nonnull PaintingJson painting) {
    PaintingEntity paintingEntity = getRequiredPainting(painting.id());
    paintingEntity.setTitle(painting.title());
    paintingEntity.setContent(
        new StringAsBytes(
            painting.content()
        ).bytes()
    );

    if (painting.museum() != null) {
      final UUID museumIdFromJson = painting.museum().id();
      if (isMuseumShouldBeUpdated(paintingEntity, museumIdFromJson)) {
        MuseumEntity museumEntity = museumRepository.findById(museumIdFromJson)
            .orElseThrow(NotFoundException::new);
        museumEntity.addPaintings(paintingEntity);
      }
    }
    if (painting.artist() != null) {
      final UUID artistIdFromJson = painting.artist().id();
      if (isArtistShouldBeUpdated(paintingEntity, artistIdFromJson)) {
        ArtistEntity artistEntity = artistRepository.findById(artistIdFromJson)
            .orElseThrow(NotFoundException::new);
        artistEntity.addPaintings(paintingEntity);
      }
    }
    return PaintingJson.fromEntity(
        paintingRepository.save(paintingEntity)
    );
  }

  @Transactional
  public PaintingJson add(PaintingJson painting) {
    return PaintingJson.fromEntity(
        paintingRepository.save(
            painting.toEntity()
        )
    );
  }

  private @Nonnull PaintingEntity getRequiredPainting(@Nonnull UUID id) {
    return paintingRepository.findById(id).orElseThrow(
        NotFoundException::new
    );
  }

  private boolean isMuseumShouldBeUpdated(@Nonnull PaintingEntity paintingEntity,
                                          @Nullable UUID museumIdFromJson) {
    return paintingEntity.getMuseum() == null ||
        !Objects.equals(paintingEntity.getMuseum().getId(), museumIdFromJson);
  }

  private boolean isArtistShouldBeUpdated(@Nonnull PaintingEntity paintingEntity,
                                          @Nullable UUID artistIdFromJson) {
    return paintingEntity.getArtist() == null ||
        !Objects.equals(paintingEntity.getArtist().getId(), artistIdFromJson);
  }
}
