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
import org.joda.time.DateTime;

import static org.junit.Assert.*;

public class EventSteps {

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
    private Event event;
    private Badge badge;
    private Rule rule;
    private PointScale pointScale;
    private User user;

    public EventSteps(Environment environment) {
        api = environment.getApi();
    }

    @Given("^there is a running api$")
    public void there_is_a_running_api() {
        assertNotNull(api);
    }

    @And("^Event: i have a payload of three apps, two being from the same owner$")
    public void iHaveAPayloadOfThreeAppsTwoBeingFromTheSameOwner() {
        stemAppOne = new ApplicationWithoutId();
        stemAppTwo = new ApplicationWithoutId();
        stemAppThree = new ApplicationWithoutId();
        stemAppOne.setOwner(Long.MAX_VALUE);
        stemAppTwo.setOwner(Long.MAX_VALUE);
        stemAppThree.setOwner(Long.MAX_VALUE - 1);
    }

    @And("^Event: i populate the server with them and get their respective Apikeys$")
    public void iPopulateTheServerWithThemAndGetTheirRespectiveApikeys() {
        try {
            appOne = api.createApplication(stemAppOne);
            appTwo = api.createApplication(stemAppTwo);
            appThree = api.createApplication(stemAppThree);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Given("^I have an event payload$")
    public void i_have_an_event_payload() {
        event = new Event();
        event.setTimestamp(DateTime.now());
        event.setType("event");
        event.setUserId("foreign");
    }

    @When("^I POST it to the /events endpoint$")
    public void iPOSTItToTheEventsEndpoint() {
        try {
            apiSuccessBehaviour(api.sendEventWithHttpInfo(appOne.getApiKey(), event));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^I receive a (\\d+) status code from the /events endpoint$")
    public void iReceiveAStatusCodeFromTheEventsEndpoint(int code) {
        assertEquals(code, lastStatusCode);
    }


    @And("^A Badge online$")
    public void aBadgeOnline() {
        try {
            BadgeWithoutId badgeWithoutId = new BadgeWithoutId();
            badgeWithoutId.setName("badge");
            badgeWithoutId.setImage("image.png");
            badge = api.createBadge(appOne.getApiKey(), badgeWithoutId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @And("^A pointScale Rule online$")
    public void aPointScaleRuleOnline() {
        RuleWithoutId ruleWithoutId = new RuleWithoutId();
        RuleIf ruleIf = new RuleIf();
        ruleIf.setType("event");
        RuleThen ruleThen = new RuleThen();
        RuleThenAwardPoints awardPoint = new RuleThenAwardPoints();
        awardPoint.setPointScaleId(pointScale.getId());
        awardPoint.setAmount(10);
        ruleThen.setAwardPoints(awardPoint);
        ruleWithoutId.setIf(ruleIf);
        ruleWithoutId.setThen(ruleThen);
        try {
            rule = api.createRule(appOne.getApiKey(), ruleWithoutId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }



    @And("^A badge Rule online$")
    public void aBadgeRuleOnline() {
        RuleWithoutId ruleWithoutId = new RuleWithoutId();
        RuleIf ruleIf = new RuleIf();
        ruleIf.setType("event");
        RuleThen ruleThen = new RuleThen();
        ruleThen.setAwardBadgeId(badge.getId());
        ruleWithoutId.setIf(ruleIf);
        ruleWithoutId.setThen(ruleThen);
        try {
            rule = api.createRule(appOne.getApiKey(), ruleWithoutId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


    @And("^A  complete Rule online$")
    public void aCompleteRuleOnline() {
        RuleWithoutId ruleWithoutId = new RuleWithoutId();
        RuleIf ruleIf = new RuleIf();
        ruleIf.setType("event");
        RuleThen ruleThen = new RuleThen();
        ruleThen.setAwardBadgeId(badge.getId());
        RuleThenAwardPoints awardPoint = new RuleThenAwardPoints();
        awardPoint.setPointScaleId(pointScale.getId());
        awardPoint.setAmount(10);
        ruleThen.setAwardPoints(awardPoint);
        ruleWithoutId.setIf(ruleIf);
        ruleWithoutId.setThen(ruleThen);
        try {
            rule = api.createRule(appOne.getApiKey(), ruleWithoutId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @And("^A User online$")
    public void aUserOnline() {
        UserWithoutId userWithoutId = new UserWithoutId();
        userWithoutId.setAppId(appOne.getId());
        userWithoutId.setForeignId("foreign");
        try {
            user = api.createUser(appOne.getApiKey(), userWithoutId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @And("^The correct badge has been assigned$")
    public void theCorrectBadgeHasBeenAssigned() {
        try {
            ApiResponse<User> response = api.getUserByIdWithHttpInfo(appOne.getApiKey(), user.getId());
            User toTest = response.getData();
            boolean contains = false;
            for(Long l : toTest.getOwnedBadges()) {
                if(l.equals(badge.getId())) {
                    contains = true;
                    break;
                }
            }
            assertTrue(contains);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @And("^The correct pointScales were given$")
    public void theCorrectPointScalesWereGiven() {
        try {
            ApiResponse<User> response = api.getUserByIdWithHttpInfo(appOne.getApiKey(), user.getId());
            User toTest = response.getData();
            boolean contains = false;
            for(PointScaleScore pss : toTest.getPointScales()) {
                if(pss.getPointScaleId().equals(pointScale.getId())) {
                    contains = true;
                    break;
                }
            }
            assertTrue(contains);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @And("^A PointScale online$")
    public void aPointScaleOnline() {
        PointScaleWithoutId pointScaleWithoutId = new PointScaleWithoutId();
        pointScaleWithoutId.setName("PointScale");
        pointScaleWithoutId.setDescription("This is a pointscale");
        try {
            pointScale = api.createPointScale(appOne.getApiKey(), pointScaleWithoutId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        if (api != null && appOne != null && appTwo != null && appThree != null) {
            try {
                api.deleteAppById(appOne.getId());
                api.deleteAppById(appTwo.getId());
                api.deleteAppById(appThree.getId());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    void apiSuccessBehaviour(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
    }

    void apiExceptionBehaviour(ApiException e) {
        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = e;
        lastStatusCode = lastApiException.getCode();
    }

}
