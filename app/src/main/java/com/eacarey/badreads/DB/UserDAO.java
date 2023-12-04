package com.eacarey.badreads.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.eacarey.badreads.User;
import java.util.List;

@Dao
public interface UserDAO {

  @Insert
  void insert(User... users);

  @Update
  void update(User... users);

  @Delete
  void delete(User user);

  @Query("SELECT * FROM " + AppDatabase.USERS_TABLE)
  LiveData<List<User>> getAllUsers();

  @Query("SELECT * FROM " + AppDatabase.USERS_TABLE + " WHERE mUserId = :userId LIMIT 1")
  LiveData<User> getUserById(int userId);

  @Query("SELECT * FROM " + AppDatabase.USERS_TABLE + " WHERE mUsername = :username LIMIT 1")
  User getUserByUsername(String username);

  @Query("SELECT * FROM " + AppDatabase.USERS_TABLE + " WHERE mUsername = :username AND mPassword = :password LIMIT 1")
  User getUserByUsernamePassword(String username, String password);

  @Query("DELETE FROM " + AppDatabase.USERS_TABLE)
  void deleteAll();
}
