package com.eacarey.badreads.Repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.eacarey.badreads.Book;
import com.eacarey.badreads.DB.AppDatabase;
import com.eacarey.badreads.DB.BookDAO;
import java.util.List;

public class BookRepository {
  private BookDAO mBookDAO;
  private LiveData<List<Book>> mAllBooks;

  public BookRepository(Application app) {
    AppDatabase db = AppDatabase.getInstance(app);
    this.mBookDAO = db.BookDAO();
    this.mAllBooks = this.mBookDAO.getAllBooks();
  }

  public LiveData<List<Book>> getAllBooks() {
    return this.mAllBooks;
  }

  public void insert(Book... books) {
    AppDatabase.databaseWriteExecutor.execute(() -> {
      mBookDAO.insert(books);
    });
  }
}
