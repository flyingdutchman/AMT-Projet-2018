package ch.heigvd.amt.api.spec.steps;

import ch.heigvd.amt.ApiException;
import ch.heigvd.amt.ApiResponse;
import ch.heigvd.amt.api.DefaultApi;
import ch.heigvd.amt.api.dto.Application;
import ch.heigvd.amt.api.dto.ApplicationWithoutId;
import ch.heigvd.amt.api.dto.Badge;
import ch.heigvd.amt.api.dto.BadgeWithoutId;
import ch.heigvd.amt.api.spec.helpers.Environment;
import cucumber.api.java.After;
import cucumber.api.java.en.*;

import java.util.List;

import static org.junit.Assert.*;

public class BadgeSteps {

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
    private BadgeWithoutId badgeWithoutId;

    public BadgeSteps(Environment environment) {
        api = environment.getApi();
    }

    @Given("^there is a Badges server$")
    public void there_is_a_Badges_server() {
        assertNotNull(api);
    }

    @And("^i have a payload of three apps, two being from the same owner$")
    public void iHaveAPayloadOfThreeAppsTwoBeingFromTheSameOwner() {
        stemAppOne = new ApplicationWithoutId();
        stemAppTwo = new ApplicationWithoutId();
        stemAppThree = new ApplicationWithoutId();
        stemAppOne.setOwner(Long.MAX_VALUE);
        stemAppTwo.setOwner(Long.MAX_VALUE);
        stemAppThree.setOwner(Long.MAX_VALUE - 1);
    }

    @And("^i populate the server with them and get their respective Apikeys$")
    public void iPopulateTheServerWithThemAndGetTheirRespectiveApikeys() {
        try {
            appOne = api.createApplication(stemAppOne);
            appTwo = api.createApplication(stemAppTwo);
            appThree = api.createApplication(stemAppThree);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Given("^i have a badge payload$")
    public void i_have_a_badge_payload() {
        badgeWithoutId = new BadgeWithoutId();
        badgeWithoutId.setName("Badge");
        badgeWithoutId.setImage("image.png");
    }

    @When("^i POST it to the /badges endpoint with (Key[ABC]|FakeKey)$")
    public void iPOSTItToTheBadgesEndpointWith(String apiKeyName) {
        try {
            apiSuccessBehaviour(api.createBadgeWithHttpInfo(getApiKeyByName(apiKeyName), badgeWithoutId));
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

    @But("^i have the location of the already existing badge$")
    public void iHaveTheLocationOfTheAlreadyExistingBadge() {
        assertNotNull(lastApiException.getResponseHeaders().get("Location"));
    }

    @When("^i GET the list of badges owned by (App[ABC]|FakeApp) on the /badges endpoint$")
    public void iGETTheListOfBadgesOwnedByOnTheBadgesIdEndpoint(String app) {
        try {
            apiSuccessBehaviour(api.getAllBadgesWithHttpInfo(getApiKeyByName(app)));
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

    @Given("^there (?:are|is) (\\d+) badge(?:s?) in the repository owned by (App[ABC](?:,App[ABC])*)$")
    public void thereAreBadgesInTheRepositoriesOwnedByAppA(int nbBadges, String apps) {
        String[] appsArray = apps.split(",");
        try {
            for (int i = 0; i < nbBadges; i++) {
                BadgeWithoutId badgeWithoutId = new BadgeWithoutId();
                badgeWithoutId.setName("Badge " + i);
                badgeWithoutId.setImage("image" + i + ".png");
                String apiKey = getApiKeyByName(appsArray[(i >= appsArray.length) ? (appsArray.length - 1) : i]);
                api.createBadge(apiKey, badgeWithoutId);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @And("^get a list of badges from the repository of size (\\d+)$")
    public void getAListOfBadgesFromTheRepositoryOfSize(int size) {
        assertTrue(lastApiResponse.getData() instanceof List);
        List list = (List) (lastApiResponse.getData());
        assertEquals(size, list.size());
        Object o = list.get(0);
        assertTrue(o instanceof Badge);
    }

    @When("^i GET lasts step badge on the /badges/id endpoint with (Key[ABC]|FakeKey)$")
    public void iGETTheBadgesIdEndpointWith(String apiKey) {
        assertTrue(lastApiResponse.getData() instanceof Badge);
        Badge badge = (Badge) (lastApiResponse.getData());
        try {
            apiSuccessBehaviour(api.getBadgeByIdWithHttpInfo(getApiKeyByName(apiKey), badge.getId()));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @When("^i GET the /badges/id with an unknown id with keyA$")
    public void iGETTheBadgesIdWithAnUnknownIdWithKeyA() {
        try {
            apiSuccessBehaviour(api.getBadgeByIdWithHttpInfo(appOne.getApiKey(), Long.MAX_VALUE));
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
        badgeWithoutId.setName("Faux Badge");
    }

    @And("^there is a badge in the repository owned by (App[ABC]|FakeApp)$")
    public void thereIsABadgeInTheRepositoryOwnedBy(String apiApp) {
        try {
            BadgeWithoutId badgeWithoutId = new BadgeWithoutId();
            badgeWithoutId.setName("OldBadge");
            badgeWithoutId.setImage("OldImage.png");
            apiSuccessBehaviour(api.createBadgeWithHttpInfo(getApiKeyByName(apiApp), badgeWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @When("^i PUT the payload into the lasts step /badges/id endpoint with (Key[ABC]|FakeKey)$")
    public void iPUTThePayloadIntoTheLastsStepBadgesIdEndpointWith(String apiKey) {
        assertTrue(lastApiResponse.getData() instanceof Badge);
        Badge badge = (Badge) (lastApiResponse.getData());
        try {
            apiSuccessBehaviour(api.updateBadgeByIdWithHttpInfo(getApiKeyByName(apiKey), badge.getId(), badgeWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @When("^i PUT the payload to a unknown /badges/id endpoint with KeyA$")
    public void iPUTThePayloadToAUnknownBadgesIdEndpointWithKeyA() {
        try {
            apiSuccessBehaviour(api.updateBadgeByIdWithHttpInfo(appOne.getApiKey(), Long.MAX_VALUE, badgeWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
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
