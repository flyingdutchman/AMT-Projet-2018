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
    ApplicationRepository applicationRepository;

    @Override
    public ResponseEntity<Object> sendEvent(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey, @ApiParam(value = "", required = true) @RequestBody Event event) {

        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }

        //Find the rules that have this event type and apply them
        for (RuleEntity r : ruleRepository.findAll()) {
            if (r.getType().equals(event.getType()) && r.getApplicationId().equals(appId)) {

                //Get the user
                ForeignUserEntity foreignUser = null;
                for (ForeignUserEntity fue : foreignUserRepository.findAll()) {
                    if (fue.getApplicationUserId().equals(event.getUserId()) && fue.getApplicationId().equals(appId)) {
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
                    foreignUser.addOwnedBadge(r.getAwardBadge());
                }
                if (r.getPointScale() != null) {
                    foreignUser.addPointScaleProgress(r.getPointScale(), r.getAmount());
                }
            }

        }
        return new ResponseEntity<>(HttpStatus.OK);
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