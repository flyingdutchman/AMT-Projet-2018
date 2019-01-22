package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.BadgesApi;
import ch.heigvd.amt.api.model.Badge;
import ch.heigvd.amt.api.model.BadgeWithoutId;
import ch.heigvd.amt.api.util.ApiHeaderBuilder;
import ch.heigvd.amt.entities.BadgeEntity;
import ch.heigvd.amt.repositories.BadgeRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgeRepository badgeRepository;

    @Override
    public ResponseEntity<Badge> createBadge(@ApiParam(value = "", required = true) @RequestBody BadgeWithoutId badge) {

        if (badge.getImage() == null || badge.getName() == null) {
            String message = "The 'name' and 'image' fields are mandatory";
            HttpHeaders responseHeaders = ApiHeaderBuilder.errorMessage(message);
            return ResponseEntity.badRequest().headers(responseHeaders).build();
        }

        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        BadgeEntity foundDouble = null;
        for (BadgeEntity be : badgeRepository.findAll()) {
            if (be.getName().equals(newBadgeEntity.getName())) {
                foundDouble = be;
                break;
            }
        }

        if (foundDouble != null) {
            String message = "A badge with the same name already exists : ";
            HttpHeaders responseHeaders = ApiHeaderBuilder.errorMessage(message);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(foundDouble.getId())
                    .toUri();
            return ResponseEntity.status(HttpStatus.CONFLICT).headers(responseHeaders).location(location).build();
        }

        badgeRepository.save(newBadgeEntity);
        Long id = newBadgeEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body(toBadge(newBadgeEntity));
    }

    @Override
    public ResponseEntity<List<Badge>> getAllBadges() {
        List<Badge> badges = new ArrayList<>();
        for (BadgeEntity badgeEntity : badgeRepository.findAll()) {
            badges.add(toBadge(badgeEntity));
        }
        return ResponseEntity.ok(badges);
    }

    @Override
    public ResponseEntity<Badge> getBadgeById(@ApiParam(value = "", required = true) @PathVariable("badgeId") Long badgeId) {
        BadgeEntity badge = badgeRepository.findOne(badgeId);
        if (badge == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(toBadge(badge));
    }

    private BadgeEntity toBadgeEntity(BadgeWithoutId badge) {
        BadgeEntity entity = new BadgeEntity();
        entity.setName(badge.getName());
        entity.setImage(badge.getImage());
        return entity;
    }

    private Badge toBadge(BadgeEntity entity) {
        Badge badge = new Badge();
        badge.setId(entity.getId());
        badge.setName(entity.getName());
        badge.setImage(entity.getImage());
        return badge;
    }
}
