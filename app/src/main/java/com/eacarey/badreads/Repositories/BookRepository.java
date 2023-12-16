package com.eacarey.badreads.Repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.eacarey.badreads.Book;
import com.eacarey.badreads.DB.AppDatabase;
import com.eacarey.badreads.DB.BookDAO;
import com.eacarey.badreads.DB.UserBookDAO;
import com.eacarey.badreads.User;
import com.eacarey.badreads.UserBook;
import com.eacarey.badreads.UserBook.UserBookReadState;
import java.util.List;

/**
 * Singleton
 */
public class BookRepository {

  private static volatile BookRepository instance;

  private BookDAO mBookDAO;
  private UserBookDAO mUserBookDAO;
  private LiveData<List<Book>> mAllBooks;

  BookRepository(Application app) {
    AppDatabase db = AppDatabase.getInstance(app);
    this.mBookDAO = db.BookDAO();
    this.mUserBookDAO = db.UserBookDAO();
    this.mAllBooks = this.mBookDAO.getAllBooks();
  }

  public static BookRepository getInstance(Application app) {
    if (instance == null) {
      instance = new BookRepository(app);
    }
    return instance;
  }

  public LiveData<List<Book>> getAllBooks() {
    return this.mAllBooks;
  }

  public LiveData<List<Book>> search(String search) {
    return this.mBookDAO.searchBooks(search);
  }

  public LiveData<Book> getBookByTitle(String title) {
    return this.mBookDAO.getBookByTitle(title);
  }

  public void insert(Book... books) {
    AppDatabase.databaseWriteExecutor.execute(() -> {
      mBookDAO.insert(books);
    });
  }

  public LiveData<UserBook> getUserBook(Book book, User user) {
    return this.mUserBookDAO.getUserBook(book.getBookId(), user.getUserId());
  }

  // TODO: add methods for 1. adding book to a list, 2. changing book state, 3. remove book from list
  public void addBookToWantToReadList(Book book, User user) {
    AppDatabase.databaseWriteExecutor.execute(() -> {
      this.mUserBookDAO.insert(
          new UserBook(user.getUserId(), book.getBookId(), UserBookReadState.WANT_TO_READ));
    });
  }

  public void removeBookFromWantToReadList(Book book, User user) {
    AppDatabase.databaseWriteExecutor.execute(() -> {
      this.mUserBookDAO.delete(
          new UserBook(user.getUserId(), book.getBookId(), UserBookReadState.WANT_TO_READ));
    });
  }

  public LiveData<List<Book>> getBooksByUser(String username) {
    return this.mUserBookDAO.getUserBooks(username);
  }

  public LiveData<List<Book>> getBooksByUserState(String username, UserBookReadState state) {
    return this.mUserBookDAO.getUserBooksByState(username, state);
  }

}
