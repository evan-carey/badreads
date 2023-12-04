package com.eacarey.badreads.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.eacarey.badreads.Book;
import com.eacarey.badreads.UserBook;
import com.eacarey.badreads.UserBook.UserBookReadState;
import java.util.List;

@Dao
public interface UserBookDAO {

  @Query(
      "SELECT b.* FROM " + AppDatabase.BOOKS_TABLE + " b " +
          "JOIN " + AppDatabase.USER_BOOKS_TABLE + " ub ON b.mBookId = ub.bookId " +
          "JOIN " + AppDatabase.USERS_TABLE + " u ON ub.userId = u.mUserId " +
          "WHERE u.mUsername = :username")
  LiveData<List<Book>> getUserBooks(String username);

  @Query(
      "SELECT ub.state, b.* FROM " + AppDatabase.BOOKS_TABLE + " b " +
          "JOIN " + AppDatabase.USER_BOOKS_TABLE + " ub ON b.mBookId = ub.bookId " +
          "JOIN " + AppDatabase.USERS_TABLE + " u ON ub.userId = u.mUserId " +
          "WHERE u.mUsername = :username AND ub.state = :state")
  LiveData<List<Book>> getUserBooksByState(String username, UserBookReadState state);

  @Insert
  void insert(UserBook... userBooks);

  @Update
  void update(UserBook... userBooks);

  @Delete
  void delete(UserBook userBook);
}
