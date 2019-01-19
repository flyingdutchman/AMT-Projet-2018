package ch.heigvd.amt.repositories;

import ch.heigvd.amt.entities.PointScaleEntity;
import org.springframework.data.repository.CrudRepository;

public interface PointsAwardRepository extends CrudRepository<PointScaleEntity, Long> {
}
