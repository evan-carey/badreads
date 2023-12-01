package com.eacarey.badreads.DB;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.eacarey.badreads.Book;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "Badreads.DB";
  public static final String BOOKS_TABLE = "books_table";

  private static volatile AppDatabase instance;

  private static final int NUMBER_OF_THREADS = 4;
  public static final ExecutorService databaseWriteExecutor =
      Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  private static final Object LOCK = new Object();

  public abstract BookDAO BookDAO();

  public static AppDatabase getInstance(Context context) {
    if (instance == null) {
      synchronized (LOCK) {
        if (instance == null) {
          instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
              DATABASE_NAME).build();
        }
      }
    }
    return instance;
  }

}
