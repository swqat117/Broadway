package com.quascenta.petersroad.droidlink;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.quascenta.petersroad.droidlink.R;


public class RealTimeLineChartActivity extends AppCompatActivity implements
        OnChartValueSelectedListener {
        FloatingActionButton button,b1;
        private LineChart mChart;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_real_time_line_chart);

            mChart = (LineChart) findViewById(R.id.chart1);
            mChart.setOnChartValueSelectedListener(this);
            button = (FloatingActionButton)findViewById(R.id.fab2);
            b1 = (FloatingActionButton)findViewById(R.id.fab3);
            // no description text
            mChart.setDescription("");
            mChart.setNoDataTextDescription("You need to provide data for the chart.");

            // enable touch gestures
            mChart.setTouchEnabled(true);
            mChart.setBackgroundColor(Color.rgb(8,54,100));
            // enable scaling and dragging
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);
            mChart.setDrawGridBackground(false);


            mChart.setPinchZoom(true);



            LineData data = new LineData();
            data.setValueTextColor(Color.WHITE);


            mChart.setData(data);


            Legend l = mChart.getLegend();

            // modify the legend ...
           ;
            l.setForm(LegendForm.LINE);
            l.setTypeface(Typeface.SANS_SERIF);
            l.setTextColor(Color.WHITE);

            XAxis xl = mChart.getXAxis();
            xl.setTypeface(Typeface.SANS_SERIF);
            xl.setTextColor(Color.WHITE);
            xl.setValueFormatter(new DayAxisValueFormatter(mChart));
            xl.setDrawGridLines(false);
            xl.setAvoidFirstLastClipping(true);
            xl.setEnabled(true);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setTypeface(Typeface.SANS_SERIF);
            leftAxis.setTextColor(Color.WHITE);
            leftAxis.setAxisMaxValue(100f);
            leftAxis.setAxisMinValue(0f);
            leftAxis.setDrawGridLines(true);

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setEnabled(false);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addEntry();
                }
            });
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    feedMultiple();
                }
            });
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mChart.clearValues();
                   Toast.makeText(getApplicationContext(),"Values Cleared",Toast.LENGTH_SHORT);
                           return true;
                }
            });

        }



        private void addEntry() {

            LineData data = mChart.getData();

            if (data != null) {

                ILineDataSet set = data.getDataSetByIndex(0);
                // set.addEntry(...); // can be called as well

                if (set == null) {
                    set = createSet();
                    data.addDataSet(set);
                }

                data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
                data.notifyDataChanged();


                mChart.notifyDataSetChanged();

                     mChart.setVisibleXRangeMaximum(120);

                mChart.moveViewToX(data.getEntryCount());


            }
        }

        private LineDataSet createSet() {

            LineDataSet set = new LineDataSet(null, "Dynamic Data");
            set.setAxisDependency(AxisDependency.LEFT);
            set.setColor(ColorTemplate.getHoloBlue());
            set.setCircleColor(Color.WHITE);
            set.setLineWidth(2f);
            set.setCircleRadius(4f);
            set.setFillAlpha(65);
            set.setFillColor(ColorTemplate.getHoloBlue());
            set.setHighLightColor(Color.rgb(244, 117, 117));
            set.setValueTextColor(Color.WHITE);
            set.setValueTextSize(9f);
            set.setDrawValues(false);
            return set;
        }

        private Thread thread;

        private void feedMultiple() {

            if (thread != null)
                thread.interrupt();

            final Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    addEntry();
                }
            };

            thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {

                        // Don't generate garbage runnables inside the loop.
                        runOnUiThread(runnable);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });

            thread.start();
        }

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            Log.i("Entry selected", e.toString());
        }

        @Override
        public void onNothingSelected() {
            Log.i("Nothing selected", "Nothing selected.");
        }

        @Override
        protected void onPause() {
            super.onPause();

            if (thread != null) {
                thread.interrupt();
            }
        }
}