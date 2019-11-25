package org.vistula.restassured.information;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.Request;
import org.vistula.restassured.RestAssuredTest;
import org.vistula.restassured.pet.Pet;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class InformationControllerHomework extends RestAssuredTest {


    @Test
    public void newPlayer() {

        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(10);
        String natPol = "Polish";
        int salrPol = 500;

        requestParams.put("nationality", natPol);
        requestParams.put("name", myName);
        requestParams.put("salary", salrPol);

        long id = given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("nationality", equalTo(natPol))
                .body("salary", equalTo(salrPol))
                .body("name", equalTo(myName))
                .extract().jsonPath().getLong("id");

        JSONObject requestParams1 = new JSONObject();
        String natSwe = "Swedish";
        requestParams1.put("nationality", natSwe);
        requestParams1.put("name", myName);
        requestParams1.put("salary", 500);
        requestParams1.put("id", id);

        given().header("Content-Type", "application/json")
                .body(requestParams1.toString())
                .put("/information/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("nationality", equalTo("Swedish"))
                .body("salary", equalTo(500))
                .body("name", equalTo(myName));

        assertThat("Swedish").isEqualTo(natSwe);
        assertThat(500).isEqualTo(500);

        JSONObject requestParams2 = new JSONObject();
        requestParams2.put("salary", 1000);

        given().header("Content-Type", "application/json")
                .body(requestParams2.toString())
                .patch("/information/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("salary", is(1000));

        assertThat(1000).isEqualTo(1000);

        JSONObject requestParams3 = new JSONObject();
        requestParams3.put("salary", 2000);
        requestParams3.put("nationality", "German");

        given().header("Content-Type", "application/json")
                .body(requestParams3.toString())
                .patch("/information/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("salary", is(2000))
                .body("nationality", equalTo("German"));

        assertThat(2000).isEqualTo(2000);
        assertThat("German").isEqualTo("German");

        given().delete("/information/" + id)
                .then()
                .log().all()
                .statusCode(204);
    }
}
