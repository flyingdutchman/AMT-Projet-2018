package io.avalia.badges.api.endpoints;

import io.avalia.badges.api.EventsApi;
import io.avalia.badges.api.model.Event;
import io.avalia.badges.entities.EventEntity;
import io.avalia.badges.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class EventsApiController implements EventsApi {
    @Autowired
    EventRepository eventRepository;

    @Override
    public ResponseEntity<Object> createEvent(Event event) {
        EventEntity newEventEntity = toEventEntity(event);
        eventRepository.save(newEventEntity) ;
        Long id = newEventEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newEventEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Event>> getEvents() {

        List<Event> events = new ArrayList<>();
        for(EventEntity eventEntity : eventRepository.findAll()) {
            events.add(toEvent(eventEntity));
        }

        return ResponseEntity.ok(events);
    }

    private EventEntity toEventEntity(Event event) {
        EventEntity entity = new EventEntity();
        entity.setName(event.getName());
        entity.setDescription(event.getDescription());
        entity.setType(event.getType());
        entity.setDate(event.getDate());
        return entity;
    }

    private Event toEvent(EventEntity entity) {
        Event event = new Event();
        event.setName(entity.getName());
        event.setDescription(entity.getDescription());
        event.setType(entity.getType());
        event.setDate(entity.getDate());
        return event;
    }
}
