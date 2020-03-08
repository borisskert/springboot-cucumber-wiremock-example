package com.github.borisskert.features.steps;

import com.github.borisskert.features.world.Authentication;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationSteps {

    @Autowired
    private Authentication authentication;

    @Given("the client has been logged in")
    public void theClientHasBeenLoggedIn() {
        authentication.simulateLogin();
    }
}
