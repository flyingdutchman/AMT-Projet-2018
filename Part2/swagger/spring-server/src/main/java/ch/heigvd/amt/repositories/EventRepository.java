package ch.heigvd.amt.repositories;

import ch.heigvd.amt.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository  extends CrudRepository<EventEntity, Long> {

}