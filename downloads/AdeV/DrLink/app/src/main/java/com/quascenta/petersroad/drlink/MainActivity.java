package com.quascenta.petersroad.drlink;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
    public void initViews(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.logger_main_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Data> androidVersions = prepareData();
        DeviceListAdapter adapter = new DeviceListAdapter(getApplicationContext(),androidVersions);
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<Data> prepareData(){
        TypedArray imgs = getResources().obtainTypedArray(R.array.event_image_id);
        TypedArray names = getResources().obtainTypedArray(R.array.image_title);
        //mImgView1.setImageResource(imgs.getResourceId(i, -1));

        ArrayList<Data> a = new ArrayList<>();
        for(int i=0;i<imgs.length();i++){
            Data a1 = new Data();
            System.out.println("names "+ names.getString(i));
            a1.setTitle(names.getString(i));
            a.add(a1);
        }
        imgs.recycle();
        return a;
    }
}
