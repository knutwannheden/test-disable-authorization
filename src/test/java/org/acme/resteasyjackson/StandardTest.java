package org.acme.resteasyjackson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;

@QuarkusTest
@TestHTTPEndpoint(JacksonResource.class)
class StandardTest {

    @Test
    void noSecurity() {
        Assertions.assertEquals(401, RestAssured.get().thenReturn().statusCode());
    }

    @Test
    @TestSecurity
    void noUser() {
        Assertions.assertEquals(401, RestAssured.get().thenReturn().statusCode());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void authorizationDisabled() {
        Assertions.assertEquals(200, RestAssured.get().thenReturn().statusCode());
    }
}