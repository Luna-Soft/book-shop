package com.zutode.bookshopclone.integration.stepdefs;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.zutode.bookshopclone.integration.utils.DatabaseTestUtils;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
public class ApiStepDefs {

    @Autowired
    private DatabaseTestUtils databaseTestUtils;
    private final String SERVER_URL = "http://localhost";
    private int port = 8080;
    private OkHttpClient client = new OkHttpClient();
    private String requestContent;
    private MediaType mediaType;
    private RequestBody requestBody;
    private Response response;
    private String responseBody;
    private Request.Builder requestBuilder;
    private Map<String, Long> lastAddedResourceId;

    @Before
    public void prepareClient(@SuppressWarnings("unused") Scenario scenario) {
        requestBuilder = new Request.Builder();
        requestBody = null;
        response = null;
        responseBody = null;
        lastAddedResourceId = new HashMap<>();
    }

    @Given("clean database")
    public void cleanDatabase() {
        databaseTestUtils.resetDatabase();
    }

    @Given("test users in database")
    public void addTestUserToDatabase(){
        databaseTestUtils.addTestUsers();
    }


    @Given("^I have the (json|plain|x-www-form-urlencoded) payload:$")
    public void iHaveThePayload(String type, String content) {
        requestContent = content;
        switch (type) {
            case "json":
                mediaType = MediaType.parse("application/json; charset=utf-8");
                requestBody = RequestBody.create(mediaType, content);
                break;
            case "x-www-form-urlencoded":
                mediaType = MediaType.parse("application/x-www-form-urlencoded");
                requestBody = RequestBody.create(mediaType, content);
                break;
            default:
                mediaType = MediaType.parse("text/plain; charset=utf-8");
                requestBody = RequestBody.create(mediaType, content);
                break;
        }
    }


    @When("^I request \"(GET|PUT|POST|DELETE|PATCH) ([^\"]*)\"$")
    @SneakyThrows
    public void iRequestPassedUrl(String method, String url) {
        final String encodedResource = url
                .replaceAll("^[/]+", "")
                .replaceAll("\\n\\s*", "");

        URL newUrl = new URL(SERVER_URL + ":" + port + "/" + encodedResource);
        if (method.equals("GET")) {
            requestBody = null;
            requestContent = null;
        }
        Request request = requestBuilder
                .url(newUrl)
                .method(method, requestBody)
                .build();
        log.info("\nRequest: {} {} with body \n{}", method, url, requestContent);
        response = client.newCall(request).execute();
        assert response.body() != null;
        responseBody = response.body().string();
        if (!responseBody.isEmpty() && responseBody.charAt(0) == '{') {
            JSONObject jsonObject = new JSONObject(responseBody);
            log.info("\nResponse: \n{} ", jsonObject.toString(4));
        } else {
            log.info("\nResponse: \n{} ", responseBody);
        }
        if (method.equals("POST")) {
            String key = url.substring(url.lastIndexOf("/") + 1);
            try {
                Long resourceId = JsonPath.parse(responseBody)
                        .read(String.format("$.%s_id", key), Long.class);
                lastAddedResourceId.put(key, resourceId);
            } catch (Exception e) {
                log.info("Cannot get key: " + key + " from response body");
            }
        }
    }

    @And("^Get authorization token from response and put it to header$")
    public void getTokenValueFromResponseAndAddToHeader() {
        String token = getPropertyFromResponseBody("access_token", String.class);
        requestBuilder.removeHeader("Authorization");
        requestBuilder.addHeader("Authorization", String.format("Bearer %s", token));

    }


    @Then("^The response status code should be (\\d+)$")
    public void theResponseStatusCodeShouldBe(int expectedStatus) {
        Assert.assertNotNull("empty response", response);
        Assert.assertEquals(StringUtils.hasText(responseBody) ? responseBody : "(empty response body)",
                expectedStatus, response.code());
    }

    @Then("^Error should occured with status (\\d+) and message contain: ([^\"]*)$")
    public void errorShouldHasStatusAndMessage(int status, String message){
        theResponseStatusCodeShouldBe(status);
        propertyShouldContain("message", message);

    }

    @And("^Property ([^\"]*) should be equal to: ([^\"]*)$")
    public void propertyShouldBeEqualTo(String property, String message) {
        String content = getPropertyFromResponseBody(property, String.class);
        Assert.assertEquals(content, message);
    }

    @And("^Property ([^\"]*) should be null")
    public void propertyShouldBeNull(String property) {
        String message = null;
        String content = getPropertyFromResponseBody(property, String.class);
        Assert.assertEquals(content, message);
    }

    @And("^Property ([^\"]*) should contain: ([^\"]*)$")
    public void propertyShouldContain(String property, String message){
        String content = getPropertyFromResponseBody(property, String.class);
        Assert.assertTrue(content.contains(message));
    }


    @SneakyThrows
    private <T> T getPropertyFromResponseBody(String property, Class<T> propertyType) {
        try {
            return JsonPath
                    .parse(responseBody)
                    .read("$." + property, propertyType);
        } catch (PathNotFoundException e) {
            JSONObject json = new JSONObject(responseBody);
            log.info("\nResponse body was: \n{}", json.toString(4));
            throw new PathNotFoundException(e);
        }
    }




}
