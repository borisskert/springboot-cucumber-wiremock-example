package com.github.borisskert.features.steps;

import com.github.borisskert.features.models.User;
import com.github.borisskert.features.world.CucumberHttpClient;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersSteps {
    private static final String USERS_URL = "/api/v1/users";

    @Autowired
    private CucumberHttpClient<User> client;

    @When("I ask for all users")
    public void iAskForAllUsers() {
        client.requestGet(USERS_URL);
    }

    @Then("should return no users")
    public void shouldReturnNoUsers() {
        client.verifyLatestStatus(HttpStatus.OK);
        client.verifyLatestBody(new ArrayList<>(), User.LIST_TYPE);
    }

    @Then("I should get following users")
    public void iShouldGetFollowingUsers(List<User> dataTable) {
        client.verifyLatestStatus(HttpStatus.OK);
        client.verifyLatestBody(dataTable, User.LIST_TYPE);
    }

    @When("I ask for a user with id {string}")
    public void iAskForAUserWithId(String userId) {
        client.requestGet(USERS_URL + "/" + userId);
    }

    @Then("I should get the following user")
    public void iShouldGetTheFollowingUser(User dataTable) {
        client.verifyLatestStatus(HttpStatus.OK);
        client.verifyLatestBody(dataTable, User.class);
    }

    @DataTableType
    public User defineProduct(Map<String, String> entry) {
        String id = entry.get("ID");
        String username = entry.get("Username");
        String email = entry.get("Email");
        String name = entry.get("Name");

        return new User(id, username, email, name);
    }
}
