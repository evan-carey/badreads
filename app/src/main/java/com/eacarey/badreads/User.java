package com.eacarey.badreads;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.eacarey.badreads.DB.AppDatabase;
import java.util.Objects;

@Entity(tableName = AppDatabase.USERS_TABLE)
public class User implements Parcelable {

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

  protected User(Parcel in) {
    mUserId = in.readInt();
    mUsername = in.readString();
    mPassword = in.readString();
    isAdmin = in.readByte() != 0;
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    @Override
    public User createFromParcel(Parcel in) {
      return new User(in);
    }

    @Override
    public User[] newArray(int size) {
      return new User[size];
    }
  };

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
    return this.mUsername;
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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(@NonNull Parcel dest, int flags) {
    dest.writeInt(mUserId);
    dest.writeString(mUsername);
    dest.writeString(mPassword);
    dest.writeByte((byte) (isAdmin ? 1 : 0));
  }
}
