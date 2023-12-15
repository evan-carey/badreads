package com.eacarey.badreads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BookDetailActivity extends AppCompatActivity {

  private BookDetailViewModel mBookDetailViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book_detail);
    this.mBookDetailViewModel = new ViewModelProvider(this).get(BookDetailViewModel.class);

    Intent intent = getIntent();
    String bookTitle = intent.getStringExtra("book_title");
    mBookDetailViewModel.selectBook(bookTitle);

    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
        .add(R.id.book_detail_fragment_container_view, BookDetailFragment.class, savedInstanceState)
        .commit();
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  public static Intent getIntent(Context context) {
    Intent intent = new Intent(context, BookDetailActivity.class);
    return intent;
  }
}