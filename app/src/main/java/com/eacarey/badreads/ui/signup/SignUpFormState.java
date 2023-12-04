package com.eacarey.badreads.ui.signup;

import androidx.annotation.Nullable;

public class SignUpFormState {

  @Nullable
  private Integer usernameError;
  @Nullable
  private Integer passwordError;
  @Nullable
  private Integer confirmPasswordError;
  private boolean isDataValid;

  public SignUpFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer confirmPasswordError) {
    this.usernameError = usernameError;
    this.passwordError = passwordError;
    this.confirmPasswordError = confirmPasswordError;
    this.isDataValid = false;
  }

  public SignUpFormState(boolean isDataValid) {
    this.usernameError = null;
    this.passwordError = null;
    this.confirmPasswordError = null;
    this.isDataValid = isDataValid;
  }

  @Nullable
  Integer getUsernameError() {
    return usernameError;
  }

  @Nullable
  Integer getPasswordError() {
    return passwordError;
  }

  @Nullable
  Integer getConfirmPasswordError() { return confirmPasswordError; }

  boolean isDataValid() {
    return isDataValid;
  }
}
