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
  public @Nonnull PaintingJson findPaintingById(@Nonnull String id) {
    return PaintingJson.fromEntity(
        paintingRepository.findById(
            UUID.fromString(id)
        ).orElseThrow(
            () -> new NotFoundException("Картина не найдена по id: " + id)
        )
    );
  }

  @Transactional(readOnly = true)
  public Page<PaintingJson> findPaintingByAuthorId(String id, Pageable pageable) {
    return paintingRepository.findAllByArtist(
        artistRepository.findById(
            UUID.fromString(id)
        ).orElseThrow(
            () -> new NotFoundException("Художник не найден по id: " + id)
        ),
        pageable
    ).map(PaintingJson::fromEntity);
  }

  @Transactional
  public @Nonnull PaintingJson update(@Nonnull PaintingJson painting) {
    PaintingEntity paintingEntity = getRequiredPainting(painting.id());
    paintingEntity.setTitle(painting.title());
    paintingEntity.setDescription(painting.description());
    paintingEntity.setContent(
        new StringAsBytes(
            painting.content()
        ).bytes()
    );

    if (painting.museum() != null) {
      final UUID museumIdFromJson = painting.museum().id();
      if (isMuseumShouldBeUpdated(paintingEntity, museumIdFromJson)) {
        MuseumEntity museumEntity = museumRepository.findById(museumIdFromJson)
            .orElseThrow(
                () -> new NotFoundException("Музей не найден по id: " + museumIdFromJson)
            );
        museumEntity.addPaintings(paintingEntity);
      }
    }
    if (painting.artist() != null) {
      final UUID artistIdFromJson = painting.artist().id();
      if (isArtistShouldBeUpdated(paintingEntity, artistIdFromJson)) {
        ArtistEntity artistEntity = artistRepository.findById(artistIdFromJson)
            .orElseThrow(
                () -> new NotFoundException("Художник не найден по id: " + artistIdFromJson)
            );
        artistEntity.addPaintings(paintingEntity);
      }
    }
    return PaintingJson.fromEntity(
        paintingRepository.save(paintingEntity)
    );
  }

  @Transactional
  public PaintingJson add(PaintingJson painting) {
    PaintingEntity paintingEntity = painting.toEntity();
    paintingEntity.setArtist(artistRepository.findById(painting.artist().id())
        .orElseThrow(
            () -> new NotFoundException("Музей не найден по id: " + painting.artist().id())
        ));
    if (painting.museum() != null && painting.museum().id() != null) {
      paintingEntity.setMuseum(museumRepository.findById(painting.museum().id())
          .orElseThrow(
              () -> new NotFoundException("Художник не найден по id: " + painting.museum().id())
          ));
    }

    return PaintingJson.fromEntity(
        paintingRepository.save(
            paintingEntity
        )
    );
  }

  private @Nonnull PaintingEntity getRequiredPainting(@Nonnull UUID id) {
    return paintingRepository.findById(id).orElseThrow(
        () -> new NotFoundException("Картина не найдена по id: " + id)
    );
  }

  private boolean isMuseumShouldBeUpdated(@Nonnull PaintingEntity paintingEntity,
                                          @Nullable UUID museumIdFromJson) {
    if (museumIdFromJson == null) {
      paintingEntity.setMuseum(null);
      return false;
    } else if (paintingEntity.getMuseum() == null) {
      return true;
    } else
      return !Objects.equals(paintingEntity.getMuseum().getId(), museumIdFromJson);
  }

  private boolean isArtistShouldBeUpdated(@Nonnull PaintingEntity paintingEntity,
                                          @Nullable UUID artistIdFromJson) {
    if (artistIdFromJson == null) {
      paintingEntity.setArtist(null);
      return false;
    } else if (paintingEntity.getArtist() == null) {
      return true;
    } else
      return !Objects.equals(paintingEntity.getArtist().getId(), artistIdFromJson);
  }
}
