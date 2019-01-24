package ch.heigvd.amt.api.spec.steps;

import ch.heigvd.amt.ApiException;
import ch.heigvd.amt.ApiResponse;
import ch.heigvd.amt.api.DefaultApi;
import ch.heigvd.amt.api.dto.Event;
import ch.heigvd.amt.api.spec.helpers.Environment;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.joda.time.DateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EventSteps {

    private DefaultApi api;
    private String apiKey;
    private String apiKeyTwo;
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private Event event;

    public EventSteps(Environment environment) {
        api = environment.getApi();
    }

    @Given("^there is a running api$")
    public void there_is_a_running_api() {
        assertNotNull(api);
    }

    @Given("^I have an event payload$")
    public void i_have_an_event_payload() {
        event = new Event();
        event.setTimestamp(DateTime.now());
        event.setType("Type");
        event.setUserId("USER1");
    }

    @When("^I POST it to the /events endpoint$")
    public void iPOSTItToTheEventsEndpoint() {
        try {
            apiSuccessBehaviour(api.sendEventWithHttpInfo(apiKey, event));
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
    }

    @And("^A Rule online$")
    public void aRuleOnline() {

    }

    @And("^The correct badge has been assigned$")
    public void theCorrectBadgeHasBeenAssigned() {

    }

    @And("^The correct pointScales were given$")
    public void theCorrectPointScalesWereGiven() {

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
