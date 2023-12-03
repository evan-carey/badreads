package com.eacarey.badreads;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.eacarey.badreads.DB.AppDatabase;
import java.util.Objects;

@Entity(tableName = AppDatabase.USERS_TABLE)
public class User {

  @PrimaryKey(autoGenerate = true)
  private int mUserId;

  @NonNull
  private String mUsername;
  @NonNull
  private String mPassword;

  private boolean isAdmin;

  public User(@NonNull String username, @NonNull String password, boolean isAdmin) {
    this.mUsername = username;
    this.mPassword = password;
    this.isAdmin = isAdmin;
  }

  public String getUsername() {
    return this.mUsername;
  }

  public int getUserId() {
    return mUserId;
  }

  public String getPassword() {
    return this.mPassword;
  }

  public boolean getIsAdmin() {
    return isAdmin;
  }

  public void setUserId(int userId) {
    mUserId = userId;
  }

  public void setUsername(@NonNull String username) {
    mUsername = username;
  }

  public void setPassword(@NonNull String password) {
    mPassword = password;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  @Override
  public String toString() {
    return "User{" +
        "mUserId=" + mUserId +
        ", mUsername='" + mUsername + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return getUserId() == user.mUserId && getUsername().equals(user.getUsername());
  }

  @Override
  public int hashCode() {
    return Objects.hash(mUserId, getUsername());
  }
}
