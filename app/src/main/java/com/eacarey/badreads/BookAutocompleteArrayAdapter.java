package com.eacarey.badreads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class BookAutocompleteArrayAdapter extends ArrayAdapter<Book> {

  Context context;
  int resource;
  //  int textViewResourceId;
  List<Book> mList, mListAll;

  public BookAutocompleteArrayAdapter(Context context, int resource,
      List<Book> bookList) {
    super(context, resource, bookList);
    this.context = context;
    this.resource = resource;
//    this.textViewResourceId = textViewResourceId;
    this.mList = bookList;
    this.mListAll = bookList;
//    this.filteredBooks = new ArrayList<Book>();
  }

  @Override
  public Book getItem(int position) {
    return mList.get(position);
  }

  @Override
  public int getCount() {
    return this.mList.size();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = convertView;
    if (view == null) {
//      LayoutInflater inflater = (LayoutInflater) context
//          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//      view = inflater.inflate(R.layout.books_adapter_item, parent, false);
      view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
      TextView textView = (TextView) view.findViewById(R.id.book_adapter_item_id_text);
      textView.setText(mList.get(position).toString());
    }
    Book book = mList.get(position);
    if (book != null) {
      TextView textView = (TextView) view.findViewById(R.id.book_adapter_item_id_text);
      if (textView != null) {
        textView.setText(book.toString());
      }

    }
    return view;
  }

  @Override
  public Filter getFilter() {

    return nameFilter;
  }

  Filter nameFilter = new Filter() {
    private Object lock = new Object();

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint,
        FilterResults results) {
//      List<Book> filteredList = (List<Book>) results.values;

//      if (results != null && results.count > 0) {
      if (results.values != null) {
//        clear();
//        for (Book book : filteredList) {
//          add(book);
//        }
        mList = (ArrayList<Book>) results.values;
      } else {
        mList = null;
      }
      if (results.count > 0) {
        notifyDataSetChanged();
      } else {
        notifyDataSetInvalidated();
      }
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      FilterResults filterResults = new FilterResults();

      if (mListAll == null) {
        synchronized (lock) {
          mListAll = new ArrayList<>(mList);
        }
      }
      if (constraint == null || constraint.length() == 0) {
        synchronized (lock) {
          filterResults.values = mListAll;
          filterResults.count = mListAll.size();
        }
      } else {
        final String searchStrLower = constraint.toString().toLowerCase();
        ArrayList<Book> filteredBooks = new ArrayList<>();
        for (Book book : mListAll) {
          if (book.getTitle().toLowerCase().contains(searchStrLower)
              || book.getAuthor().toLowerCase().contains(searchStrLower)
          ) {
            filteredBooks.add(book);
          }
        }
        filterResults.values = filteredBooks;
        filterResults.count = filteredBooks.size();

      }
      return filterResults;
    }
  };
}
