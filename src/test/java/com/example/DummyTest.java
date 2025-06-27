package com.example;

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
 * Smoke‑tests that the target service is alive and that its /action endpoint
 * renders in a browser. Both an HTTP‑level check (200 OK) and a Selenide UI
 * check are performed.
 */
public class DummyTest {

    /**
     * URL of the service under test – can be overridden via the TARGET_URL env
     * var set in the GitHub Actions workflow.
     */
    private static final String TARGET_URL = System.getenv().getOrDefault(
            "TARGET_URL2",
            "http://myservice2:8080/action"
    );

    @Test
    @DisplayName("Service responds with HTTP 200")
    void serviceResponds200() throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TARGET_URL))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200, response.statusCode(), "Expected HTTP 200 from service");
    }

    @Test
    @DisplayName("Page loads successfully in Chrome")
    void pageLoadsInChrome() {
        // Pick up browser/headless flags from the workflow environment
        Configuration.browser = System.getenv().getOrDefault("SELENIDE_BROWSER", "chrome");
        Configuration.headless = Boolean.parseBoolean(
                System.getenv().getOrDefault("SELENIDE_HEADLESS", "true")
        );

        open(TARGET_URL);
        $("body").shouldBe(Condition.visible);
    }
}

