package com.eacarey.badreads;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.eacarey.badreads.databinding.ActivityBookDetailBinding;

public class BookDetailActivity extends AppCompatActivity {

  ActivityBookDetailBinding mBinding;

  private BookDetailViewModel mBookDetailViewModel;

  private Button mAddToListButton;
  private Button mRemoveFromListButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_book_detail);
    this.mBinding = ActivityBookDetailBinding.inflate(getLayoutInflater());
    setContentView(mBinding.getRoot());

    this.mBookDetailViewModel = new ViewModelProvider(this).get(BookDetailViewModel.class);

    this.mAddToListButton = this.mBinding.bookDetailButtonAddToList;
    this.mRemoveFromListButton = this.mBinding.bookDetailButtonRemoveFromList;

    Intent intent = getIntent();
    Book book = intent.getParcelableExtra("book");
    mBookDetailViewModel.selectBook(book);

    this.mBookDetailViewModel.getUserBook().observe(this, userBook -> {
      if (userBook == null) {
        this.mAddToListButton.setEnabled(true);
        this.mRemoveFromListButton.setEnabled(false);
      } else {
        this.mAddToListButton.setEnabled(false);
        this.mRemoveFromListButton.setEnabled(true);
      }
    });

    this.mAddToListButton.setOnClickListener(v -> {
      mBookDetailViewModel.addBookToWantToReadList();
      Toast.makeText(this,
          "Added " + this.mBookDetailViewModel.getBook().getValue() + " to the list",
          Toast.LENGTH_SHORT).show();
    });
    this.mRemoveFromListButton.setOnClickListener(v -> {
      mBookDetailViewModel.removeBookFromWantToReadList();
      Toast.makeText(this,
          "Removed " + this.mBookDetailViewModel.getBook().getValue() + " from the list",
          Toast.LENGTH_SHORT).show();
    });

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