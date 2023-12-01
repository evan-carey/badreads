package com.eacarey.badreads.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.eacarey.badreads.Book;
import java.util.List;

@Dao
public interface BookDAO {

  @Insert
  void insert(Book... books);

  @Update
  void update(Book... books);

  @Delete
  void delete(Book book);

  @Query("SELECT * FROM books_table ORDER BY mAuthor ASC")
  LiveData<List<Book>> getAllBooks();
}
