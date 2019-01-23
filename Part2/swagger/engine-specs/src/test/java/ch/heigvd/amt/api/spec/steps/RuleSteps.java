package ch.heigvd.amt.api.spec.steps;

import ch.heigvd.amt.ApiException;
import ch.heigvd.amt.api.dto.*;
import ch.heigvd.amt.api.spec.helpers.Environment;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class RuleSteps extends Steps {

    private RuleWithoutId ruleWithoutId;

    public RuleSteps(Environment environment) {
        super(environment);
    }

    @Given("^there is a Rule server$")
    public void thereIsARuleServer() {
        assertNotNull(api);
    }

    @Given("^I have a rule payload$")
    public void iHaveARulePayload() {
        ruleWithoutId = new RuleWithoutId();
        RuleIf ruleIf = new RuleIf();
        ruleIf.setType("typeOne");
        RuleThen ruleThen = new RuleThen();
        ruleThen.setAwardBadgeId(1L);
        RuleThenAwardPoints ruleThenAwardPoints = new RuleThenAwardPoints();
        ruleThenAwardPoints.setPointScaleId(1L);
        ruleThenAwardPoints.setAmount(10);
        ruleThen.setAwardPoints(ruleThenAwardPoints);
        ruleWithoutId.setIf(ruleIf);
        ruleWithoutId.setThen(ruleThen);
    }

    @When("^I POST it to the /rules endpoint$")
    public void iPOSTItToTheRulesEndpoint() {
        try {
            apiSuccessBehaviour(api.createRuleWithHttpInfo(apiKey, ruleWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^I receive a (\\d+) status code from the /rules endpoint$")
    public void iReceiveAStatusCodeFromTheRulesEndpoint(int code) {
        assertEquals(code, lastStatusCode);
    }

    @And("^The newly created rule$")
    public void theNewlyCreatedRule() {
        assertTrue(lastApiResponse.getData() instanceof Rule);
    }

    @Given("^There is at least one rule in the repository$")
    public void thereIsAtLeastOneRuleInTheRepository() {
        RuleWithoutId ruleWithoutIdOne = new RuleWithoutId();
        RuleIf ruleIf = new RuleIf();
        ruleIf.setType("typeOne");
        RuleThen ruleThen = new RuleThen();
        ruleThen.setAwardBadgeId(1L);
        RuleThenAwardPoints ruleThenAwardPoints = new RuleThenAwardPoints();
        ruleThenAwardPoints.setPointScaleId(1L);
        ruleThenAwardPoints.setAmount(10);
        ruleThen.setAwardPoints(ruleThenAwardPoints);
        ruleWithoutIdOne.setIf(ruleIf);
        ruleWithoutIdOne.setThen(ruleThen);

        RuleWithoutId ruleWithoutIdTwo = new RuleWithoutId();
        RuleIf ruleIfTwo = new RuleIf();
        ruleIfTwo.setType("typeTwo");
        RuleThen ruleThenTwo = new RuleThen();
        ruleThenTwo.setAwardBadgeId(2L);
        RuleThenAwardPoints ruleThenAwardPointsTwo = new RuleThenAwardPoints();
        ruleThenAwardPointsTwo.setPointScaleId(2L);
        ruleThenAwardPointsTwo.setAmount(20);
        ruleThenTwo.setAwardPoints(ruleThenAwardPointsTwo);
        ruleWithoutIdTwo.setIf(ruleIfTwo);
        ruleWithoutIdTwo.setThen(ruleThenTwo);

        try {
            api.createRuleWithHttpInfo(apiKey, ruleWithoutIdOne);
            api.createRuleWithHttpInfo(apiKey, ruleWithoutIdTwo);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @When("^I ask for a list of registered rules with a GET on the /rules endpoint$")
    public void iAskForAListOfRegisteredRulesWithAGETOnTheRulesEndpoint() {
        try {
            apiSuccessBehaviour(api.getAllRulesWithHttpInfo(apiKey));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @And("^Get a list of rules from the repository$")
    public void getAListOfRulesFromTheRepository() {
        assertTrue(lastApiResponse.getData() instanceof List);
        Object o = ((List) (lastApiResponse.getData())).get(0);
        assertTrue(o instanceof Rule);
    }

    @Then("^I see my rule in the list$")
    public void iSeeMyRuleInTheList() {
        Rule found = null;
        List list = (List) lastApiResponse.getData();
        for (Object o : list) {
            Rule r = (Rule) o;
            if (r.getIf().getType().equals(ruleWithoutId.getIf().getType())) {
                found = r;
            }
        }
        assertNotEquals(found, null);
    }

    @When("^I GET the /rules/id endpoint$")
    public void iGETTheRulesIdEndpoint() {
        assertTrue(lastApiResponse.getData() instanceof Rule);
        Rule rule = (Rule) (lastApiResponse.getData());
        try {
            apiSuccessBehaviour(api.getRuleByIdWithHttpInfo(apiKey, rule.getId()));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @And("^The rule corresponding to the given id$")
    public void theRuleCorrespondingToTheGivenId() {
        assertTrue(lastApiResponse.getData() instanceof Rule);
        Rule rule = (Rule) lastApiResponse.getData();
        assertEquals(ruleWithoutId.getIf().getType(), rule.getIf().getType());
        assertEquals(ruleWithoutId.getThen().getAwardBadgeId(), rule.getThen().getAwardBadgeId());
        assertEquals(ruleWithoutId.getThen().getAwardPoints().getPointScaleId(), rule.getThen().getAwardPoints().getPointScaleId());
        assertEquals(ruleWithoutId.getThen().getAwardPoints().getAmount(), rule.getThen().getAwardPoints().getAmount());
    }

    @Given("^I have an incorrect rule payload$")
    public void iHaveAnIncorrectRulePayload() {
        ruleWithoutId = new RuleWithoutId();
        RuleIf ruleIf = new RuleIf();
        ruleIf.setType("This is broken");
        ruleWithoutId.setIf(ruleIf);
    }
}
