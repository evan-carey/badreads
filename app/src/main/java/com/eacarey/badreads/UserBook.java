package com.eacarey.badreads;

import androidx.room.Entity;
import com.eacarey.badreads.DB.AppDatabase;

@Entity(tableName = AppDatabase.USER_BOOKS_TABLE, primaryKeys = {"userId", "bookId"})
public class UserBook {
  public int userId;
  public int bookId;
  private UserBookReadState state = UserBookReadState.WANT_TO_READ;

  public UserBook(int userId, int bookId, UserBookReadState state) {
    this.userId = userId;
    this.bookId = bookId;
    this.state = state;
  }

  public enum UserBookReadState {
    WANT_TO_READ,
    READ
  }

  public UserBookReadState getState() {
    return state;
  }

  public void setState(UserBookReadState state) {
    this.state = state;
  }
}
