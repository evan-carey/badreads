package com.eacarey.badreads.DB;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.eacarey.badreads.Book;
import com.eacarey.badreads.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Book.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "Badreads.DB";
  public static final String BOOKS_TABLE = "books_table";
  public static final String USERS_TABLE = "users_table";

  private static volatile AppDatabase instance;

  private static final int NUMBER_OF_THREADS = 4;
  public static final ExecutorService databaseWriteExecutor =
      Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  public abstract BookDAO BookDAO();
  public abstract UserDAO UserDAO();

  public static AppDatabase getInstance(Context context) {
    if (instance == null) {
      synchronized (AppDatabase.class) {
        if (instance == null) {
          instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
              DATABASE_NAME)
              .addCallback(sRoomDatabaseCallback)
              .allowMainThreadQueries()
              .build();
        }
      }
    }
    return instance;
  }

  /**
   * Override the onCreate method to populate the database.
   * For this sample, we clear the database every time it is created.
   */
  private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);

      databaseWriteExecutor.execute(() -> {
        // Populate the database in the background.
        // If you want to start with more words, just add them.
        UserDAO userDAO = instance.UserDAO();
        userDAO.deleteAll();

        User user = new User("testuser1", "testuser1", false);
        User admin = new User("admin2", "admin2", true);
        userDAO.insert(user, admin);
      });
    }
  };
}
