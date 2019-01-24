package ch.heigvd.amt.api.spec.steps;

import ch.heigvd.amt.ApiException;
import ch.heigvd.amt.ApiResponse;
import ch.heigvd.amt.api.DefaultApi;
import ch.heigvd.amt.api.dto.Application;
import ch.heigvd.amt.api.dto.ApplicationWithoutId;
import ch.heigvd.amt.api.dto.Badge;
import ch.heigvd.amt.api.dto.BadgeWithoutId;
import ch.heigvd.amt.api.spec.helpers.Environment;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class CreateBadgeSteps {

    private DefaultApi api;
    private ApplicationWithoutId appOne;
    private ApplicationWithoutId appTwo;
    private ApplicationWithoutId appThree;
    private String apiKey;
    private String apiKeyTwo;
    private String apiKeyThree;
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    boolean lastApiCallThrewException;
    int lastStatusCode;
    private static int cnt = 0;
    private BadgeWithoutId badgeWithoutId;

    public CreateBadgeSteps(Environment environment) {
        api = environment.getApi();
    }


    @Given("^there is a Badges server$")
    public void there_is_a_Badges_server() {
        assertNotNull(api);
    }

    @And("^i have a payload of three apps, two being from the same owner$")
    public void iHaveAPayloadOfThreeAppsTwoBeingFromTheSameOwner() {
        appOne = new ApplicationWithoutId();
        appTwo = new ApplicationWithoutId();
        appThree = new ApplicationWithoutId();
        appOne.setOwner(1L);
        appTwo.setOwner(1L);
        appThree.setOwner(2L);
    }

    @And("^i populate the server with them and get their respective Apikeys$")
    public void iPopulateTheServerWithThemAndGetTheirRespectiveApikeys() {
        try {
            apiKey = api.createApplication(appOne).getApiKey();
            apiKeyTwo = api.createApplication(appTwo).getApiKey();
            apiKeyThree = api.createApplication(appThree).getApiKey();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Given("^i have a badge payload$")
    public void i_have_a_badge_payload() {
        badgeWithoutId = new BadgeWithoutId();
        int num = cnt++;
        badgeWithoutId.setName("Badge " + num);
        badgeWithoutId.setImage("image" + num + ".png");
    }

    @When("^i POST it to the /badges endpoint$")
    public void i_POST_it_to_the_badges_endpoint() {
        try {
            apiSuccessBehaviour(api.createBadgeWithHttpInfo(apiKey, badgeWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^i receive a (\\d+) status code from the /badges endpoint$")
    public void i_receive_a_status_code(int code) {
        assertEquals(code, lastStatusCode);
    }

    @And("^the newly created badge$")
    public void theNewlyCreatedBadge() {
        assertTrue(lastApiResponse.getData() instanceof Badge);
    }

    @When("^i ask for a list of registered badges with a GET on the /badges endpoint$")
    public void iAskForAListOfRegisteredBadgesWithAGETOnTheBadgesEndpoint() {
        try {
            apiSuccessBehaviour(api.getAllBadgesWithHttpInfo(apiKey));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^i see my badge in the list$")
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

    @Given("^there are two badges in the repositories$")
    public void thereAreTwoBadgesInTheRepositories() {
        BadgeWithoutId badgeWithoutIdOne = new BadgeWithoutId();
        badgeWithoutIdOne.setName("Badge One");
        badgeWithoutIdOne.setImage("imageOne.png");
        BadgeWithoutId badgeWithoutIdTwo = new BadgeWithoutId();
        badgeWithoutIdTwo.setName("Badge Two");
        badgeWithoutIdTwo.setImage("imageTwo.png");
        try {
            api.createBadgeWithHttpInfo(apiKey, badgeWithoutIdOne);
            api.createBadgeWithHttpInfo(apiKey, badgeWithoutIdTwo);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @And("^get a list of badges from the repository$")
    public void getAListOfBadgesFromTheRepository() {
        assertTrue(lastApiResponse.getData() instanceof List);
        Object o = ((List) (lastApiResponse.getData())).get(0);
        assertTrue(o instanceof Badge);
    }

    @When("^i GET the /badges/id endpoint$")
    public void iGETTheBadgesIdEndpoint() {
        assertTrue(lastApiResponse.getData() instanceof Badge);
        Badge badge = (Badge) (lastApiResponse.getData());
        try {
            apiSuccessBehaviour(api.getBadgeByIdWithHttpInfo(apiKey, badge.getId()));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @And("^the badge corresponding to the given id$")
    public void theBadgeCorrespondingToTheGivenId() {
        assertTrue(lastApiResponse.getData() instanceof Badge);
        Badge badge = (Badge) lastApiResponse.getData();
        assertEquals(badgeWithoutId.getName(), badge.getName());
    }

    @Given("^i have an incorrect badge payload$")
    public void iHaveAnIncorrectBadgePayload() {
        badgeWithoutId = new BadgeWithoutId();
        int num = cnt++;
        badgeWithoutId.setName("Badge " + num);
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
