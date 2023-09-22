package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.PaintingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaintingRepository extends JpaRepository<PaintingEntity, UUID> {

}
