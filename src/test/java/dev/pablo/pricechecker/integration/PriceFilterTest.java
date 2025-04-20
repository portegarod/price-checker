package dev.pablo.pricechecker.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class PriceFilterTest {

  @LocalServerPort
  private Integer port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  void testOk200() {
    given().queryParam("date", "2020-06-14T10:00:00").queryParam("product_id", "35455")
        .queryParam("brand_id", "1").when().get("/api/v1/price-checker/prices").then().log().body()
        .assertThat().body("size()", equalTo(1)).body("[0].product_id", equalTo("35455"))
        .body("[0].brand_id", equalTo("1")).body("[0].price_list", equalTo("1"))
        .body("[0].start_date", equalTo("2020-06-14T00:00:00"))
        .body("[0].end_date", equalTo("2020-12-31T23:59:59")).body("[0].price", equalTo(35.50F))
        .statusCode(200);

    given().queryParam("date", "2020-06-14T16:00:00").queryParam("product_id", "35455")
        .queryParam("brand_id", "1").when().get("/api/v1/price-checker/prices").then().log().body()
        .assertThat().body("size()", equalTo(1)).body("[0].product_id", equalTo("35455"))
        .body("[0].brand_id", equalTo("1")).body("[0].price_list", equalTo("2"))
        .body("[0].start_date", equalTo("2020-06-14T15:00:00"))
        .body("[0].end_date", equalTo("2020-06-14T18:30:00")).body("[0].price", equalTo(25.45F))
        .statusCode(200);

    given().queryParam("date", "2020-06-14T21:00:00").queryParam("product_id", "35455")
        .queryParam("brand_id", "1").when().get("/api/v1/price-checker/prices").then().log().body()
        .assertThat().body("size()", equalTo(1)).body("[0].product_id", equalTo("35455"))
        .body("[0].brand_id", equalTo("1")).body("[0].price_list", equalTo("1"))
        .body("[0].start_date", equalTo("2020-06-14T00:00:00"))
        .body("[0].end_date", equalTo("2020-12-31T23:59:59")).body("[0].price", equalTo(35.50F))
        .statusCode(200);

    given().queryParam("date", "2020-06-15T10:00:00").queryParam("product_id", "35455")
        .queryParam("brand_id", "1").when().get("/api/v1/price-checker/prices").then().log().body()
        .assertThat().body("size()", equalTo(1)).body("[0].product_id", equalTo("35455"))
        .body("[0].brand_id", equalTo("1")).body("[0].price_list", equalTo("3"))
        .body("[0].start_date", equalTo("2020-06-15T00:00:00"))
        .body("[0].end_date", equalTo("2020-06-15T11:00:00")).body("[0].price", equalTo(30.50F))
        .statusCode(200);

    given().queryParam("date", "2020-06-16T21:00:00").queryParam("product_id", "35455")
        .queryParam("brand_id", "1").when().get("/api/v1/price-checker/prices").then().log().body()
        .assertThat().body("size()", equalTo(1)).body("[0].product_id", equalTo("35455"))
        .body("[0].brand_id", equalTo("1")).body("[0].price_list", equalTo("4"))
        .body("[0].start_date", equalTo("2020-06-15T16:00:00"))
        .body("[0].end_date", equalTo("2020-12-31T23:59:59")).body("[0].price", equalTo(38.95F))
        .statusCode(200);
  }

  @Test
  void testNoContent204() {
    given().queryParam("date", "2020-06-16T21:00:00").queryParam("product_id", "0")
        .queryParam("brand_id", "1").when().get("/api/v1/price-checker/prices").then()
        .statusCode(204);
  }

  @Test
  void testBadRequest400() {
    given().queryParam("date", "123").queryParam("product_id", "35455").queryParam("brand_id", "1")
        .when().get("/api/v1/price-checker/prices").then().statusCode(400);
  }
}
