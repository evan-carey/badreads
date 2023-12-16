package com.eacarey.badreads;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.eacarey.badreads.Repositories.BookRepository;
import com.eacarey.badreads.Repositories.UserRepository;

public class BookDetailViewModel extends AndroidViewModel {

  private BookRepository bookRepository;
  private UserRepository userRepository;
  private MutableLiveData<Book> mBook = new MutableLiveData<>();

  public BookDetailViewModel(Application app) {
    super(app);
    this.bookRepository = BookRepository.getInstance(app);
    this.userRepository = UserRepository.getInstance(app);

  }

  public void selectBook(Book book) {
    this.mBook.setValue(book);
  }

  public LiveData<Book> getBook() {
    return this.mBook;
  }

  public LiveData<UserBook> getUserBook() {
    return Transformations.switchMap(this.mBook, book -> this.bookRepository.getUserBook(book, this.userRepository.getUser()));
  }

  public void addBookToWantToReadList() {
    this.bookRepository.addBookToWantToReadList(this.getBook().getValue(),
        this.userRepository.getUser());
  }

  public void removeBookFromWantToReadList() {
    this.bookRepository.removeBookFromWantToReadList(this.getBook().getValue(),
        this.userRepository.getUser());
  }

}