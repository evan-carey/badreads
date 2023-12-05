package com.eacarey.badreads;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.eacarey.badreads.databinding.FragmentBookDetailBinding;

public class BookDetailFragment extends Fragment {

  private BookDetailViewModel mViewModel;
  FragmentBookDetailBinding mBinding;

  TextView mTitle;
  TextView mAuthor;

  public static BookDetailFragment newInstance() {
    return new BookDetailFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mBinding = FragmentBookDetailBinding.inflate(inflater, container, false);
    mTitle = mBinding.bookDetailTitle;
    mAuthor = mBinding.bookDetailAuthor;

    mViewModel = new ViewModelProvider(requireActivity()).get(BookDetailViewModel.class);

    mViewModel.getBook().observe(getViewLifecycleOwner(), book -> {
      this.mTitle.setText(book.getTitle());
      this.mAuthor.setText(book.getAuthor());
    });

    return mBinding.getRoot();
//    return inflater.inflate(R.layout.fragment_book_detail, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
//    mViewModel = new ViewModelProvider(requireActivity()).get(BookDetailViewModel.class);
//
//    mViewModel.getBook().observe(getViewLifecycleOwner(), book -> {
//      this.mTitle.setText(book.getTitle());
//      this.mAuthor.setText(book.getAuthor());
//    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mBinding = null;
  }
}