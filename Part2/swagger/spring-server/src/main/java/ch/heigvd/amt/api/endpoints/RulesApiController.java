package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.RulesApi;
import ch.heigvd.amt.api.model.*;
import ch.heigvd.amt.api.util.ApiResponseBuilder;
import ch.heigvd.amt.entities.ApiKeyEntity;
import ch.heigvd.amt.entities.RuleEntity;
import ch.heigvd.amt.repositories.ApiKeyRepository;
import ch.heigvd.amt.repositories.RuleRepository;
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
public class RulesApiController implements RulesApi {

    @Autowired
    RuleRepository ruleRepository;
    @Autowired
    ApiKeyRepository apiKeyRepository;

    @Override
    public ResponseEntity<Rule> createRule(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey, @ApiParam(value = "", required = true) @RequestBody RuleWithoutId rule) {

        if (apiKey == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }

        if(!findKey(apiKey)) {
            return ApiResponseBuilder.unauthorizedMessage();
        }

        RuleThen ruleThen = rule.getThen();
        RuleIf ruleIf = rule.getIf();

        if (ruleThen == null || ruleIf == null) {
            return ApiResponseBuilder.badRequestMessage("The 'if' and 'then' field are mandatory");
        }

        if (ruleIf.getType() == null) {
            return ApiResponseBuilder.badRequestMessage("The 'type' field in 'if' is mandatory");
        }

        RuleThenAwardPoints thenAwardPts = ruleThen.getAwardPoints();

        // If there is nothing in Then
        if (thenAwardPts == null && ruleThen.getAwardBadgeId() == null) {
            return ApiResponseBuilder.badRequestMessage("At least one of the 'awardBadgeId' or 'awardPoints' should be informed");
        }

        // If AwardPoints exist and one of its field is missing
        if (thenAwardPts != null && (thenAwardPts.getPointScaleId() == null || thenAwardPts.getAmount() == null)) {
            return ApiResponseBuilder.badRequestMessage("The 'amount' and 'pointScaleId' fields of 'awardPoints are mandatory'");
        }

        RuleEntity newRuleEntity = toRuleEntity(rule, apiKey);
        ruleRepository.save(newRuleEntity);
        Long id = newRuleEntity.getId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body(toRule(newRuleEntity));
    }

    @Override
    public ResponseEntity<Rule> getRuleById(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey, @ApiParam(value = "", required = true) @PathVariable("ruleId") Long ruleId) {
        if (apiKey == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        if(!findKey(apiKey)) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        RuleEntity ruleEntity = ruleRepository.findOne(ruleId);
        if (ruleEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!ruleEntity.getApiKey().equals(apiKey)) {
            return ApiResponseBuilder.forbiddenMessage();
        }
        return ResponseEntity.ok(toRule(ruleEntity));
    }

    @Override
    public ResponseEntity<List<Rule>> getAllRules(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey) {
        if (apiKey == null) {
            return ApiResponseBuilder.forbiddenMessage();
        }
        if(!findKey(apiKey)) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        List<Rule> rules = new ArrayList<>();
        for (RuleEntity re : ruleRepository.findAll()) {
            if(re.getApiKey().equals(apiKey)) {
                rules.add(toRule(re));
            }
        }
        return ResponseEntity.ok(rules);
    }

    private RuleEntity toRuleEntity(RuleWithoutId rule, String apiKey) {
        RuleEntity entity = new RuleEntity();
        entity.setType(rule.getIf().getType());
        entity.setAwardBadge(rule.getThen().getAwardBadgeId());
        if (rule.getThen().getAwardPoints() != null) {
            entity.setPointScale(rule.getThen().getAwardPoints().getPointScaleId());
            entity.setAmount(rule.getThen().getAwardPoints().getAmount());
        }
        entity.setApiKey(apiKey);
        return entity;
    }

    private Rule toRule(RuleEntity entity) {
        Rule rule = new Rule();
        rule.setId(entity.getId());

        RuleIf ruleIf = new RuleIf();
        ruleIf.setType(entity.getType());
        rule.setIf(ruleIf);

        RuleThen ruleThen = new RuleThen();
        ruleThen.setAwardBadgeId(entity.getAwardBadge());

        RuleThenAwardPoints ruleThenAwardPoints = new RuleThenAwardPoints();
        ruleThenAwardPoints.setPointScaleId(entity.getPointScale());
        ruleThenAwardPoints.setAmount(entity.getAmount());
        ruleThen.setAwardPoints(ruleThenAwardPoints);
        rule.setThen(ruleThen);

        return rule;
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
