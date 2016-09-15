package com.quascenta.petersroad.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ScrollingActivity extends AppCompatActivity implements RageComicListFragment.OnRageComicSelected {
    SharedPreferences sharedPreferences ;
    int count  = 0;
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "team1";
    public static final String PREFS_KEY1 = "team2";
    SharedPreferences.Editor editor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0){
                    Snackbar.make(view, "You cant start a match with no teams", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
                else if(count == 1){
                    Snackbar.make(view, "Only 2 teams can play a match. Do you know football?", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }   else if(count == 2){
                    Intent i = new Intent(getApplicationContext(),BoardDisplay.class);
                    System.out.println(sharedPreferences.getString(PREFS_KEY,null));
                    System.out.println(sharedPreferences.getString("team2",null));
                    i.putExtra("team1",sharedPreferences.getString(PREFS_KEY,null));
                    i.putExtra("team2",sharedPreferences.getString(PREFS_KEY1,null));

                    startActivity(i);
                }

        }});
        if(savedInstanceState == null ){
            getSupportFragmentManager().beginTransaction().add(R.id.root_layout,RageComicListFragment.newInstance(),"rageComicList").commit();
        }
    }
    @Override
    public void onRageComicSelected(int ImageResid, String name, String description) {
        count++;
        editor = sharedPreferences.edit(); //2
        if(count == 1) {
            editor.putString(PREFS_KEY, name); //3
            editor.commit(); //4
            System.out.println(count);
            Toast.makeText(this, "You Selected " + name + " 1 of 2 team(s) added", Toast.LENGTH_SHORT).show();
        }
        else if(count == 2){
            editor.putString(PREFS_KEY1,name);
            editor.commit();
            System.out.println("---Before-"+count);
            Toast.makeText(this,"2 of 2 team(s) added, Press PLAY",Toast.LENGTH_SHORT).show();

            System.out.println("after2--"+count);
        }
        else if(count >= 3){

            Toast.makeText(this,"Only 2 teams can play a match. Do you know football?",Toast.LENGTH_SHORT).show();
            System.out.println("after--"+count);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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
    public void save(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(PREFS_KEY, text); //3
        editor.commit(); //4
    }
}
