package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.model.CountryJson;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {

  @Nonnull
  Page<CountryEntity> findAllByNameContainsIgnoreCase(
      @Nonnull String name,
      @Nonnull Pageable pageable
  );

  @Nonnull
  Optional<CountryEntity> findByName(@Nonnull String name);
}
