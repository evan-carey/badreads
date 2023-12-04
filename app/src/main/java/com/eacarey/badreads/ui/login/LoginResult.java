package com.eacarey.badreads.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult<T> {

  @Nullable
  private T success;
  @Nullable
  private Integer error;

  public LoginResult(@Nullable Integer error) {
    this.error = error;
  }

  public LoginResult(@Nullable T success) {
    this.success = success;
  }

  @Nullable
  public T getSuccess() {
    return success;
  }

  @Nullable
  public Integer getError() {
    return error;
  }
}