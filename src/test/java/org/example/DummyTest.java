package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Purposely failing tests to illustrate pipeline red status.
 */
public class DummyTest {

    private static final String TARGET_URL = System.getenv().getOrDefault(
            "TARGET_URL",
            "http://myservice:8080/action"
    );

    @Test
    @DisplayName("Service deliberately expected to return 500")
    void serviceResponds500() throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TARGET_URL))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        // Intentionally wrong expectation: should fail when service returns 200
        assertEquals(500, response.statusCode(), "Deliberate failure: expecting 500 but got " + response.statusCode());
    }

    @Test
    @DisplayName("Page should contain a non‑existent element (will fail)")
    void pageLoadsInChrome() {
        Configuration.browser = System.getenv().getOrDefault("SELENIDE_BROWSER", "chrome");
        Configuration.headless = Boolean.parseBoolean(
                System.getenv().getOrDefault("SELENIDE_HEADLESS", "true")
        );

        open(TARGET_URL);
        // Expect an element that certainly doesn't exist -> fail
        $("#definitely-non-existent").shouldBe(Condition.visible);
    }
}

