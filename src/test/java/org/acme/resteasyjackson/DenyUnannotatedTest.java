package org.acme.resteasyjackson;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;

@QuarkusTest
@TestProfile(DenyUnannotatedTest.DenyUnannotatedProfile.class)
@TestHTTPEndpoint(JacksonResource.class)
class DenyUnannotatedTest {

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

    public static class DenyUnannotatedProfile implements QuarkusTestProfile {
        @Override
        public Map<String, String> getConfigOverrides() {
            return Map.of("quarkus.security.jaxrs.deny-unannotated-endpoints", "true");
        }
    }
}