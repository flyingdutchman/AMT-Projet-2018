package ch.heigvd.amt.api.spec.steps;

import ch.heigvd.amt.ApiException;
import ch.heigvd.amt.ApiResponse;
import ch.heigvd.amt.api.DefaultApi;
import ch.heigvd.amt.api.dto.PointScale;
import ch.heigvd.amt.api.dto.PointScaleWithoutId;
import ch.heigvd.amt.api.spec.helpers.Environment;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class PointScaleSteps {

    private DefaultApi api;
    private String apiKey;
    private String apiKeyTwo;
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private static int cnt = 0;
    private PointScaleWithoutId pointScaleWithoutId;

    public PointScaleSteps(Environment environment) {
        api = environment.getApi();
    }

    @Given("^there is a PointScale server$")
    public void thereIsAPointScaleServer() {
        assertNotNull(api);
    }

    @Given("^I have a pointScale payload$")
    public void iHaveAPointScalePayload() {
        pointScaleWithoutId = new PointScaleWithoutId();
        int num = cnt++;
        pointScaleWithoutId.setName("Xp" + num);
        pointScaleWithoutId.setDescription("This is your experience points");
    }

    @When("^I POST it to the /pointScales endpoint$")
    public void iPOSTItToThePointScalesEndpoint() {
        try {
            apiSuccessBehaviour(api.createPointScaleWithHttpInfo(apiKey, pointScaleWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^I receive a (\\d+) status code from the /pointScales endpoint$")
    public void iReceiveAStatusCodeFromThePointScalesEndpoint(int code) {
        assertEquals(code, lastStatusCode);
    }

    @And("^The newly created pointScale$")
    public void theNewlyCreatedPointScale() {
        assertTrue(lastApiResponse.getData() instanceof PointScale);
    }

    @Given("^There is at least one pointScale in the repository$")
    public void thereIsAtLeastOnePointScaleInTheRepository() {
        PointScaleWithoutId pointScaleOne = new PointScaleWithoutId();
        pointScaleOne.setName("One");
        pointScaleOne.setDescription("OneDesc");
        PointScaleWithoutId pointScaleTwo = new PointScaleWithoutId();
        pointScaleTwo.setName("Two");
        pointScaleTwo.setDescription("TwoDesc");
        try {
            api.createPointScaleWithHttpInfo(apiKey, pointScaleOne);
            api.createPointScaleWithHttpInfo(apiKey, pointScaleTwo);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @When("^I ask for a list of registered pointScales with a GET on the /pointScales endpoint$")
    public void iAskForAListOfRegisteredPointScalesWithAGETOnThePointScalesEndpoint() {
        try {
            apiSuccessBehaviour(api.getAllPointScalesWithHttpInfo(apiKey));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @And("^Get a list of pointScales from the repository$")
    public void getAListOfPointScalesFromTheRepository() {
        assertTrue(lastApiResponse.getData() instanceof List);
        Object o = ((List) (lastApiResponse.getData())).get(0);
        assertTrue(o instanceof PointScale);
    }

    @Then("^I see my pointScale in the list$")
    public void iSeeMyPointScaleInTheList() {
        PointScale found = null;
        List list = (List) lastApiResponse.getData();
        for (Object o : list) {
            PointScale pointScale = (PointScale) o;
            if (pointScale.getName().equals(pointScaleWithoutId.getName())) {
                found = pointScale;
            }
        }
        assertNotEquals(found, null);
    }

    @When("^I GET the /pointScales/id endpoint$")
    public void iGETThePointScalesIdEndpoint() {
        assertTrue(lastApiResponse.getData() instanceof PointScale);
        PointScale pointScale = (PointScale) (lastApiResponse.getData());
        try {
            apiSuccessBehaviour(api.getPointScaleByIdWithHttpInfo(apiKey, pointScale.getId()));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @And("^The pointScale corresponding to the given id$")
    public void thePointScaleCorrespondingToTheGivenId() {
        assertTrue(lastApiResponse.getData() instanceof PointScale);
        PointScale pointScale = (PointScale) lastApiResponse.getData();
        assertEquals(pointScaleWithoutId.getName(), pointScale.getName());

    }

    @Given("^I have an incorrect pointScale payload$")
    public void iHaveAnIncorrectPointScalePayload() {
        pointScaleWithoutId = new PointScaleWithoutId();
        pointScaleWithoutId.setName("Incorrect");
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