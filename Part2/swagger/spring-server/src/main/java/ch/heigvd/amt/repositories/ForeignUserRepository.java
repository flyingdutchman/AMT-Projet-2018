package ch.heigvd.amt.repositories;

import ch.heigvd.amt.entities.ForeignUserEntity;
import org.springframework.data.repository.CrudRepository;

public interface ForeignUserRepository extends CrudRepository<ForeignUserEntity, Long> {
}
