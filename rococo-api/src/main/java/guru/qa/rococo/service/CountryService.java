package guru.qa.rococo.service;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.repository.CountryRepository;
import guru.qa.rococo.data.repository.MuseumRepository;
import guru.qa.rococo.exception.NotFoundException;
import guru.qa.rococo.model.CountryJson;
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
public class CountryService {
  private final CountryRepository countryRepository;

  @Autowired
  public CountryService(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }


  @Transactional(readOnly = true)
  public @Nonnull Page<CountryJson> getAll(@Nullable String name, @Nonnull Pageable pageable) {
    Page<CountryEntity> countries = (name == null)
        ? countryRepository.findAll(pageable)
        : countryRepository.findAllByNameContainsIgnoreCase(name, pageable);
    return countries.map(CountryJson::fromEntity);
  }

  @Transactional(readOnly = true)
  public @Nonnull CountryJson findCountryById(@Nonnull String id) {
    return CountryJson.fromEntity(
        countryRepository.findById(
            UUID.fromString(id)
        ).orElseThrow(NotFoundException::new)
    );
  }

  @Transactional(readOnly = true)
  public @Nonnull CountryJson findCountryByName(@Nonnull String name) {
    return CountryJson.fromEntity(
        countryRepository.findByName(
            name
        ).orElseThrow(NotFoundException::new)
    );
  }

  private @Nonnull CountryEntity getRequiredMuseum(@Nonnull UUID id) {
    return countryRepository.findById(id).orElseThrow(
        NotFoundException::new
    );
  }
}
