package com.eacarey.badreads.ui.booklist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.eacarey.badreads.R;

/**
 * Defines the view holder item for the book list recycler view
 */
public class BookViewHolder extends RecyclerView.ViewHolder {
private final TextView bookItemView;
  public BookViewHolder(@NonNull View itemView) {
    super(itemView);
    this.bookItemView = itemView.findViewById(R.id.book_list_item);
  }

  public void bind(String text) {
    this.bookItemView.setText(text);
  }

  static BookViewHolder create(ViewGroup parent) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_list_item, parent, false);
    return new BookViewHolder(view);
  }
}
