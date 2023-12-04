package com.eacarey.badreads.Models;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.eacarey.badreads.R;
import com.eacarey.badreads.Repositories.UserRepository;
import com.eacarey.badreads.User;
import com.eacarey.badreads.data.Result;
import com.eacarey.badreads.data.Result.Success;
import com.eacarey.badreads.data.AuthResult;
import com.eacarey.badreads.ui.signup.SignUpFormState;

public class SignUpViewModel extends AndroidViewModel {

  private MutableLiveData<SignUpFormState> mSignUpFormState = new MutableLiveData<>();
    private MutableLiveData<AuthResult<User>> signUpResult = new MutableLiveData<>();
  private UserRepository mUserRepository;

  public SignUpViewModel(@NonNull Application application) {
    super(application);
    this.mUserRepository = UserRepository.getInstance(application);
  }

  public LiveData<SignUpFormState> getSignUpFormState() {
    return mSignUpFormState;
  }

  public LiveData<AuthResult<User>> getSignUpResult() { return signUpResult; }

  public void signUp(String username, String password) {
    Result<User> result = mUserRepository.createUserAccount(username, password);

    if (result instanceof Result.Success) {
      User data = ((Success<User>) result).getData();
      signUpResult.setValue(new AuthResult<>(data));
    } else {
      signUpResult.setValue(new AuthResult<>(R.string.login_failed));
    }
  }

  public void signUpDataChanged(String username, String password, String confirmPassword) {
    if (!isUsernameValid(username)) {
      mSignUpFormState.setValue(new SignUpFormState(R.string.username_already_exists, null, null));
    } else if (!isPasswordValid(password)) {
      mSignUpFormState.setValue(new SignUpFormState(null, R.string.invalid_password, null));
    } else if (!passwordsMatch(password, confirmPassword)) {
      mSignUpFormState.setValue(new SignUpFormState(null, null, R.string.passwords_dont_match));
    } else {
      mSignUpFormState.setValue(new SignUpFormState(true));
    }

  }

  private boolean isUsernameValid(String username) {
    User existingUser = this.mUserRepository.getUserByUsername(username);
    return existingUser == null;
  }

  private boolean isPasswordValid(String password) {
    return password != null && password.trim().length() > 5;
  }

  private boolean passwordsMatch(String password, String confirmPassword) {
    return password.equals(confirmPassword);
  }
}
