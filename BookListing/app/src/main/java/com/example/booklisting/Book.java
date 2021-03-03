package com.example.booklisting;

import android.graphics.Bitmap;

public class Book {
      private String title;
      private String author;
      private String img;
      private String loc;
      public Book(String title,String author,String img,String loc){
          this.title=title;
          this.author=author;
          this.img=img;
          this.loc=loc;
      }

    public String getAuthor() {
        return author;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getLoc() {
        return loc;
    }
}
