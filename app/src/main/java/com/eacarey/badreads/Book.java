package com.eacarey.badreads;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.eacarey.badreads.DB.AppDatabase;
import java.util.Objects;

@Entity(tableName = AppDatabase.BOOKS_TABLE)
public class Book {
  @PrimaryKey(autoGenerate = true)
  private int mBookId;

  @NonNull
  private String mTitle;
  @NonNull
  private String mAuthor;
  private int mPages;

  public Book(@NonNull String title, @NonNull String author, int pages) {
    this.mTitle = title;
    this.mAuthor = author;
    this.mPages = pages;
  }

  @Override
  public String toString() {
    return "Book{" +
        "mBookId='" + mBookId + '\'' +
        "mTitle='" + mTitle + '\'' +
        ", mAuthor='" + mAuthor + '\'' +
        ", mPages=" + mPages +
        '}';
  }

  public int getBookId() {
    return mBookId;
  }

  public void setBookId(int bookId) {
    mBookId = bookId;
  }

  public String getTitle() {
    return mTitle;
  }


  public void setTitle(String title) {
    mTitle = title;
  }

  public String getAuthor() {
    return mAuthor;
  }

  public void setAuthor(String author) {
    mAuthor = author;
  }

  public int getPages() {
    return mPages;
  }

  public void setPages(int pages) {
    mPages = pages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return getPages() == book.getPages() && Objects.equals(getTitle(), book.getTitle())
        && Objects.equals(getAuthor(), book.getAuthor());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTitle(), getAuthor(), getPages());
  }


}
