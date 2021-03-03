package com.example.booklisting;

import android.util.Log;
import android.view.inputmethod.InputConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class Utils {

    private Utils(){
    }

    public static ArrayList<Book> fetchBooksdata(String request_url){
        Log.v("Utils",request_url);
        URL url = createURL(request_url);
        String JSONResponse= null;
        try{
            JSONResponse = makeHTTPRequest(url);
        } catch (Exception e) {
           Log.e("Utils","Error creating JSON Response",e);
        }
        ArrayList<Book> books = extractBooks(JSONResponse);
        return books;
    }

    private static ArrayList<Book> extractBooks(String jsonResponse) {
         ArrayList<Book> books = new ArrayList<Book>();
         try{
             JSONObject jsonObject = new JSONObject(jsonResponse);
             JSONArray items = jsonObject.getJSONArray("items");
             for(int i=0;i<items.length();i++){
                 JSONObject book = items.getJSONObject(i);
                 JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                 String title = volumeInfo.getString("title");
                 JSONArray authorsArray= volumeInfo.getJSONArray("authors");
                 StringBuilder authors=new StringBuilder();
                 int j;
                 for(j=0;j<authorsArray.length()-1;j++){
                     authors.append(authorsArray.get(j));
                     authors.append(" , ");
                 }
                 authors.append(authorsArray.get(j));
                 JSONObject imageLinks =volumeInfo.getJSONObject("imageLinks");
                 String img=imageLinks.getString("thumbnail");
                 String previewLink=volumeInfo.getString("previewLink");
                 books.add(new Book(title,authors.toString(),img,previewLink));
             }

         } catch (JSONException e) {
             Log.e("Utils ","Error parsing the JSON response",e);
         }
         return books;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String JSONResponse="";
        if(url==null)
            return JSONResponse;
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try{
            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream=urlConnection.getInputStream();
                JSONResponse = readFromStream(inputStream);
            }
            else{
                Log.e("Utils","Error Response Code: "+urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Utils","Problem retrieving JSON result",e);
        }finally {
           if(urlConnection!=null)
               urlConnection.disconnect();
           if(inputStream!=null)
               inputStream.close();
        }
        return JSONResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line =bufferedReader.readLine();
            while(line!=null)
            {
                stringBuilder.append(line);
                line=bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static URL createURL(String request_url) {
        URL url= null;
        try{
            url = new URL(request_url);
        } catch (MalformedURLException e) {
           Log.e("Utils ","Error with creating URL ",e);
        }
        return url;
    }
}
