package ch.heigvd.amt.repositories;

import ch.heigvd.amt.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
