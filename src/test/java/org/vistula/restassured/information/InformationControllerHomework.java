package org.vistula.restassured.information;

import io.restassured.RestAssured;
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
    public void NewPlayer() {

        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(10);;
        String natPol = "Polish";
        int salrPol = 500;


        requestParams.put("nationality", natPol);
        requestParams.put("name", myName);
        requestParams.put("salary", salrPol);



        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("nationality", equalTo(natPol))
                .body("salary", equalTo(salrPol))
                .body("name", equalTo(myName))
                .body("id", is(3))
                .extract().path("id");


        JSONObject requestParams1 = new JSONObject();
        String natSwe = "Swedish";
        requestParams1.put("nationality", natSwe);
        requestParams1.put("name", myName);
        requestParams1.put("salary", 500);
        requestParams1.put("id", 3);

        given().header("Content-Type", "application/json")
                .body(requestParams1.toString())
                .put("/information/3")
                .then()
                .log().all()
                .statusCode(200)
                .body("nationality", equalTo("Swedish"))
                .body("salary", equalTo(500))
                .body("name", equalTo("Robert"))
                .body("id", equalTo(3));

        assertThat("Swedish").isEqualTo(natSwe);
        assertThat(500).isEqualTo(500);

        JSONObject requestParams2 = new JSONObject();
        requestParams2.put("salary", 1000);

        given().header("Content-Type", "application/json")
                .body(requestParams2.toString())
                .patch("/information/3")
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
                .patch("/information/3")
                .then()
                .log().all()
                .statusCode(200)
                .body("salary", is(2000))
                .body("nationality", equalTo("German"));

        assertThat(2000).isEqualTo(2000);
        assertThat("German").isEqualTo("German");

        given().delete("/information/3")
                .then()
              .log().all()
            .statusCode(204);

    }

    //@Test //GET
    //public void getId() {

      //  JsonPath jsonPath;
        //jsonPath = new JsonPath("id");
        //int user_id = jsonPath.getInt("id");
       // given().header("Content-Type", "application/json")

         //       .get("/information/" + user_id)
           //     .then()
             //   .log().all()
               // .statusCode(200);


       // assertThat(user_id).isEqualTo("id");
    //}

        @Test //PUT
        public void putNewPlayer () {
            JSONObject requestParams = new JSONObject();



            requestParams.put("nationality", "Swedish");
            requestParams.put("name", "Robert");
            requestParams.put("salary", 500);


            given().header("Content-Type", "application/json")
                    .body(requestParams.toString())
                    .put("/information/3")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("nationality", equalTo("German"))
                    .body("salary", equalTo(500))
                    .body("name", equalTo("Robert"));

            assertThat("nationality").isEqualTo("Swedish");
            assertThat("name").isEqualTo("Robert");
            assertThat("salary").isEqualTo(500);
        }

        @Test // PATCH
        public void patchSalary () {
            JSONObject requestParams = new JSONObject();
            int salary = 1000;
            requestParams.put("salary", 1000);

            given().header("Content-Type", "application/json")
                    .body(requestParams.toString())
                    .patch("/information/3")
                    .then()
                    .log().all()
                    .statusCode(200)
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
                    .patch("/information/3")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("salary", is(2000))
                    .body("nationality", equalTo("German"));

            assertThat(2000).isEqualTo(2000);
            assertThat("German").isEqualTo("German");
        }

        @Test // DELETE
        public void deleteId () {

            given().delete("/information/3")
                    .then()
                    .log().all()
                    .statusCode(204);

        }


    }
