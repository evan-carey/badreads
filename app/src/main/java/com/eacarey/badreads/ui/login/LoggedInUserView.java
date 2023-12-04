package com.eacarey.badreads.ui.login;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView implements Parcelable {

  private String displayName;
  private boolean isAdmin;

  public LoggedInUserView(String displayName, boolean isAdmin) {
    this.displayName = displayName;
    this.isAdmin = isAdmin;
  }

  protected LoggedInUserView(Parcel in) {
    displayName = in.readString();
    isAdmin = in.readByte() != 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(displayName);
    dest.writeByte((byte) (isAdmin ? 1 : 0));
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<LoggedInUserView> CREATOR = new Creator<LoggedInUserView>() {
    @Override
    public LoggedInUserView createFromParcel(Parcel in) {
      return new LoggedInUserView(in);
    }

    @Override
    public LoggedInUserView[] newArray(int size) {
      return new LoggedInUserView[size];
    }
  };

  public String getDisplayName() {
    return displayName;
  }
  public boolean getIsAdmin() { return isAdmin; }
}