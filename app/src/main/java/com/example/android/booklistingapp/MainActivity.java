package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    // Constant value for the book loader ID.
    private static final int BOOK_LOADER_ID = 1;

    // Request url to send search request to google books API
    private static String google_books_request_url;

    // Adapter for the list of book
    private BookAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.btn_search);

        // Find a reference to the {@link ListView} in the layout
        final ListView bookListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // hide loading indicator so that it is not display on launch
        final View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader.
        loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);

        bookListView.setEmptyView(mEmptyStateTextView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    // display loading indicator during the search
                    loadingIndicator.setVisibility(View.VISIBLE);
                    // get the user search and form the request
                    formRequestUrl(getUserSearchText());
                    // restart loader
                    getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    loadingIndicator.setVisibility(View.GONE);

                    // Clear the adapter of previous book data
                    mAdapter.clear();

                    // Set empty state text to display "No internet connection."
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
            }
        });
    }

    @Override
    public android.content.Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, google_books_request_url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Book>> loader, List<Book> bookList) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous book data
        mAdapter.clear();

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (bookList != null && !bookList.isEmpty()) {
            mAdapter.addAll(bookList);
        } else {
            // Set empty state text to display "No books found."
            mEmptyStateTextView.setText(R.string.no_books);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    /**
     * Get the text the user has entered for the search
     *
     * @return the search terms
     */
    private String getUserSearchText() {
        EditText searchText = (EditText) findViewById(R.id.search_text);
        // read the name entered and store it in var search
        String search = searchText.getText().toString().replace(' ', '+');
        return search;
    }

    private String formRequestUrl(String search) {
        return google_books_request_url = "https://www.googleapis.com/books/v1/volumes?q=" + search
                + "&maxResults=40";
    }

    private boolean isConnected() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}