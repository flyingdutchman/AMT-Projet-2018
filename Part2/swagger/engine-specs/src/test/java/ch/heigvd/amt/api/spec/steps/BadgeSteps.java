package ch.heigvd.amt.api.spec.steps;

import ch.heigvd.amt.ApiException;
import ch.heigvd.amt.ApiResponse;
import ch.heigvd.amt.api.DefaultApi;
import ch.heigvd.amt.api.dto.ApplicationWithoutId;
import ch.heigvd.amt.api.dto.Badge;
import ch.heigvd.amt.api.dto.BadgeWithoutId;
import ch.heigvd.amt.api.spec.helpers.Environment;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BadgeSteps {

    private static int cnt = 0;

    private DefaultApi api;
    private ApplicationWithoutId appOne;
    private ApplicationWithoutId appTwo;
    private ApplicationWithoutId appThree;
    private String apiKeyOne;
    private String apiKeyTwo;
    private String apiKeyThree;
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private String falseApiKey = "iamfalse";
    private BadgeWithoutId badgeWithoutId;

    private ArrayList<Pair<Long, String>> badgesToDelete;


    public BadgeSteps(Environment environment) {
        api = environment.getApi();
        badgesToDelete = new ArrayList<>();
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
            apiKeyOne = api.createApplication(appOne).getApiKey();
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

    @When("^i POST it to the /badges endpoint with (KeyA|KeyB|KeyC|FakeKey|no Key)$")
    public void iPOSTItToTheBadgesEndpointWith(String apiKeyName) {
        String apiKey = getApiKeyByName(apiKeyName);
        try {
            ApiResponse apiResponse = api.createBadgeWithHttpInfo(apiKey, badgeWithoutId);
            badgesToDelete.add(new Pair<>(getIdFromResponse(apiResponse), apiKey));
            apiResponse.getHeaders().get("location");
            apiSuccessBehaviour(apiResponse);
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

    @When("^i GET the list of badges owned by (AppA|AppB|AppC) on the /badges/id endpoint$")
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

    @Given("^there are two badges in the repositories owned by (AppA|AppB|AppC) and (AppA|AppB|AppC)$")
    public void thereAreTwoBadgesInTheRepositories(String app1, String app2) {
        BadgeWithoutId badgeWithoutIdOne = new BadgeWithoutId();
        badgeWithoutIdOne.setName("Badge One");
        badgeWithoutIdOne.setImage("imageOne.png");
        BadgeWithoutId badgeWithoutIdTwo = new BadgeWithoutId();
        badgeWithoutIdTwo.setName("Badge Two");
        badgeWithoutIdTwo.setImage("imageTwo.png");
        try {
            ApiResponse apiRes = api.createBadgeWithHttpInfo(app1, badgeWithoutIdOne);
            badgesToDelete.add(new Pair<>(getIdFromResponse(apiRes), app1));
            apiRes = api.createBadgeWithHttpInfo(app2, badgeWithoutIdTwo);
            badgesToDelete.add(new Pair<>(getIdFromResponse(apiRes), app2));
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

    @When("^i GET the /badges/id endpoint with (KeyA|KeyB|KeyC|FakeKey|no Key)$")
    public void iGETTheBadgesIdEndpointWith(String apiKey) {
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

//    @After
//    public void cleanUp() {
//        //TODO delete the 3 apps
//
//        // Delete the created badges
//        for (Pair<Long, String> p : badgesToDelete) {
//            try {
//                api.deleteBadgeById(p.getValue(), p.getKey());
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    void apiSuccessBehaviour(ApiResponse apiResponse) {
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

    private Long getIdFromResponse(ApiResponse apiResponse) {
        Map headers = apiResponse.getHeaders();
        List list = (List) headers.get("Location");
        String location = (String) list.get(0);
        String sub = location.substring(location.lastIndexOf('/') + 1);
        return Long.valueOf(sub);
    }

    private String getApiKeyByName(String name) {
        switch (name) {
            case "AppA":
            case "KeyA":
                return apiKeyOne;
            case "AppB":
            case "KeyB":
                return apiKeyTwo;
            case "AppC":
            case "KeyC":
                return apiKeyThree;
            case "FakeKey":
                return falseApiKey;
        }
        return null;
    }
}
