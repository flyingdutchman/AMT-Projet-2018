package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.EventsApi;
import ch.heigvd.amt.api.model.Event;
import ch.heigvd.amt.api.util.ApiResponseBuilder;
import ch.heigvd.amt.entities.*;
import ch.heigvd.amt.repositories.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")


/**
 * Here is where the buisness core is
 */
@Controller
public class EventsApiController implements EventsApi {

    @Autowired
    RuleRepository ruleRepository;
    @Autowired
    ForeignUserRepository foreignUserRepository;
    @Autowired
    BadgeAwardRepository badgeAwardRepository;
    @Autowired
    PointsAwardRepository pointsAwardRepository;
    @Autowired
    ApiKeyRepository apiKeyRepository;

    @Override
    public ResponseEntity<Object> sendEvent(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey, @ApiParam(value = "", required = true) @RequestBody Event event) {

        if (apiKey == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        if(!findKey(apiKey)) {
            return ApiResponseBuilder.unauthorizedMessage();
        }

        //Find the rules that have this event type and apply them
        for (RuleEntity r : ruleRepository.findAll()) {
            if (r.getType().equals(event.getType()) && r.getApiKey().equals(apiKey)) {

                //Get the user
                ForeignUserEntity foreignUser = null;
                for (ForeignUserEntity fue : foreignUserRepository.findAll()) {
                    if (fue.getApplicationUserId().equals(event.getUserId()) && fue.getApiKey().equals(apiKey)) {
                        foreignUser = fue;
                        break;
                    }
                }
                if (foreignUser == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                // Add the badge and points
                if (r.getAwardBadge() != null) {
                    // Record Reward and If the badge is not already owned, add it
                    boolean hasCreatedBadge = foreignUser.addOwnedBadge(r.getAwardBadge());
                    if (hasCreatedBadge) {
                        // And record the award event
                        BadgeAwardEntity badgeAwardEntity = new BadgeAwardEntity();
                        badgeAwardEntity.setBadgeId(r.getAwardBadge());
                        badgeAwardEntity.setTimestamp(event.getTimestamp());
                        badgeAwardEntity.setUserId(foreignUser.getId());
                        badgeAwardEntity.setApiKey(apiKey);
                        badgeAwardRepository.save(badgeAwardEntity);
                    }
                }
                if (r.getPointScale() != null) {
                    foreignUser.addPointScaleProgress(r.getPointScale(), r.getAmount());
                    // Record Reward
                    PointsAwardEntity pointsAwardEntity = new PointsAwardEntity();
                    pointsAwardEntity.setAmount(r.getAmount());
                    pointsAwardEntity.setPointScaleId(r.getPointScale());
                    pointsAwardEntity.setTimestamp(event.getTimestamp());
                    pointsAwardEntity.setUserId(foreignUser.getId());
                    pointsAwardEntity.setApiKey(apiKey);
                }
            }

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean findKey(String key) {
        for(ApiKeyEntity ake : apiKeyRepository.findAll()) {
            if(ake.getApiKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
}