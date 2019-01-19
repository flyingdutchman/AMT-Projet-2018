package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.RulesApi;
import ch.heigvd.amt.api.model.*;
import ch.heigvd.amt.entities.RuleEntity;
import ch.heigvd.amt.repositories.RuleRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RulesApiController implements RulesApi {

    @Autowired
    RuleRepository ruleRepository;

    @Override
    public ResponseEntity<Object> createRule(@ApiParam(value = "", required = true) @RequestBody RuleWithoutId rule) {
        RuleWithoutIdThen ruleThen = rule.getThen();
        RuleWithoutIdIf ruleIf = rule.getIf();

        if (ruleThen == null || ruleIf == null) {
            return ResponseEntity
                    .badRequest()
                    .body((Object) "The 'If' and 'Then' field are mandatory, please correct your requset");
        }

        if (ruleIf.getType() == null) {
            return ResponseEntity
                    .badRequest()
                    .body((Object) "The 'type' field in 'If' is mandatory, please correct your requset");
        }

        RuleWithoutIdThenAwardPoints thenAwardPts = ruleThen.getAwardPoints();

        // If there is nothing in Then
        if (thenAwardPts == null && ruleThen.getAwardBadgeId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body((Object) "The 'Then' field must be completed with at least one of 'awardBadge' or 'awardPoint'");
        }

        // If AwardPoints exist and one of its field is missing
        if (thenAwardPts != null && (thenAwardPts.getPointScaleId() == null || thenAwardPts.getAmount() == null)) {
            return ResponseEntity
                    .badRequest()
                    .body((Object) "The 'amount' and 'pointScale' fields of 'awardPoints are mandatory'");
        }

        RuleEntity newRuleEntity = toRuleEntity(rule);
        ruleRepository.save(newRuleEntity);
        Long id = newRuleEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Rule> getRuleById(@ApiParam(value = "", required = true) @PathVariable("ruleId") Long ruleId) {
        RuleEntity ruleEntity = ruleRepository.findOne(ruleId);
        if(ruleEntity == null) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(toRule(ruleEntity));
    }

    @Override
    public ResponseEntity<List<Rule>> getRules() {
        List<Rule> rules = new ArrayList<>();
        for (RuleEntity re : ruleRepository.findAll()) {
            rules.add(toRule(re));
        }
        return ResponseEntity.ok(rules);
    }

    private RuleEntity toRuleEntity(RuleWithoutId rule) {
        RuleEntity entity = new RuleEntity();
        if (rule.getIf() != null) {
            entity.setType(rule.getIf().getType());
        }
        if (rule.getThen() != null) {
            entity.setAwardBadge(rule.getThen().getAwardBadgeId());
            if (rule.getThen().getAwardPoints() != null) {
                entity.setPointScale(rule.getThen().getAwardPoints().getPointScaleId());
                entity.setAmount(rule.getThen().getAwardPoints().getAmount());
            }

        }
        return entity;
    }

    private Rule toRule(RuleEntity entity) {
        Rule rule = new Rule();
        rule.setId(entity.getId());

        RuleWithoutIdIf ruleIf = new RuleWithoutIdIf();
        ruleIf.setType(entity.getType());
        rule.setIf(ruleIf);

        RuleWithoutIdThen ruleThen = new RuleWithoutIdThen();
        ruleThen.setAwardBadgeId(entity.getAwardBadge());

        RuleWithoutIdThenAwardPoints ruleThenAwardPoints = new RuleWithoutIdThenAwardPoints();
        ruleThenAwardPoints.setPointScaleId(entity.getPointScale());
        ruleThenAwardPoints.setAmount(entity.getAmount());
        ruleThen.setAwardPoints(ruleThenAwardPoints);
        rule.setThen(ruleThen);

        return rule;
    }
}
