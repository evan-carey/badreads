package com.eacarey.badreads.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.eacarey.badreads.Models.UserViewModel;
import com.eacarey.badreads.R;
import com.eacarey.badreads.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

  public static final String EXTRA_REPLY = "com.eacarey.badreads.LOGIN_REPLY";

  //  private LoginViewModel loginViewModel;
  private ActivityLoginBinding binding;

  private UserViewModel mUserViewModel;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

//    loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
//        .get(LoginViewModel.class);

    final EditText usernameEditText = binding.username;
    final EditText passwordEditText = binding.password;
    final Button loginButton = binding.login;
    final ProgressBar loadingProgressBar = binding.loading;

    mUserViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
      @Override
      public void onChanged(@Nullable LoginFormState loginFormState) {
        if (loginFormState == null) {
          return;
        }
        loginButton.setEnabled(loginFormState.isDataValid());
        if (loginFormState.getUsernameError() != null) {
          usernameEditText.setError(getString(loginFormState.getUsernameError()));
        }
        if (loginFormState.getPasswordError() != null) {
          passwordEditText.setError(getString(loginFormState.getPasswordError()));
        }
      }
    });

    mUserViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
      @Override
      public void onChanged(@Nullable LoginResult loginResult) {
        if (loginResult == null) {
          return;
        }
        loadingProgressBar.setVisibility(View.GONE);
        if (loginResult.getError() != null) {
          showLoginFailed(loginResult.getError());
        }
        if (loginResult.getSuccess() != null) {
          updateUiWithUser(loginResult.getSuccess());

          Intent replyIntent = new Intent();
          replyIntent.putExtra(EXTRA_REPLY, loginResult.getSuccess());
          setResult(Activity.RESULT_OK, replyIntent);

          //Complete and destroy login activity once successful
          finish();
        }
      }
    });

    TextWatcher afterTextChangedListener = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // ignore
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // ignore
      }

      @Override
      public void afterTextChanged(Editable s) {
        mUserViewModel.loginDataChanged(usernameEditText.getText().toString(),
            passwordEditText.getText().toString());
      }
    };
    usernameEditText.addTextChangedListener(afterTextChangedListener);
    passwordEditText.addTextChangedListener(afterTextChangedListener);
    passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          mUserViewModel.login(usernameEditText.getText().toString(),
              passwordEditText.getText().toString());
        }
        return false;
      }
    });

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loadingProgressBar.setVisibility(View.VISIBLE);
        mUserViewModel.login(usernameEditText.getText().toString(),
            passwordEditText.getText().toString());
      }
    });
  }

  private void updateUiWithUser(LoggedInUserView model) {
    String welcome = getString(R.string.welcome) + model.getDisplayName();
    // TODO : initiate successful logged in experience
    Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
  }

  private void showLoginFailed(@StringRes Integer errorString) {
    Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
  }

  public static Intent getIntent(Context context) {
    return new Intent(context, LoginActivity.class);
  }
}