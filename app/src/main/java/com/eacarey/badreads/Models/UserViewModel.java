package com.eacarey.badreads.Models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.eacarey.badreads.R;
import com.eacarey.badreads.Repositories.UserRepository;
import com.eacarey.badreads.User;
import com.eacarey.badreads.data.Result;
import com.eacarey.badreads.ui.login.LoginFormState;
import com.eacarey.badreads.data.AuthResult;
import java.util.Objects;

public class UserViewModel extends AndroidViewModel {

  private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
  private MutableLiveData<AuthResult<User>> loginResult = new MutableLiveData<>();
  private UserRepository mUserRepository;

  public UserViewModel(Application app) {
    super(app);
    this.mUserRepository = UserRepository.getInstance(app);
  }

  public LiveData<LoginFormState> getLoginFormState() {
    return loginFormState;
  }

  public LiveData<AuthResult<User>> getLoginResult() {
    return loginResult;
  }

  public void login(String username, String password) {
    // can be launched in a separate asynchronous job
    Result<User> result = mUserRepository.login(username, password);

    if (result instanceof Result.Success) {
      User data = ((Result.Success<User>) result).getData();
      loginResult.setValue(
          new AuthResult<>(new User(data.getUsername(), data.getPassword(), data.getIsAdmin())));
    } else {
      loginResult.setValue(new AuthResult<>(R.string.login_failed));
    }
  }

  public void logout() {
    this.mUserRepository.logout();
  }

  public void loginDataChanged(String username, String password) {
    if (!isUserNameValid(username)) {
      loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
    } else if (!isPasswordValid(password)) {
      loginFormState.setValue(new LoginFormState(null, null));
    } else {
      loginFormState.setValue(new LoginFormState(true));
    }
  }

  // A placeholder username validation check
  private boolean isUserNameValid(String username) {
    if (username == null) {
      return false;
    }
    return !username.trim().isEmpty();
  }

  // A placeholder password validation check
  private boolean isPasswordValid(String password) {
    return password != null && password.trim().length() > 5;
  }

  public LiveData<User> getUser() {
    return this.mUserRepository.getUser();
  }

  public LiveData<Boolean> isLoggedIn() {
    return Transformations.map(this.getUser(), Objects::nonNull);
  }
}
