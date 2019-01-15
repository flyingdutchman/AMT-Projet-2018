package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.RFC3339DateFormat;
import ch.heigvd.amt.api.EventsApi;
import ch.heigvd.amt.api.model.Event;
import ch.heigvd.amt.entities.EventEntity;
import ch.heigvd.amt.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
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
        //entity.setId(event.get);
        entity.setTimestamp(event.getTimestamp().toDate());
        entity.setType(event.getType());
        entity.setUserId(event.getUserId());
        return entity;
    }

    private Event toEvent(EventEntity entity) {
        Event event = new Event();
        event.setTimestamp(event.getTimestamp());
        event.setType(entity.getType());
        event.setUserId(entity.getUserId());
        return event;
    }
}