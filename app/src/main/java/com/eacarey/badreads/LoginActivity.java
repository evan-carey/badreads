package com.eacarey.badreads;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.eacarey.badreads.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
  ActivityLoginBinding mLoginBinding;

  private EditText mUsernameInput;
  private EditText mPasswordInput;

  private Button mLoginButton;
  private Button mSignUpButton;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState,
      @Nullable PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);

    setContentView(R.layout.activity_login);
    this.mLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(this.mLoginBinding.getRoot());


  }

  private void bindView() {
    this.mUsernameInput =this.mLoginBinding.username;
    this.mPasswordInput = this.mLoginBinding.password;
    this.mLoginButton = this.mLoginBinding.login;

  }
}
