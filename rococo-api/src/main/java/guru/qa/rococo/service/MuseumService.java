package guru.qa.rococo.service;

import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.repository.MuseumRepository;
import guru.qa.rococo.exception.NotFoundException;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.util.StringAsBytes;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MuseumService {
  private final MuseumRepository museumRepository;

  @Autowired
  public MuseumService(MuseumRepository museumRepository) {
    this.museumRepository = museumRepository;
  }

  @Transactional(readOnly = true)
  public @Nonnull Page<MuseumJson> getAll(@Nonnull Pageable pageable) {
    return museumRepository.findAll(pageable).map(MuseumJson::fromEntity);
  }

  @Transactional(readOnly = true)
  public @Nonnull MuseumJson findMuseumById(@Nonnull String id) {
    return MuseumJson.fromEntity(
        museumRepository.findById(
            UUID.fromString(id)
        ).orElseThrow(NotFoundException::new)
    );
  }

  @Transactional
  public @Nonnull MuseumJson update(@Nonnull MuseumJson museum) {
    MuseumEntity museumEntity = getRequiredMuseum(museum.id());
    museumEntity.setTitle(museum.title());
    museumEntity.setAddress(museum.address());
    museumEntity.setPhoto(
        new StringAsBytes(
            museum.photo()
        ).bytes()
    );
    return MuseumJson.fromEntity(
        museumRepository.save(museumEntity)
    );
  }

  @Transactional
  public MuseumJson add(MuseumJson museum) {
    return MuseumJson.fromEntity(
        museumRepository.save(
            museum.toEntity()
        )
    );
  }

  private @Nonnull MuseumEntity getRequiredMuseum(@Nonnull UUID id) {
    return museumRepository.findById(id).orElseThrow(
        NotFoundException::new
    );
  }
}
