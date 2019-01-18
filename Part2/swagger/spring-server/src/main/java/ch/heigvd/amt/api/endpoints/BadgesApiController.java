package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.BadgesApi;
import ch.heigvd.amt.api.model.Badge;
import ch.heigvd.amt.api.model.BadgeWithoutId;
import ch.heigvd.amt.entities.BadgeEntity;
import ch.heigvd.amt.repositories.BadgeRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgeRepository badgeRepository;

    @Override
    public ResponseEntity<Object> createBadge(@ApiParam(value = "" ,required=true ) @RequestBody BadgeWithoutId badge) {

        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        badgeRepository.save(newBadgeEntity);
        Long id = newBadgeEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Badge>> getBadges() {
        List<Badge> badges = new ArrayList<>();
        for (BadgeEntity badgeEntity : badgeRepository.findAll()) {
            badges.add(toBadge(badgeEntity));
        }
        return ResponseEntity.ok(badges);
    }

    @Override
    public ResponseEntity<Badge> getBadgesById(Long badgeId) {
        BadgeEntity badge = badgeRepository.findOne(badgeId);
        if(badge == null) {
            //TODO nicer
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(toBadge(badge));
    }

    private BadgeEntity toBadgeEntity(BadgeWithoutId badge) {
        BadgeEntity entity = new BadgeEntity();
        entity.setName(badge.getName());
        entity.setImage(badge.getImage());
        entity.setType(badge.getType());
        entity.setValue(badge.getValue());
        return entity;
    }

    private Badge toBadge(BadgeEntity entity) {
        Badge badge = new Badge();
        badge.setId(entity.getId());
        badge.setName(entity.getName());
        badge.setImage(entity.getImage());
        badge.setType(entity.getType());
        badge.setValue(entity.getValue());
        return badge;
    }
}
