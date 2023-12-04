package com.eacarey.badreads.ui.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.eacarey.badreads.Models.SignUpViewModel;
import com.eacarey.badreads.R;
import com.eacarey.badreads.User;
import com.eacarey.badreads.databinding.ActivitySignUpBinding;
import com.eacarey.badreads.data.AuthResult;

public class SignUpActivity extends AppCompatActivity {
  public static final String SIGN_UP_EXTRA_REPLY = "com.eacarey.badreads.SIGN_UP_REPLY";

  ActivitySignUpBinding mBinding;

  private SignUpViewModel mSignUpViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    mBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
    setContentView(mBinding.getRoot());

    mSignUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

    final EditText usernameEditText = mBinding.username;
    final EditText passwordEditText = mBinding.password;
    final EditText confirmPasswordEditText = mBinding.confirmPassword;
    final Button createAccountButton = mBinding.createAccountButton;

    mSignUpViewModel.getSignUpFormState().observe(this, new Observer<SignUpFormState>() {
      @Override
      public void onChanged(SignUpFormState signUpFormState) {
        if (signUpFormState == null) {
          return;
        }
        createAccountButton.setEnabled(signUpFormState.isDataValid());
        if (signUpFormState.getUsernameError() != null) {
          usernameEditText.setError(getString(signUpFormState.getUsernameError()));
        }
        if (signUpFormState.getPasswordError() != null) {
          passwordEditText.setError(getString(signUpFormState.getPasswordError()));
        }
        if (signUpFormState.getConfirmPasswordError() != null) {
          confirmPasswordEditText.setError(getString(signUpFormState.getConfirmPasswordError()));
        }
      }
    });

    mSignUpViewModel.getSignUpResult().observe(this, new Observer<AuthResult<User>>() {
      @Override
      public void onChanged(AuthResult<User> userAuthResult) {
        if (userAuthResult == null) {
          return;
        }

        if (userAuthResult.getError() != null) {
          showLoginFailed(userAuthResult.getError());
        }
        if (userAuthResult.getSuccess() != null) {

          Intent replyIntent = new Intent();
          replyIntent.putExtra(SIGN_UP_EXTRA_REPLY, userAuthResult.getSuccess());
          setResult(Activity.RESULT_OK, replyIntent);
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
        mSignUpViewModel.signUpDataChanged(usernameEditText.getText().toString(),
            passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
      }
    };
    usernameEditText.addTextChangedListener(afterTextChangedListener);
    passwordEditText.addTextChangedListener(afterTextChangedListener);
    confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);
    confirmPasswordEditText.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE) {
        mSignUpViewModel.signUp(usernameEditText.getText().toString(),
            passwordEditText.getText().toString());
      }
      return false;
    });

    createAccountButton.setOnClickListener(v -> {
      String username = usernameEditText.getText().toString();
      String password = passwordEditText.getText().toString();
      mSignUpViewModel.signUp(username, password);
    });
  }

  private void showLoginFailed(@StringRes Integer errorString) {
    Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
  }

  public static Intent getIntent(Context context) {
    return new Intent(context, SignUpActivity.class);
  }
}