package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.sessionId;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

public class InformationControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().get("/information")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(2));

    }


    @Test
    public void shouldCreateNewPlayer() {
        JSONObject requestParams = new JSONObject();


        String playerName = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("name", playerName);
        String myNationality = "Poland";
        int salary  = 1000;
        requestParams.put("nationality", myNationality);
        requestParams.put("salary", salary);
        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", greaterThan(0))
                .body("name", equalTo(playerName))
                .body("salary", is(salary))
                .body("nationality", equalTo(myNationality));

        //given().delete("/information/" + id)
          //      .then()
            //    .log().all()
              //  .statusCode(204);

    }


    @Test
    public void deletePlayer() {

        given().delete("/information/14")
                .then()
                .log().all()
                .statusCode(204);
}

}
