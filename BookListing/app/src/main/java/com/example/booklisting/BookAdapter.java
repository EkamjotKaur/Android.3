package com.example.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.booklisting.R.layout.list_item;


public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, @NonNull ArrayList<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Book currBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currBook.getTitle());

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        authorView.setText(currBook.getAuthor());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.img);
        String url = currBook.getImg().replace("http", "https");
        Picasso.get().load(url).into(imageView);
//        Log.v("Book Adapter",currBook.getImg());

//        imageView.setImageBitmap(getBitmapFromURL(currBook.getImg()));

        return listItemView;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}