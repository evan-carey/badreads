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
import com.eacarey.badreads.Models.BookViewModel;
import com.eacarey.badreads.Models.UserViewModel;
import com.eacarey.badreads.databinding.ActivityMainBinding;
import com.eacarey.badreads.ui.login.LoginActivity;
import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

  public static final int LOGIN_ACTIVITY_REQUEST_CODE = 1;

  ActivityMainBinding binding;

  private UserViewModel mUserViewModel;
  private BookViewModel mBookViewModel;
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
    mBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
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

    // bind search
    this.mSearchBooksView = binding.searchBooksAutocomplete;
    this.mSearchBooksView.setThreshold(1);
    // clear text on focus
    this.mSearchBooksView.setOnFocusChangeListener((v, hasFocus) -> {
      if (hasFocus) {
        mSearchBooksView.setText("");
      }
    });

    this.mBookViewModel.getAllBooks().observe(this, books -> {
      // TODO: write custom ArrayAdapter so we can use Book objects (to search on title and author)
      // https://stackoverflow.com/questions/16782288/autocompletetextview-with-custom-adapter-and-filter
//      List<String> bookStrings = books.stream().map(Book::getTitle).collect(Collectors.toList());

//      ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//          android.R.layout.simple_dropdown_item_1line, bookStrings);
      ArrayAdapter<Book> adapter = new BooksArrayAdapter(this, R.layout.books_adapter_item, books);
      this.mSearchBooksView.setAdapter(adapter);

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
    if (!mUserViewModel.isLoggedIn()) {
      // user not logged in -> redirect to LoginActivity

      Intent intent = new Intent(MainActivity.this, LoginActivity.class);
      startActivityForResult(intent, LOGIN_ACTIVITY_REQUEST_CODE);
    } else {
      // user is logged in -> display user name
      User user = mUserViewModel.getUser();

      binding.welcomeLabel.setText(
          String.format(getString(R.string.welcome_message), user.getUsername()));
      this.mSearchBooksView.setText(null);

      if (user.getIsAdmin()) {
        this.mAdminButton.setVisibility(View.VISIBLE);
      }
    }
  }

//  public void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//
//    // return from LoginActivity
//    if (requestCode == LOGIN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
////      this.mUserViewModel = (LoggedInUserView) data.getParcelableExtra(LoginActivity.LOGIN_EXTRA_REPLY);
////      binding.welcomeLabel.setText(
////          String.format(getString(R.string.welcome_message), this.mUserViewModel.getDisplayName()));
//    }
//  }

  public static Intent getIntent(Context context) {
    return new Intent(context, MainActivity.class);
  }
}
