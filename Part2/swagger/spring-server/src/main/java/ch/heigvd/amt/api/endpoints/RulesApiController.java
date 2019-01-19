package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.RulesApi;
import ch.heigvd.amt.api.model.Rule;
import ch.heigvd.amt.api.model.RuleIf;
import ch.heigvd.amt.api.model.RuleThen;
import ch.heigvd.amt.api.model.RuleThenAwardPoints;
import ch.heigvd.amt.entities.RuleEntity;
import ch.heigvd.amt.repositories.RuleRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class RulesApiController implements RulesApi {

    @Autowired
    RuleRepository ruleRepository;

    @Override
    public ResponseEntity<Object> createRule(@ApiParam(value = "" ,required=true ) @RequestBody Rule rule) {

        if(rule.getThen() == null || rule.getIf() == null) {
            return ResponseEntity
                    .badRequest()
                    .body((Object)"The 'If' and 'Then' field are mandatory, please correct your requset");
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

    private RuleEntity toRuleEntity(Rule rule) {
        RuleEntity entity = new RuleEntity();
        if(rule.getIf() != null) {
            entity.setType(rule.getIf().getType());
        }
        if(rule.getThen() != null) {
            entity.setAwardBadge(rule.getThen().getAwardBadge());
            if(rule.getThen().getAwardPoints() != null) {
                entity.setPointScale(rule.getThen().getAwardPoints().getPointScale());
                entity.setAmount(rule.getThen().getAwardPoints().getAmount());
            }

        }
        return entity;
    }

    private Rule toRule(RuleEntity entity) {
        Rule rule = new Rule();
        RuleIf ruleIf = new RuleIf();
        ruleIf.setType(entity.getType());
        RuleThen ruleThen = new RuleThen();
        ruleThen.setAwardBadge(entity.getAwardBadge());
        RuleThenAwardPoints ruleThenAwardPoints = new RuleThenAwardPoints();
        ruleThenAwardPoints.setPointScale(entity.getPointScale());
        ruleThen.setAwardPoints(ruleThenAwardPoints);
        rule.setIf(ruleIf);
        rule.setThen(ruleThen);
        return rule;
    }
}
