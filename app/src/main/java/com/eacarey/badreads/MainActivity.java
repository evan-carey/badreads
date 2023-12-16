package com.eacarey.badreads;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.eacarey.badreads.Models.BookListViewModel;
import com.eacarey.badreads.Models.UserViewModel;
import com.eacarey.badreads.databinding.ActivityMainBinding;
import com.eacarey.badreads.ui.booklist.BookListAdapter;
import com.eacarey.badreads.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

  public static final int LOGIN_ACTIVITY_REQUEST_CODE = 1;

  ActivityMainBinding binding;

  private UserViewModel mUserViewModel;
  private BookListViewModel mBookListViewModel;
//  private BookDetailViewModel mBookDetailViewModel;

  private Button mAdminButton;
  private AutoCompleteTextView mSearchBooksView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    mBookListViewModel = new ViewModelProvider(this).get(BookListViewModel.class);
//    mBookDetailViewModel = new ViewModelProvider(this).get(BookDetailViewModel.class);

    // bind Logout button
    final Button logoutButton = this.binding.logOutButton;
    logoutButton.setOnClickListener(v -> {
      this.mUserViewModel.logout();
      Intent intent = new Intent(MainActivity.this, LoginActivity.class);
      startActivityForResult(intent, LOGIN_ACTIVITY_REQUEST_CODE);
    });

    // bind Admin button
    this.mAdminButton = binding.adminPageButton;
    mAdminButton.setOnClickListener(v -> {
      Intent intent = AdminPageActivity.getIntent(getApplicationContext());
      startActivity(intent);
    });

    // bind list view
    RecyclerView bookListView = binding.recyclerview;
    final BookListAdapter bookListAdapter = new BookListAdapter(new BookListAdapter.BookDiff());
    bookListView.setAdapter(bookListAdapter);
    bookListView.setLayoutManager(new LinearLayoutManager(this));
//    Transformations.switchMap(this.mUserViewModel.getUser(),
//            user -> this.mBookListViewModel.getUserBooks(user.getUsername()))
    this.mBookListViewModel.getUserBooks().observe(this, bookListAdapter::submitList);

    // bind search
    this.mSearchBooksView = binding.searchBooksAutocomplete;
    this.mSearchBooksView.setThreshold(1);
    // clear text on focus
    this.mSearchBooksView.setOnFocusChangeListener((v, hasFocus) -> {
      if (hasFocus) {
        mSearchBooksView.setText("");
      }
    });

    this.mUserViewModel.getUser().observe(this, user -> {
      boolean isLoggedIn = user != null;
      if (!isLoggedIn) {
        // user not logged in -> redirect to LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, LOGIN_ACTIVITY_REQUEST_CODE);
      } else {
        this.binding.welcomeLabel.setText(
            String.format(getString(R.string.welcome_message), user.getUsername()));
        this.mSearchBooksView.setText(null);

        if (user.getIsAdmin()) {
          this.mAdminButton.setVisibility(View.VISIBLE);
        } else {
          this.mAdminButton.setVisibility(View.INVISIBLE);
        }
      }
    });

    this.mBookListViewModel.getAllBooks().observe(this, books -> {
      final ArrayAdapter<Book> bookAutcompleteAdapter = new BookAutocompleteArrayAdapter(this,
          R.layout.books_adapter_item, books);
      this.mSearchBooksView.setAdapter(bookAutcompleteAdapter);

      this.mSearchBooksView.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          String selected = parent.getItemAtPosition(position).toString();
//          mSearchBooksView.setText(selected);
//          mBookDetailViewModel.selectBook(selected);

//          Intent intent = BookDetailActivity.getIntent(getApplicationContext());
//          startActivity(intent);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
          // ignore
        }
      });

      // when user selects a book from the search, navigate to the BookDetailActivity
      this.mSearchBooksView.setOnItemClickListener((parent, view, position, id) -> {
        Book selected = (Book) parent.getItemAtPosition(position);
//        mSearchBooksView.setText(selected);
//        mBookDetailViewModel.selectBook(selected);
        Intent intent = BookDetailActivity.getIntent(this);
        intent.putExtra("book", selected);
        startActivity(intent);
      });
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    this.mSearchBooksView.setText(null);
  }

  public static Intent getIntent(Context context) {
    return new Intent(context, MainActivity.class);
  }
}
