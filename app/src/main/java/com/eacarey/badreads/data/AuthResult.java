package com.eacarey.badreads.data;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
public class AuthResult<T> {

  @Nullable
  private T success;
  @Nullable
  private Integer error;

  public AuthResult(@Nullable Integer error) {
    this.error = error;
  }

  public AuthResult(@Nullable T success) {
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