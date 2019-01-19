package ch.heigvd.amt.repositories;

import ch.heigvd.amt.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RuleRepository extends CrudRepository<RuleEntity, Long> {
}
