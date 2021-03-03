package com.example.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String request_url="https://www.googleapis.com/books/v1/volumes?q=";
//    private static int Book_Loader_Id=1;
//    private BookAdapter mBookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp=request_url;
                EditText editText = (EditText) findViewById(R.id.type);
                String url = editText.getText().toString();
                temp += url.toLowerCase() + "&maxResults=10";
                new BookAsyncTask().execute(temp);
            }
        });


//        android.app.LoaderManager loaderManager = getLoaderManager();
//        loaderManager.initLoader(Book_Loader_Id, null, this).forceLoad();
    }

//    @NonNull
//    @Override
//    public Loader<ArrayList<Book>> onCreateLoader(int id, @Nullable Bundle args) {
//        return new BookLoader(MainActivity.this,request_url);
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
//        mBookAdapter.clear();
//        if(data!=null && !data.isEmpty())
//        {
//            mBookAdapter.addAll(data);
//        }
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<ArrayList<Book>> loader) {
//        mBookAdapter.clear();
//    }

    private class BookAsyncTask extends AsyncTask<String,Void, ArrayList<Book>> {
            @Override
            protected ArrayList<Book> doInBackground(String... urls) {
                // Create a list of earthquake locations.
                if(urls.length<1||urls[0]==null)
                    return null;

                           ArrayList<Book> books = Utils.fetchBooksdata(urls[0]);


                return books;
            }

            @Override
            protected void onPostExecute(ArrayList<Book> books) {


                ListView bookListView = (ListView) findViewById(R.id.booklist);
                BookAdapter bookAdapter =  new BookAdapter(MainActivity.this,books);
                //Adding intent
                bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Book b = bookAdapter.getItem(i);
                        String url =b.getLoc();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
                bookListView.setAdapter(bookAdapter);


            }
        }

    }