package com.eacarey.badreads.DB;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.eacarey.badreads.Book;
import com.eacarey.badreads.User;
import com.eacarey.badreads.UserBook;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Book.class, User.class, UserBook.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "Badreads.DB";
  public static final String BOOKS_TABLE = "books_table";
  public static final String USERS_TABLE = "users_table";
  public static final String USER_BOOKS_TABLE = "user_books_table";

  private static volatile AppDatabase instance;

  private static final int NUMBER_OF_THREADS = 4;
  public static final ExecutorService databaseWriteExecutor =
      Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  public abstract BookDAO BookDAO();

  public abstract UserDAO UserDAO();

  public abstract UserBookDAO UserBookDAO();

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
   * Override the onCreate method to populate the database. For this sample, we clear the database
   * every time it is created.
   */
  private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);

      databaseWriteExecutor.execute(() -> {
        // Populate the database in the background
        populateUsers();
        populateBooks();
      });
    }
  };

  private static void populateUsers() {
    UserDAO userDAO = instance.UserDAO();
    userDAO.deleteAll();

    User user = new User("testuser1", "testuser1", false);
    User admin = new User("admin2", "admin2", true);
    userDAO.insert(user, admin);

  }

  private static void populateBooks() {
    BookDAO bookDAO = instance.BookDAO();
    bookDAO.deleteAll();

    Book wayOfKings = new Book("The Way of Kings", "Brandon Sanderson");
    Book warbreaker = new Book("Warbreaker", "Brandon Sanderson");
    Book furiesOfCalderon = new Book("Furies of Calderon", "Jim Butcher");
    Book brokenEarth1 = new Book("The Fifth Season", "N. K. Jemisin");
    Book brokenEarth2 = new Book("The Obelisk Gate", "N. K. Jemisin");
    Book brokenEarth3 = new Book("The Stone Sky", "N. K. Jemisin");
    Book dune1 = new Book("Dune", "Frank Herbert");
    Book dune2 = new Book("Dune Messiah", "Frank Herbert");
    Book dune3 = new Book("Children of Dune", "Frank Herbert");
    Book firstLaw1 = new Book("The Blade Itself", "Joe Abercrombie");
    Book firstLaw2 = new Book("Before They Are Hanged", "Joe Abercrombie");
    Book firstLaw3 = new Book("Last Argument of Kings", "Joe Abercrombie");
    Book locke = new Book("The Lies of Locke Lamora", "Scott Lynch");
    Book threeBody1 = new Book("The Three-Body Problem", "Cixin Liu");
    Book threeBody2 = new Book("The Dark Forest", "Cixin Liu");
    Book threeBody3 = new Book("Death's End", "Cixin Liu");
    Book kingkiller1 = new Book("The Name of the Wind", "Patrick Rothfuss");
    Book kingkiller2 = new Book("The Wise Man's Fear", "Patrick Rothfuss");
    Book redrising1 = new Book("Red Rising", "Pierce Brown");
    Book redrising2 = new Book("Golden Son", "Pierce Brown");
    Book redrising3 = new Book("Morning Star", "Pierce Brown");
    Book neuromancer = new Book("Neuromancer", "William Gibson");

    bookDAO.insert(wayOfKings, warbreaker, furiesOfCalderon, brokenEarth1, brokenEarth2,
        brokenEarth3, dune1, dune2, dune3, firstLaw1, firstLaw2, firstLaw3, locke, threeBody1,
        threeBody2, threeBody3, kingkiller1, kingkiller2, redrising1, redrising2, redrising3,
        neuromancer);
  }
}
