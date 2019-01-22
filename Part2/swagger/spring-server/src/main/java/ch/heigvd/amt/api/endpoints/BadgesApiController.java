package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.BadgesApi;
import ch.heigvd.amt.api.model.Badge;
import ch.heigvd.amt.api.model.BadgeWithoutId;
import ch.heigvd.amt.api.util.ApiResponseBuilder;
import ch.heigvd.amt.entities.BadgeEntity;
import ch.heigvd.amt.entities.UserEntity;
import ch.heigvd.amt.repositories.BadgeRepository;
import ch.heigvd.amt.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<Badge> createBadge(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey, @ApiParam(value = "", required = true) @RequestBody BadgeWithoutId badge) {

        UserEntity userEntity = getUser(apiKey);

        if (userEntity == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }

        if (badge.getImage() == null || badge.getName() == null) {
            return ApiResponseBuilder.badRequestMessage("The 'name' and 'image' fields are mandatory");
        }

        BadgeEntity newBadgeEntity = toBadgeEntity(badge, userEntity.getId());
        BadgeEntity foundDouble = null;
        for (BadgeEntity be : badgeRepository.findAll()) {
            if (be.getName().equals(newBadgeEntity.getName()) && be.getOwner() == newBadgeEntity.getOwner()) {
                foundDouble = be;
                break;
            }
        }

        if (foundDouble != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(foundDouble.getId())
                    .toUri();
            return ApiResponseBuilder.conflictMessage(location);
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
    public ResponseEntity<List<Badge>> getAllBadges(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey) {
        UserEntity userEntity = getUser(apiKey);
        if (userEntity == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        List<Badge> badges = new ArrayList<>();
        for (BadgeEntity badgeEntity : badgeRepository.findAll()) {
            if (badgeEntity.getOwner().equals(userEntity.getId())) {
                badges.add(toBadge(badgeEntity));
            }
        }
        return ResponseEntity.ok(badges);
    }

    @Override
    public ResponseEntity<Badge> getBadgeById(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey, @ApiParam(value = "", required = true) @PathVariable("badgeId") Long badgeId) {
        UserEntity userEntity = getUser(apiKey);
        if (userEntity == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        BadgeEntity badge = badgeRepository.findOne(badgeId);
        if (badge == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (badge.getOwner() != userEntity.getId()) {
            return ApiResponseBuilder.forbiddenMessage();
        }
        return ResponseEntity.ok(toBadge(badge));
    }

    private BadgeEntity toBadgeEntity(BadgeWithoutId badge, Long owner) {
        BadgeEntity entity = new BadgeEntity();
        entity.setName(badge.getName());
        entity.setImage(badge.getImage());
        entity.setOwner(owner);
        return entity;
    }

    private Badge toBadge(BadgeEntity entity) {
        Badge badge = new Badge();
        badge.setId(entity.getId());
        badge.setName(entity.getName());
        badge.setImage(entity.getImage());
        badge.setOwner(entity.getOwner());
        return badge;
    }

    private UserEntity getUser(String apiKey) {
        UserEntity userEntity = null;
        for (UserEntity ue : userRepository.findAll()) {
            if (ue.getApiKey() == apiKey) {
                userEntity = ue;
                break;
            }
        }
        return userEntity;
    }
}
