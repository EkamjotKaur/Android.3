package com.example.booklisting;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {
    private String request_url;
    public BookLoader(Context context,String url){
        super(context);
        request_url=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Book> loadInBackground() {
       final ArrayList<Book> books=Utils.fetchBooksdata(request_url);
        return books;
    }
}
