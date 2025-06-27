package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class DummyTest {

  @Test
  void callServiceAction() {
    // Pick up env vars set in the workflow
    Configuration.browser  = System.getenv().getOrDefault("SELENIDE_BROWSER", "chrome");
    Configuration.headless = Boolean.parseBoolean(System.getenv().getOrDefault("SELENIDE_HEADLESS", "true"));

    String targetUrl = System.getenv().getOrDefault(
        "TARGET_URL",
        "http://myservice:8081/action"
    );

    open(targetUrl);

    // Ultra-simple smoke assertion: page has a <body>
    $("body").shouldBe(Condition.exist);

    System.out.println("Test succeeded!");
  }
}

