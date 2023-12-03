package com.eacarey.badreads.Repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import androidx.lifecycle.LiveData;
import com.eacarey.badreads.DB.AppDatabase;
import com.eacarey.badreads.DB.UserDAO;
import com.eacarey.badreads.R;
import com.eacarey.badreads.User;
import com.eacarey.badreads.data.Result;
import java.util.List;

/**
 * Singleton
 */
public class UserRepository {

  private static volatile UserRepository instance;

  private UserDAO mUserDAO;

  // If user credentials will be cached in local storage, it is recommended it be encrypted
  // @see https://developer.android.com/training/articles/keystore
  // logged in user
  private User user = null;

  private SharedPreferences mSharedPref;
  private String mSharedPrefUsernameKey;
  private String mSharedPrefPasswordKey;

  private UserRepository(Application app) {
    AppDatabase db = AppDatabase.getInstance(app);
    this.mUserDAO = db.UserDAO();
    this.mSharedPrefUsernameKey = app.getString(R.string.shared_preferences_username_key);
    this.mSharedPrefPasswordKey = app.getString(R.string.shared_preferences_password_key);
    this.mSharedPref = app.getSharedPreferences(this.mSharedPrefUsernameKey,
        Context.MODE_PRIVATE);

    String username = this.mSharedPref.getString(this.mSharedPrefUsernameKey, null);
    String password = this.mSharedPref.getString(this.mSharedPrefPasswordKey, null);
    this.login(username, password);
  }

  public static UserRepository getInstance(Application app) {
    if (instance == null) {
      instance = new UserRepository(app);
    }
    return instance;
  }

  public boolean isLoggedIn() {
    return user != null;
  }

  public void logout() {
    user = null;
    this.mSharedPref.edit().clear().apply();
  }

  public User getUser() {
    return this.user;
  }

  private void setLoggedInUser(User user) {
    this.user = user;
    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    Editor editor = this.mSharedPref.edit();
    editor.putString(this.mSharedPrefUsernameKey, user.getUsername());
    editor.putString(this.mSharedPrefPasswordKey, user.getPassword());
    editor.apply();
  }

  public Result<User> login(String username, String password) {
    // handle login
    User result = getUserByUsernamePassword(username, password);
    if (result == null) {
      logout();
      return new Result.Error(new Exception("Error logging in"));
    }
    User foundUser = result;
    setLoggedInUser(foundUser);
    return new Result.Success<User>(foundUser);

//    Result<User> result = dataSource.login(username, password);
//    if (result instanceof Result.Success) {
//      setLoggedInUser(((Result.Success<User>) result).getData());
//    }
//    return result;
  }

  public LiveData<List<User>> getAllUsers() {
    return this.mUserDAO.getAllUsers();
  }

  public LiveData<User> getUserById(int userId) {
    return this.mUserDAO.getUserById(userId);
  }

  public LiveData<User> getUserByUsername(String username) {
    return this.mUserDAO.getUserByUsername(username);
  }

  public User getUserByUsernamePassword(String username, String password) {
    return this.mUserDAO.getUserByUsernamePassword(username, password);
  }
}
