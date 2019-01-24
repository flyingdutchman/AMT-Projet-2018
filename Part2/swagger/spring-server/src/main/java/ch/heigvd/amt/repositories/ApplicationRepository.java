package ch.heigvd.amt.repositories;

import ch.heigvd.amt.entities.ApplicationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<ApplicationEntity, Long> {
}
