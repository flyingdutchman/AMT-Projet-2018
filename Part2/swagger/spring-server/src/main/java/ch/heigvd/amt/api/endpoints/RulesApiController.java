package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.RulesApi;
import ch.heigvd.amt.api.model.Rule;
import ch.heigvd.amt.entities.RuleEntity;
import ch.heigvd.amt.repositories.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity<Object> createRule(Rule rule) {
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
    public ResponseEntity<List<Rule>> getRules() {

        List<Rule> rules = new ArrayList<>();
        for(RuleEntity ruleEntity : ruleRepository.findAll()) {
            rules.add(toRule(ruleEntity));
        }

        return ResponseEntity.ok(rules);
    }

    private RuleEntity toRuleEntity(Rule rule) {
        RuleEntity entity = new RuleEntity();
        entity.setName(rule.getName());
        entity.setDescription(rule.getDescription());
        entity.setAction(rule.getAction());
        return entity;
    }

    private Rule toRule(RuleEntity entity) {
        Rule rule = new Rule();
        rule.setName(entity.getName());
        rule.setDescription(entity.getDescription());
        rule.setAction(entity.getAction());
        return rule;
    }
}
