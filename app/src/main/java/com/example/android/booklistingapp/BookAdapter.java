package com.example.android.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Implement an ArrayAdapter of object {@link Book}.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * Constructor of an ArrayAdapter of {@link Book}
     *
     * @param context  used to inflate layout
     * @param bookList list of {@link Book} we want to display
     */
    public BookAdapter(Context context, List<Book> bookList) {
        super(context, 0, bookList);
    }

    /**
     * Provide a view for a AdapterView.
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // get the current {@link Book} object at this position in the list
        Book currentBook = getItem(position);

        // find the TextView for the book title and set its value
        TextView bookTitle = (TextView) listItemView.findViewById(R.id.book_title);
        bookTitle.setText(currentBook.getBookTitle());

        // find the TextView for the book author and set its value
        TextView bookAuthor = (TextView) listItemView.findViewById(R.id.book_author);
        bookAuthor.setText(currentBook.getBookAuthor());

        return listItemView;
    }
}
