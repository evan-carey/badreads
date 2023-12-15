package com.eacarey.badreads;

import androidx.room.Entity;
import com.eacarey.badreads.DB.AppDatabase;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserBook userBook = (UserBook) o;
    return userId == userBook.userId && bookId == userBook.bookId && state == userBook.state;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, bookId, state);
  }

  @Override
  public String toString() {
    return "UserBook{" +
        "userId=" + userId +
        ", bookId=" + bookId +
        ", state=" + state +
        '}';
  }
}
