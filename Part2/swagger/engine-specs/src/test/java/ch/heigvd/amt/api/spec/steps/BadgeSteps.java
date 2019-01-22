package ch.heigvd.amt.api.spec.steps;

import ch.heigvd.amt.ApiException;
import ch.heigvd.amt.ApiResponse;
import ch.heigvd.amt.api.DefaultApi;
import ch.heigvd.amt.api.dto.Badge;
import ch.heigvd.amt.api.dto.BadgeWithoutId;
import ch.heigvd.amt.api.spec.helpers.Environment;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class BadgeSteps {

    private DefaultApi api;

    private BadgeWithoutId badgeWithoutId;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private static int cnt = 0;

    public BadgeSteps(Environment environment) {
        this.api = environment.getApi();
    }

    @Given("^there is a Badges server$")
    public void there_is_a_Badges_server() {
        assertNotNull(api);
    }

    @Given("^I have a badge payload$")
    public void i_have_a_badge_payload() {
        badgeWithoutId = new ch.heigvd.amt.api.dto.BadgeWithoutId();
        int num = cnt++;
        badgeWithoutId.setName("Badge " + num);
        badgeWithoutId.setImage("image" + num + ".png");
    }

    @When("^I POST it to the /badges endpoint$")
    public void i_POST_it_to_the_badges_endpoint() {
        try {
            apiSuccessBehaviour(api.createBadgeWithHttpInfo(badgeWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) {
        assertEquals(arg1, lastStatusCode);
    }

    @And("^The newly created badge$")
    public void theNewlyCreatedBadge() {
        assertTrue(lastApiResponse.getData() instanceof Badge);
    }

    @When("^I ask for a list of registered badges with a GET on the /badges endpoint$")
    public void iAskForAListOfRegisteredBadgesWithAGETOnTheBadgesEndpoint() {
        try {
            apiSuccessBehaviour(api.getAllBadgesWithHttpInfo());
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^I see my badge in the list$")
    public void iSeeMyBadgeInTheList() {
        Badge found = null;
        List list = (List) lastApiResponse.getData();
        for (Object o : list) {
            Badge b = (Badge) o;
            if (b.getName().equals(badgeWithoutId.getName())) {
                found = b;
            }
        }
        assertNotEquals(found, null);
    }

    @Given("^There is at least one badge in the repository$")
    public void thereIsAtLeastOneBadgeInTheRepository() {
        BadgeWithoutId badgeWithoutIdOne = new BadgeWithoutId();
        badgeWithoutIdOne.setName("Badge One");
        badgeWithoutIdOne.setImage("imageOne.png");
        BadgeWithoutId badgeWithoutIdTwo = new BadgeWithoutId();
        badgeWithoutIdTwo.setName("Badge Two");
        badgeWithoutIdTwo.setImage("imageTwo.png");
        try {
            api.createBadgeWithHttpInfo(badgeWithoutIdOne);
            api.createBadgeWithHttpInfo(badgeWithoutIdTwo);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @And("^Get a list of badges from the repository$")
    public void getAListOfBadgesFromTheRepository() {
        assertTrue(lastApiResponse.getData() instanceof List);
        Object o = ((List) (lastApiResponse.getData())).get(0);
        assertTrue(o instanceof Badge);
    }

    @When("^I GET the /badge/id endpoint$")
    public void iGETTheBadgeIdEndpoint() {
        assertTrue(lastApiResponse.getData() instanceof Badge);
        Badge badge = (Badge) (lastApiResponse.getData());
        try {
            apiSuccessBehaviour(api.getBadgesByIdWithHttpInfo(badge.getId()));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @And("^The badge corresponding to the given id$")
    public void theBadgeCorrespondingToTheGivenId() {
        assertTrue(lastApiResponse.getData() instanceof Badge);
        Badge badge = (Badge) lastApiResponse.getData();
        assertEquals(badgeWithoutId.getName(), badge.getName());
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

    @Given("^I have an incorrect badge payload$")
    public void iHaveAnIncorrectBadgePayload() {
        badgeWithoutId = new ch.heigvd.amt.api.dto.BadgeWithoutId();
        int num = cnt++;
        badgeWithoutId.setName("Badge " + num);
    }
}
