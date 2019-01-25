package ch.heigvd.amt.api.spec.steps;

import ch.heigvd.amt.ApiException;
import ch.heigvd.amt.ApiResponse;
import ch.heigvd.amt.api.DefaultApi;
import ch.heigvd.amt.api.dto.*;
import ch.heigvd.amt.api.spec.helpers.Environment;
import cucumber.api.java.After;
import cucumber.api.java.en.*;

import java.util.List;

import static org.junit.Assert.*;

public class PointScaleSteps {

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
    private PointScaleWithoutId pointScaleWithoutId;

    public PointScaleSteps(Environment environment) {
        api = environment.getApi();
    }

    @Given("^there is a PointScale server$")
    public void thereIsAPointScaleServer() {
        assertNotNull(api);
    }

    @And("^PointScale: i have a payload of three apps, two being from the same owner$")
    public void iHaveAPayloadOfThreeAppsTwoBeingFromTheSameOwner() {
        stemAppOne = new ApplicationWithoutId();
        stemAppTwo = new ApplicationWithoutId();
        stemAppThree = new ApplicationWithoutId();
        stemAppOne.setOwner(Long.MAX_VALUE);
        stemAppTwo.setOwner(Long.MAX_VALUE);
        stemAppThree.setOwner(Long.MAX_VALUE-1);
    }

    @And("^PointScale: i populate the server with them and get their respective Apikeys$")
    public void iPopulateTheServerWithThemAndGetTheirRespectiveApikeys() {
        try {
            appOne = api.createApplication(stemAppOne);
            appTwo = api.createApplication(stemAppTwo);
            appThree = api.createApplication(stemAppThree);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Given("^i have a pointScale payload$")
    public void iHaveAPointScalePayload() {
        pointScaleWithoutId = new PointScaleWithoutId();
        pointScaleWithoutId.setName("PointScale");
        pointScaleWithoutId.setDescription("This is your experience points");
    }

    @When("^i POST it to the /pointScales endpoint with (Key[ABC]|FakeKey)$")
    public void iPOSTItToThePointScalesEndpointWith(String apiKeyName) {
        try {
            apiSuccessBehaviour(api.createPointScaleWithHttpInfo(getApiKeyByName(apiKeyName), pointScaleWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^i receive a (\\d+) status code from the /pointScales endpoint$")
    public void iReceiveAStatusCodeFromThePointScalesEndpoint(int code) {
        assertEquals(code, lastStatusCode);
    }

    @And("^the newly created pointScale$")
    public void theNewlyCreatedPointScale() {
        assertTrue(lastApiResponse.getData() instanceof PointScale);
    }


    @But("^i have the location of the already existing pointScale$")
    public void iHaveTheLocationOfTheAlreadyExistingPointScale() {
        assertNotNull(lastApiException.getResponseHeaders().get("Location"));
    }

    @When("^i GET the list of pointScales owned by (App[ABC]|FakeApp) on the /pointScales endpoint$")
    public void iGETTheListOfPointScalesOwnedByAppAOnThePointScalesEndpoint(String app) {
        try {
            apiSuccessBehaviour(api.getAllPointScalesWithHttpInfo(getApiKeyByName(app)));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @When("^i GET the list of pointScales owned by (App[ABC]|FakeApp) on the /pointScale endpoint$")
    public void iGETTheListOfpointsScalesOwnedByOnThePointScaleIdEndpoint(String app) {
        try {
            apiSuccessBehaviour(api.getAllPointScalesWithHttpInfo(getApiKeyByName(app)));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @Then("^i see my pointScale in the list$")
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

    @Given("^there (?:are|is) (\\d+) pointScale(?:s?) in the repository owned by (App[ABC](?:,App[ABC])*)$")
    public void thereArePointScalesInTheRepositoryOwnedBy(int nbPointScales, String apps) {
        String[] appsArray = apps.split(",");
        try {
            for(int i = 0; i < nbPointScales; i++) {
                PointScaleWithoutId pointScaleWithoutId = new PointScaleWithoutId();
                pointScaleWithoutId.setName("PointScale "+i);
                pointScaleWithoutId.setDescription("Description");
                String apiKey = getApiKeyByName(appsArray[(i >= appsArray.length) ? (appsArray.length-1) : i]);
                api.createPointScale(apiKey, pointScaleWithoutId);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @And("^get a list of pointScales from the repository of size (\\d+)$")
    public void getAListOfPointScalesFromTheRepositoryofSSize(int size) {
        assertTrue(lastApiResponse.getData() instanceof List);
        List list = (List) (lastApiResponse.getData());
        assertEquals(size, list.size());
        Object o = (list).get(0);
        assertTrue(o instanceof PointScale);
    }

    @When("^i GET lasts step pointScale on the /pointScales/id endpoint with (Key[ABC]|FakeKey)$")
    public void iGETLastsStepPointScalesIdEndpointWith(String apiKey) {
        assertTrue(lastApiResponse.getData() instanceof PointScale);
        PointScale pointScale = (PointScale) (lastApiResponse.getData());
        try {
            apiSuccessBehaviour(api.getPointScaleByIdWithHttpInfo(getApiKeyByName(apiKey), pointScale.getId()));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @When("^i GET the /pointScales/id with an unknown id with keyA$")
    public void iGETThePointScalesIdWithAnUnknownIdWithKeyA() {
        try {
            apiSuccessBehaviour(api.getPointScaleByIdWithHttpInfo(appOne.getApiKey(), Long.MAX_VALUE));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @And("^the pointScale corresponding to the given id$")
    public void thePointScaleCorrespondingToTheGivenId() {
        assertTrue(lastApiResponse.getData() instanceof PointScale);
        PointScale pointScale = (PointScale) lastApiResponse.getData();
        assertEquals(pointScaleWithoutId.getName(), pointScale.getName());

    }

    @Given("^i have an incorrect pointScale payload$")
    public void iHaveAnIncorrectPointScalePayload() {
        pointScaleWithoutId = new PointScaleWithoutId();
        pointScaleWithoutId.setName("Incorrect");
    }

    @And("^there is a pointScale in the repository owned by (App[ABC]|FakeApp)$")
    public void thereIsAPointScaleInTheRepositoryOwnedBy(String apiApp) {
        try {
            PointScaleWithoutId pointScaleWithoutId = new PointScaleWithoutId();
            pointScaleWithoutId.setName("OldPointScale");
            pointScaleWithoutId.setDescription("OldDesc");
            apiSuccessBehaviour(api.createPointScaleWithHttpInfo(getApiKeyByName(apiApp), pointScaleWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @When("^i PUT the payload into the lasts step /pointScales/id endpoint with (Key[ABC]|FakeKey)$")
    public void iPUTThePayloadIntoTheLastsStepPointScalesIdEndpointWith(String apiKey) {
        assertTrue(lastApiResponse.getData() instanceof PointScale);
        PointScale pointScale = (PointScale) (lastApiResponse.getData());
        try {
            apiSuccessBehaviour(api.updatePointScaleByIdWithHttpInfo(getApiKeyByName(apiKey), pointScale.getId(), pointScaleWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
    }

    @When("^i PUT the payload to a unknown /pointScales/id endpoint with KeyA$")
    public void iPUTThePayloadToAUnknownPointScalesIdEndpointWithKeyA() {
        try {
            apiSuccessBehaviour(api.updatePointScaleByIdWithHttpInfo(appOne.getApiKey(), Long.MAX_VALUE, pointScaleWithoutId));
        } catch (ApiException e) {
            apiExceptionBehaviour(e);
        }
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