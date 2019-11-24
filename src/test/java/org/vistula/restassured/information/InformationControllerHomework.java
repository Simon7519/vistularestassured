package org.vistula.restassured.information;

import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;
import org.vistula.restassured.pet.Pet;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.omg.CORBA.ServiceInformationHelper.extract;

public class InformationControllerHomework extends RestAssuredTest {

    @Test //POST
    public void postNewPlayer() {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(10);;

        requestParams.put("nationality", "Polish");
        requestParams.put("name", myName);
        requestParams.put("salary", 500);


        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("nationality", equalTo("Polish"))
                .body("salary", equalTo(500))
                .body("name", equalTo(myName))
                .extract().path("id");





    }
   // @Test //GET
//public void getId () {

  //      JsonPath jsonPath = new JsonPath(responseBody);
    //    int user_id = jsonPath.getInt("id");

      //  assertThat(myName)
    //}



    @Test //PUT
    public void putNewPlayer() {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(10);;

        requestParams.put("nationality", "Swedish");
        requestParams.put("name", myName);
        requestParams.put("salary", 500);


        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information/" + "id")
                .then()
                .log().all()
                .statusCode(201)
                .body("nationality", equalTo("German"))
                .body("salary", equalTo(500))
                .body("name", equalTo(myName));

        assertThat("nationality").isEqualTo("Swedish");
        assertThat("name").isEqualTo(myName);
        assertThat("salary").isEqualTo(500);
    }

    @Test // PATCH
    public void patchSalary () {
        JSONObject requestParams = new JSONObject();
        int salary = 1000;
        requestParams.put("salary", salary);

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .patch("/information/" + "id")
                .then()
                .log().all()
                .statusCode(201)
                .body("salary", is(salary));

        assertThat(salary).isEqualTo(1000);

    }
@Test // PATCH

    public void patchNationalityAndSalary () {
    JSONObject requestParams = new JSONObject();

    requestParams.put("salary", 2000);
    requestParams.put("nationality", "German");

    given().header("Content-Type", "application/json")
            .body(requestParams.toString())
            .patch("/information/" + "id")
            .then()
            .log().all()
            .statusCode(201)
            .body("salary", is(2000))
            .body("nationality", equalTo("German"));

assertThat("salary").isEqualTo(2000);
assertThat("nationality").isEqualTo("German");
}

@Test // DELETE
    public void deleteId () {

    given().delete("/information/" + "id")
            .then()
            .log().all()
            .statusCode(204);

}



}
