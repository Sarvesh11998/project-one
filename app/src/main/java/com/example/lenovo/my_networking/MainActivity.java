package com.example.lenovo.my_networking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostAsync.PostsDownloadListener {
    ArrayList<Posts> post = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> titles = new ArrayList<>();
    // ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchpost();
            }
        });

        ListView listView = (ListView) findViewById(R.id.lv);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);
        //   progressBar=(ProgressBar)findViewById(R.id.pb);
    }


    private void fetchpost() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        PostAsync postAsync = new PostAsync(this);
        postAsync.execute(url);
        Log.i("Course Async Task", "After execution");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //in master branch


    //in second branch







    @Override
    public void onDownload(ArrayList<Posts> post) {


        titles.clear();
        for (Posts posts : post) {
            titles.add(posts.title);
        }

        arrayAdapter.notifyDataSetChanged();


    }

}

