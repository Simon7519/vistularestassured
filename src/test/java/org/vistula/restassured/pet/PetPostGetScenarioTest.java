package org.vistula.restassured.pet;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;

public class PetPostGetScenarioTest extends RestAssuredTest{

    @Test
    public void shouldGetFirstPet1() {
        getPet(1, "Cow", "/pet/1");
    }

    @Test
    public void shouldCreateNewPet10() {
        JSONObject requestParams = new JSONObject();
        int value = ThreadLocalRandom.current().nextInt(20, Integer.MAX_VALUE);
        requestParams.put("id", value);
        String randomName = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("name", randomName);


        createNewPet(requestParams);
        getPet(value, randomName, "/pet/" + value);
        deletePet(value);
    }

    private void deletePet(int value) {
        given().delete("/pet/" + value)
                .then()
                .log().all()
                .statusCode(204);
    }

    private void createNewPet(JSONObject requestParams) {
        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);
    }

    private void getPet(int value, String randomName, String s) {
        given().get(s)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(value))
                .body("name", equalTo(randomName));
    }


}





