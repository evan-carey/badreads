package com.eacarey.badreads.data;

import com.eacarey.badreads.User;
import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

  public Result<User> login(String username, String password) {

    try {
      // TODO: handle loggedInUser authentication
      User fakeUser =
          new User(
              "Jane Doe",
              java.util.UUID.randomUUID().toString(),
              false
          );
      return new Result.Success<>(fakeUser);
    } catch (Exception e) {
      return new Result.Error(new IOException("Error logging in", e));
    }
  }

  public void logout() {
    // TODO: revoke authentication
  }
}