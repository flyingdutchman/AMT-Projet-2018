package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.EventsApi;
import ch.heigvd.amt.api.model.Event;
import ch.heigvd.amt.entities.*;
import ch.heigvd.amt.repositories.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")


/**
 * Here is where the buisness core is
 */
@Controller
public class EventsApiController implements EventsApi {

    @Autowired
    RuleRepository ruleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BadgeAwardRepository badgeAwardRepository;
    @Autowired
    PointsAwardRepository pointsAwardRepository;

    @Override
    public ResponseEntity<Object> sendEvent(@ApiParam(value = "" ,required=true ) @RequestBody Event event) {

        //Find the rules that have this event type and apply them
        for(RuleEntity r : ruleRepository.findAll()) {
            if(r.getType().equals(event.getType())) {

                //Get the user
                UserEntity user = null;
                for(UserEntity ue : userRepository.findAll()) {
                    if(ue.getApplicationUserId().equals(event.getUserId())) {
                        user = ue;
                        break;
                    }
                }
                if(user == null) {
                    return new ResponseEntity<>((Object)"UserId not found", HttpStatus.NOT_FOUND);
                }

                // Add the badges and points
                if(r.getAwardBadge() != null) {
                    user.addOwnedBadge(r.getAwardBadge());
                    // Record Reward
                    BadgeAwardEntity badgeAwardEntity = new BadgeAwardEntity();
                    badgeAwardEntity.setBadgeId(r.getAwardBadge());
                    badgeAwardEntity.setTimestamp(event.getTimestamp());
                    badgeAwardEntity.setUserId(user.getId());
                    badgeAwardRepository.save(badgeAwardEntity);
                }
                if(r.getPointScale() != null) {
                    user.addPointScaleProgress(r.getPointScale(), r.getAmount());
                    // Record Reward
                    PointsAwardEntity pointsAwardEntity = new PointsAwardEntity();
                    pointsAwardEntity.setAmount(r.getAmount());
                    pointsAwardEntity.setPointScaleId(r.getPointScale());
                    pointsAwardEntity.setTimestamp(event.getTimestamp());
                    pointsAwardEntity.setUserId(user.getId());
                }
            }

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}