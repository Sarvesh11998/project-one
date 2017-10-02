package com.example.lenovo.my_networking;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Lenovo on 10/1/2017.
 */

public class PostAsync extends AsyncTask<String, Void, ArrayList<Posts>> {

    public PostsDownloadListener postsDownloadListener;

    public PostAsync(PostsDownloadListener listener) {
        postsDownloadListener = listener;
    }


    @Override
    protected ArrayList<Posts> doInBackground(String... strings) {

        Log.i("Course AsyncTask", "Inside Async Task ");

        String urlstring = strings[0];

        try {
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            Log.i("Courses Response:", "Connection Started");
            urlConnection.connect();
            Log.i("Courses Response:", "Connection Complete");

            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String response = "";
            while (scanner.hasNext()) {
                response = response + scanner.next();
            }
            Log.i("Courses Response:",response);
            ArrayList<Posts> post = parseposts(response);
            return post;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Posts> parseposts(String response) throws JSONException {

        ArrayList<Posts> post = null;


        JSONArray postJSONArray = new JSONArray(response);

        if (postJSONArray != null) {
            post = new ArrayList<>();
            for (int i = 0; i < postJSONArray.length(); i++) {
                JSONObject postObject = postJSONArray.getJSONObject(i);
                int id = postObject.getInt("id");
                String title = postObject.getString("title");
                String body = postObject.getString("body");
                Posts posts = new Posts(id, title, body);
                post.add(posts);
            }
        }


        return post;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Posts> post) {

        postsDownloadListener.onDownload(post);
    }


    public static interface PostsDownloadListener {

        void onDownload(ArrayList<Posts> post);

    }


}
