package com.quascenta.petersroad.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class BoardDisplay extends AppCompatActivity {
    TextView team1, team2,timer;
    ImageButton score1, score2;
    Button add, restart, end;
    private int[] mImageResIds;
    SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "team1";
    String x, y;
    final int imageCount = 15;
    int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_display);
        team1 = (TextView) findViewById(R.id.t1);
        team2 = (TextView) findViewById(R.id.t2);
        final Resources resources = this.getResources();
        final TypedArray typedArray = resources.obtainTypedArray(R.array.numbers);
        mImageResIds = new int[imageCount];
        score1 = (ImageButton) findViewById(R.id.score1);
        score2 = (ImageButton) findViewById(R.id.score2);
        score1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if(i<=15)
              System.out.println("-----goal"+i);
               mImageResIds[i] = typedArray.getResourceId(i,0);
                score1.setImageResource(mImageResIds[i]);


            }
        });
        add = (Button) findViewById(R.id.button2);
        restart = (Button) findViewById(R.id.button3);
        end = (Button) findViewById(R.id.button);
        sharedPreferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        x = getIntent().getStringExtra("team1");

        y = getIntent().getStringExtra("team2");
       if(x!= null && y!= null){
           team1.setText(x.toUpperCase(Locale.US));
           team2.setText(y.toUpperCase(Locale.US));
       }
        System.out.println(" "+y);


    }

}


