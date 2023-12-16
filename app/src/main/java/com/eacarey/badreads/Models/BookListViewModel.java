package com.eacarey.badreads.Models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import com.eacarey.badreads.Book;
import com.eacarey.badreads.Repositories.BookRepository;
import com.eacarey.badreads.Repositories.UserRepository;
import java.util.List;

public class BookListViewModel extends AndroidViewModel {
  private UserRepository userRepository;
  private BookRepository bookRepository;
  private final LiveData<List<Book>> mAllBooks;

  public BookListViewModel(Application app) {
    super(app);
    this.userRepository = UserRepository.getInstance(app);
    this.bookRepository = BookRepository.getInstance(app);
    this.mAllBooks = this.bookRepository.getAllBooks();
  }

  public LiveData<List<Book>> getAllBooks() {
    return this.mAllBooks;
  }

  public LiveData<List<Book>> getUserBooks() {
    return Transformations.switchMap(this.userRepository.getUser(), user -> {
      if (user != null) {
        return this.bookRepository.getBooksByUser(user.getUsername());
      } else {
        return null;
      }
    });
  }

  public void insert(Book... books) {
    this.bookRepository.insert(books);
  }
}
