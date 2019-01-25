package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.BadgesApi;
import ch.heigvd.amt.api.model.Badge;
import ch.heigvd.amt.api.model.BadgeWithoutId;
import ch.heigvd.amt.api.util.ApiResponseBuilder;
import ch.heigvd.amt.entities.ApplicationEntity;
import ch.heigvd.amt.entities.BadgeEntity;
import ch.heigvd.amt.repositories.ApplicationRepository;
import ch.heigvd.amt.repositories.BadgeRepository;
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
    ApplicationRepository applicationRepository;

    @Override
    public ResponseEntity<Badge> createBadge(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey, @ApiParam(value = "", required = true) @RequestBody BadgeWithoutId badge) {

        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }

        if (badge.getImage() == null || badge.getName() == null) {
            return ApiResponseBuilder.badRequestMessage("The 'name' and 'image' fields are mandatory");
        }

        BadgeEntity newBadgeEntity = toBadgeEntity(badge, appId);
        BadgeEntity foundDouble = null;
        for (BadgeEntity be : badgeRepository.findAll()) {
            if (be.getApplicationId().equals(appId) && be.getName().equals(newBadgeEntity.getName())) {
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
        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        List<Badge> badges = new ArrayList<>();
        for (BadgeEntity badgeEntity : badgeRepository.findAll()) {
            if (badgeEntity.getApplicationId().equals(appId)) {
                badges.add(toBadge(badgeEntity));
            }
        }
        return ResponseEntity.ok(badges);
    }

    @Override
    public ResponseEntity<Badge> getBadgeById(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey, @ApiParam(value = "", required = true) @PathVariable("badgeId") Long badgeId) {
        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        BadgeEntity badge = badgeRepository.findOne(badgeId);
        if (badge == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!badge.getApplicationId().equals(appId)) {
            return ApiResponseBuilder.forbiddenMessage();
        }
        return ResponseEntity.ok(toBadge(badge));
    }

    @Override
    public ResponseEntity<Badge> updateBadgeById(@ApiParam(value = "The API key header" ,required=true ) @RequestHeader(value="apiKey", required=true) String apiKey,
                                                 @ApiParam(value = "",required=true ) @PathVariable("badgeId") Long badgeId,
                                                 @ApiParam(value = "" ,required=true ) @RequestBody BadgeWithoutId badge) {
        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }

        if (badge.getImage() == null || badge.getName() == null) {
            return ApiResponseBuilder.badRequestMessage("The 'name' and 'image' fields are mandatory");
        }

        BadgeEntity newEntity = toBadgeEntity(badge, appId);
        BadgeEntity oldEntity = badgeRepository.findOne(badgeId);
        if (oldEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!oldEntity.getApplicationId().equals(appId)) {
            return ApiResponseBuilder.forbiddenMessage();
        }
        BadgeEntity foundDouble = null;
        for (BadgeEntity be : badgeRepository.findAll()) {
            if (be.getApplicationId().equals(appId) && be.getName().equals(newEntity.getName())) {
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

        newEntity.setId(oldEntity.getId());
        badgeRepository.save(newEntity);
        Long id = newEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body(toBadge(newEntity));
    }

    private BadgeEntity toBadgeEntity(BadgeWithoutId badge, Long appId) {
        BadgeEntity entity = new BadgeEntity();
        entity.setName(badge.getName());
        entity.setImage(badge.getImage());
        entity.setApplicationId(appId);
        return entity;
    }

    private Badge toBadge(BadgeEntity entity) {
        Badge badge = new Badge();
        badge.setId(entity.getId());
        badge.setName(entity.getName());
        badge.setImage(entity.getImage());
        badge.setAppId(entity.getApplicationId());
        return badge;
    }

    private Long getAppId(String apiKey) {
        if(apiKey == null)
            return null;
        return findKey(apiKey);
    }

    private Long findKey(String key) {
        for(ApplicationEntity ake : applicationRepository.findAll()) {
            if(ake.getApiKey().equals(key)) {
                return ake.getId();
            }
        }
        return null;
    }
}
