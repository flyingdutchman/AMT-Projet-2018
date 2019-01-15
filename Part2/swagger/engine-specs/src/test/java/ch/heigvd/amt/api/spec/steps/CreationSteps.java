package ch.heigvd.amt.api.spec.steps;

import ch.heigvd.amt.api.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ch.heigvd.amt.ApiException;
import ch.heigvd.amt.ApiResponse;
import ch.heigvd.amt.api.DefaultApi;
import ch.heigvd.amt.api.dto.BadgeWithoutId;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CreationSteps {

    private Environment environment;
    private DefaultApi api;

    BadgeWithoutId badge;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^there is a Badges server$")
    public void there_is_a_Badges_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a badge payload$")
    public void i_have_a_badge_payload() throws Throwable {
        badge = new ch.heigvd.amt.api.dto.BadgeWithoutId();
    }

    @When("^I POST it to the /badges endpoint$")
    public void i_POST_it_to_the_badges_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createBadgeWithHttpInfo(badge);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }

    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(201, lastStatusCode);
    }

}
