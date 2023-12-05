package com.eacarey.badreads;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.eacarey.badreads.Repositories.BookRepository;

public class BookDetailViewModel extends AndroidViewModel {
  private BookRepository mRepository;
  private LiveData<Book> mBook = new MutableLiveData<>();
  private LiveData<UserBook> mUserBook;

  public BookDetailViewModel(Application app) {
    super(app);
    this.mRepository = BookRepository.getInstance(app);
  }

  public void selectBook(String title) {
    this.mBook = this.mRepository.getBookByTitle(title);
  }

  public LiveData<Book> getBook() {
    return this.mBook;
  }

}