package io.avalia.badges.repositories;

import io.avalia.badges.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository  extends CrudRepository<EventEntity, Long> {

}
