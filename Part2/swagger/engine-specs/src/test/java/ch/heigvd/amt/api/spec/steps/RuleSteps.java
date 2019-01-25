package ch.heigvd.amt.api.spec.steps;

import ch.heigvd.amt.ApiException;
import ch.heigvd.amt.ApiResponse;
import ch.heigvd.amt.api.DefaultApi;
import ch.heigvd.amt.api.dto.*;
import ch.heigvd.amt.api.spec.helpers.Environment;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class RuleSteps {

    private DefaultApi api;
    private ApplicationWithoutId stemAppOne;
    private ApplicationWithoutId stemAppTwo;
    private ApplicationWithoutId stemAppThree;
    private Application appOne;
    private Application appTwo;
    private Application appThree;
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private RuleWithoutId ruleWithoutId;

    public RuleSteps(Environment environment) {
        api = environment.getApi();
    }

    @Given("^there is a Rules server$")
    public void thereIsARuleServer() {
        assertNotNull(api);
    }

    @And("^Rules: i have a payload of three apps, two being from the same owner$")
    public void iHaveAPayloadOfThreeAppsTwoBeingFromTheSameOwner() {
        stemAppOne = new ApplicationWithoutId();
        stemAppTwo = new ApplicationWithoutId();
        stemAppThree = new ApplicationWithoutId();
        stemAppOne.setOwner(Long.MAX_VALUE);
        stemAppTwo.setOwner(Long.MAX_VALUE);
        stemAppThree.setOwner(Long.MAX_VALUE-1);
    }

    @And("^Rules: i populate the server with them and get their respective Apikeys$")
    public void iPopulateTheServerWithThemAndGetTheirRespectiveApikeys() {
        try {
            appOne = api.createApplication(stemAppOne);
            appTwo = api.createApplication(stemAppTwo);
            appThree = api.createApplication(stemAppThree);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Given("^i have a rule payload$")
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

    @When("^i POST it to the /rules endpoint with (Key[ABC]|FakeKey)$")
    public void iPOSTItToTheRulesEndpointWithKeyA(String apiKey) {
        try {
            apiSuccessBehaviour(api.createRuleWithHttpInfo(getApiKeyByName(apiKey), ruleWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^i receive a (\\d+) status code from the /rules endpoint$")
    public void iReceiveAStatusCodeFromTheRulesEndpoint(int code) {
        assertEquals(code, lastStatusCode);
    }

    @And("^the newly created rule$")
    public void theNewlyCreatedRule() {
        assertTrue(lastApiResponse.getData() instanceof Rule);
    }

    @When("^i GET the list of rules owned by (App[ABC]|FakeApp) on the /rules endpoint$")
    public void iGETTheListOfRulesOwnedByAppAOnTheRulesEndpoint(String app) {
        try {
            apiSuccessBehaviour(api.getAllRulesWithHttpInfo(getApiKeyByName(app)));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^i see my rule in the list$")
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

    @Given("^there (?:are|is) (\\d+) rule(?:s?) in the repository owned by (App[ABC](?:,App[ABC])*)$")
    public void thereAreRulesInTheRepositoryOwnedByAppA(int nbRule, String apps) {
        String[] appsArray = apps.split(",");
        try {
            for(int i = 0; i < nbRule; i++) {
                RuleWithoutId ruleWithoutId = new RuleWithoutId();
                RuleIf ruleIf = new RuleIf();
                ruleIf.setType("type"+i);
                RuleThen ruleThen = new RuleThen();
                ruleThen.setAwardBadgeId(1L+i);
                RuleThenAwardPoints ruleThenAwardPoints = new RuleThenAwardPoints();
                ruleThenAwardPoints.setPointScaleId(1L+i);
                ruleThenAwardPoints.setAmount(10+i);
                ruleThen.setAwardPoints(ruleThenAwardPoints);
                ruleWithoutId.setIf(ruleIf);
                ruleWithoutId.setThen(ruleThen);

                String apiKey = getApiKeyByName(appsArray[(i >= appsArray.length) ? (appsArray.length-1) : i]);
                api.createRuleWithHttpInfo(apiKey, ruleWithoutId);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @And("^get a list of rules from the repository of size (\\d+)$")
    public void getAListOfRulesFromTheRepositoryOfSize(int size) {
        assertTrue(lastApiResponse.getData() instanceof List);
        List list = (List) (lastApiResponse.getData());
        assertEquals(size, list.size());
        Object o = list.get(0);
        assertTrue(o instanceof Rule);
    }


    @When("^i GET lasts step rule on the /rules/id endpoint with (Key[ABC]|FakeKey)$")
    public void iGETLastsStepRuleOnTheRulesIdEndpointWith(String apiKey) {
        assertTrue(lastApiResponse.getData() instanceof Rule);
        Rule rule = (Rule) (lastApiResponse.getData());
        try {
            apiSuccessBehaviour(api.getRuleByIdWithHttpInfo(getApiKeyByName(apiKey), rule.getId()));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @And("^the rule corresponding to the given id$")
    public void theRuleCorrespondingToTheGivenId() {
        assertTrue(lastApiResponse.getData() instanceof Rule);
        Rule rule = (Rule) lastApiResponse.getData();
        assertEquals(ruleWithoutId.getIf().getType(), rule.getIf().getType());
        assertEquals(ruleWithoutId.getThen().getAwardBadgeId(), rule.getThen().getAwardBadgeId());
        assertEquals(ruleWithoutId.getThen().getAwardPoints().getPointScaleId(), rule.getThen().getAwardPoints().getPointScaleId());
        assertEquals(ruleWithoutId.getThen().getAwardPoints().getAmount(), rule.getThen().getAwardPoints().getAmount());
    }

    @Given("^i have an incorrect rule payload$")
    public void iHaveAnIncorrectRulePayload() {
        ruleWithoutId = new RuleWithoutId();
        RuleIf ruleIf = new RuleIf();
        ruleIf.setType("This is broken");
        ruleWithoutId.setIf(ruleIf);
    }

    @When("^i GET the /rules/id with an unknown id with keyA$")
    public void iGETTheRulesIdWithAnUnknownIdWithKeyA() {
        try {
            apiSuccessBehaviour(api.getRuleByIdWithHttpInfo(appOne.getApiKey(), Long.MAX_VALUE));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @And("^there is a rule in the repository owned by (App[ABC]|FakeApp)$")
    public void thereIsARuleInTheRepositoryOwnedByAppA(String apiApp) {
        try {

            RuleWithoutId ruleWithoutId = new RuleWithoutId();
            RuleIf ruleIf = new RuleIf();
            ruleIf.setType("oldType");
            RuleThen ruleThen = new RuleThen();
            ruleThen.setAwardBadgeId(1L);
            RuleThenAwardPoints ruleThenAwardPoints = new RuleThenAwardPoints();
            ruleThenAwardPoints.setPointScaleId(1L);
            ruleThenAwardPoints.setAmount(10);
            ruleThen.setAwardPoints(ruleThenAwardPoints);
            ruleWithoutId.setIf(ruleIf);
            ruleWithoutId.setThen(ruleThen);
            apiSuccessBehaviour(api.createRuleWithHttpInfo(getApiKeyByName(apiApp), ruleWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @When("^i PUT the payload into the lasts step /rules/id endpoint with (Key[ABC]|FakeKey)$")
    public void iPUTThePayloadIntoTheLastsStepRulesIdEndpointWithKeyA(String apiKey) {
        assertTrue(lastApiResponse.getData() instanceof Rule);
        Rule rule = (Rule) (lastApiResponse.getData());
        try {
            apiSuccessBehaviour(api.updateRuleByIdWithHttpInfo(getApiKeyByName(apiKey), rule.getId(), ruleWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @When("^i PUT the payload to a unknown /rules/id endpoint with KeyA$")
    public void iPUTThePayloadToAUnknownRulesIdEndpointWithKeyA() {
        try {
            apiSuccessBehaviour(api.updateRuleByIdWithHttpInfo(appOne.getApiKey(), Long.MAX_VALUE, ruleWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    private void apiSuccessBehaviour(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
    }

    private void apiExceptionBehaviour(ApiException e) {
        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = e;
        lastStatusCode = lastApiException.getCode();
    }

    @After
    public void cleanUp() {
        if(api!= null && appOne != null && appTwo != null && appThree != null) {
            try {
                api.deleteAppById(appOne.getId());
                api.deleteAppById(appTwo.getId());
                api.deleteAppById(appThree.getId());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String getApiKeyByName(String name) {
        switch (name) {
            case "AppA":
            case "KeyA":
                return appOne.getApiKey();
            case "AppB":
            case "KeyB":
                return appTwo.getApiKey();
            case "AppC":
            case "KeyC":
                return appThree.getApiKey();
            case "FakeApp":
            case "FakeKey":
                return "iamfalse";
        }
        return null;
    }
}
