package guru.qa.rococo.service;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.repository.CountryRepository;
import guru.qa.rococo.data.repository.MuseumRepository;
import guru.qa.rococo.exception.NotFoundException;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.util.StringAsBytes;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MuseumService {
  private final MuseumRepository museumRepository;
  private final CountryRepository countryRepository;

  @Autowired
  public MuseumService(MuseumRepository museumRepository, CountryRepository countryRepository) {
    this.museumRepository = museumRepository;
    this.countryRepository = countryRepository;
  }

  @Transactional(readOnly = true)
  public @Nonnull Page<MuseumJson> getAll(@Nullable String title, @Nonnull Pageable pageable) {
    Page<MuseumEntity> museums = (title == null)
        ? museumRepository.findAll(pageable)
        : museumRepository.findAllByTitleContainsIgnoreCase(title, pageable);
    return museums.map(MuseumJson::fromEntity);
  }

  @Transactional(readOnly = true)
  public @Nonnull MuseumJson findMuseumById(@Nonnull String id) {
    return MuseumJson.fromEntity(
        museumRepository.findById(
            UUID.fromString(id)
        ).orElseThrow(
            () -> new NotFoundException("Музей не найден по id: " + id)
        )
    );
  }

  @Transactional
  public @Nonnull MuseumJson update(@Nonnull MuseumJson museum) {
    MuseumEntity museumEntity = getRequiredMuseum(museum.id());
    museumEntity.setTitle(museum.title());
    museumEntity.setCity(museum.geo().city());
    museumEntity.setDescription(museum.description());
    museumEntity.setPhoto(
        new StringAsBytes(
            museum.photo()
        ).bytes()
    );
    museumEntity.setCountry(getRequiredCountry(museum.geo().country().id()));
    return MuseumJson.fromEntity(
        museumRepository.save(museumEntity)
    );
  }

  @Transactional
  public MuseumJson add(MuseumJson museum) {
    MuseumEntity museumEntity = museum.toEntity();
    museumEntity.setCountry(getRequiredCountry(museum.geo().country().id()));
    return MuseumJson.fromEntity(
        museumRepository.save(
            museumEntity
        )
    );
  }

  private @Nonnull MuseumEntity getRequiredMuseum(@Nonnull UUID id) {
    return museumRepository.findById(id).orElseThrow(
        () -> new NotFoundException("Музей не найден по id: " + id)
    );
  }

  private @Nonnull CountryEntity getRequiredCountry(@Nonnull UUID id) {
    return countryRepository.findById(id).orElseThrow(
        () -> new NotFoundException("Страна не найдена по id: " + id)
    );
  }
}
