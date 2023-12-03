package com.eacarey.badreads.Repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.eacarey.badreads.Book;
import com.eacarey.badreads.DB.AppDatabase;
import com.eacarey.badreads.DB.BookDAO;
import java.util.List;

/**
 * Singleton
 */
public class BookRepository {
  private static volatile BookRepository instance;

  private BookDAO mBookDAO;
  private LiveData<List<Book>> mAllBooks;

  BookRepository(Application app) {
    AppDatabase db = AppDatabase.getInstance(app);
    this.mBookDAO = db.BookDAO();
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

  public void insert(Book... books) {
    AppDatabase.databaseWriteExecutor.execute(() -> {
      mBookDAO.insert(books);
    });
  }
}
