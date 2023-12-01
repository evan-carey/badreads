package com.eacarey.badreads.Models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.eacarey.badreads.Book;
import com.eacarey.badreads.Repositories.BookRepository;
import java.util.List;

public class BookViewModel extends AndroidViewModel {
  private BookRepository mRepository;
  private final LiveData<List<Book>> mAllBooks;

  public BookViewModel(Application app) {
    super(app);
    this.mRepository = new BookRepository(app);
    this.mAllBooks = this.mRepository.getAllBooks();
  }

  public LiveData<List<Book>> getAllBooks() {
    return this.mAllBooks;
  }

  public void insert(Book... books) {
    this.mRepository.insert(books);
  }
}
