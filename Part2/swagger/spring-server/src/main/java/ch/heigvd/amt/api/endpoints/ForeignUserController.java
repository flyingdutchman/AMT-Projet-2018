package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.UsersApi;
import ch.heigvd.amt.api.model.PointScaleScore;
import ch.heigvd.amt.api.model.User;
import ch.heigvd.amt.api.model.UserWithoutId;
import ch.heigvd.amt.api.util.ApiResponseBuilder;
import ch.heigvd.amt.entities.ApplicationEntity;
import ch.heigvd.amt.entities.ForeignUserEntity;
import ch.heigvd.amt.repositories.ApplicationRepository;
import ch.heigvd.amt.repositories.ForeignUserRepository;
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
public class ForeignUserController implements UsersApi {

    @Autowired
    ForeignUserRepository foreignUserRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public ResponseEntity<User> createUser(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey,
                                           @ApiParam(value = "", required = true) @RequestBody UserWithoutId user) {

        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }

        if (user.getAppId() == null || user.getForeignId() == null) {
            return ApiResponseBuilder.badRequestMessage("The 'foreignId' and 'appId' fields are mandatory");
        }

        ForeignUserEntity newForeignUserEntity = toForeignUserEntity(user, appId);
        ForeignUserEntity foundDouble = null;
        for (ForeignUserEntity fue : foreignUserRepository.findAll()) {
            if (fue.getApplicationId().equals(appId) && fue.getForeignId().equals(newForeignUserEntity.getForeignId())) {
                foundDouble = fue;
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

        foreignUserRepository.save(newForeignUserEntity);
        Long id = newForeignUserEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newForeignUserEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body(toUser(newForeignUserEntity));
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey) {
        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        List<User> users = new ArrayList<>();
        for (ForeignUserEntity ue : foreignUserRepository.findAll()) {
            if (ue.getApplicationId().equals(appId)) {
                users.add(toUser(ue));
            }
        }
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<User> getUserById(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey,
                                            @ApiParam(value = "", required = true) @PathVariable("userId") Long userId) {

        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        ForeignUserEntity entity = foreignUserRepository.findOne(userId);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!entity.getApplicationId().equals(appId)) {
            return ApiResponseBuilder.forbiddenMessage();
        }
        return ResponseEntity.ok(toUser(entity));
    }

    private ForeignUserEntity toForeignUserEntity(UserWithoutId userWithoutId, Long appId) {
        ForeignUserEntity entity = new ForeignUserEntity();
        entity.setForeignId(userWithoutId.getForeignId());
        entity.setApplicationId(appId);
        return entity;
    }

    private User toUser(ForeignUserEntity entity) {
        User user = new User();
        user.setForeignId(entity.getForeignId());
        user.setOwnedBadges(entity.getBadgeOwned());
        ArrayList<PointScaleScore> list = new ArrayList<>();
        for (Long l : entity.getPointScaleProgress().keySet()) {
            PointScaleScore pointScaleScore = new PointScaleScore();
            pointScaleScore.setPointScaleId(l);
            pointScaleScore.amount(entity.getPointScaleProgress().get(l));
            list.add(pointScaleScore);
        }
        user.setPointScales(list);
        user.setAppId(entity.getApplicationId());
        return user;
    }

    private Long getAppId(String apiKey) {
        if (apiKey == null)
            return null;
        return findKey(apiKey);
    }

    private Long findKey(String key) {
        for (ApplicationEntity ake : applicationRepository.findAll()) {
            if (ake.getApiKey().equals(key)) {
                return ake.getId();
            }
        }
        return null;
    }
}
