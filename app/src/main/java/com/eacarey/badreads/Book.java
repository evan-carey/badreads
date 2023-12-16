package com.eacarey.badreads;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.eacarey.badreads.DB.AppDatabase;
import java.util.Objects;

@Entity(tableName = AppDatabase.BOOKS_TABLE)
public class Book implements Parcelable {

  @PrimaryKey(autoGenerate = true)
  private int mBookId;

  @NonNull
  private String mTitle;
  @NonNull
  private String mAuthor;

  public Book(@NonNull String title, @NonNull String author) {
    this.mTitle = title;
    this.mAuthor = author;
  }

  protected Book(Parcel in) {
    mBookId = in.readInt();
    mTitle = in.readString();
    mAuthor = in.readString();
  }

  public static final Creator<Book> CREATOR = new Creator<Book>() {
    @Override
    public Book createFromParcel(Parcel in) {
      return new Book(in);
    }

    @Override
    public Book[] newArray(int size) {
      return new Book[size];
    }
  };

  @NonNull
  @Override
  public String toString() {
    return "\"" + this.getTitle() + "\" by " + this.getAuthor();
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(getTitle(), book.getTitle())
        && Objects.equals(getAuthor(), book.getAuthor());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTitle(), getAuthor());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(@NonNull Parcel dest, int flags) {
    dest.writeInt(mBookId);
    dest.writeString(mTitle);
    dest.writeString(mAuthor);
  }
}
