package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.MuseumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MuseumRepository extends JpaRepository<MuseumEntity, UUID> {

}
