package com.quascenta.petersroad.droidlink;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Random;

import static com.quascenta.petersroad.droidlink.DeviceListRecyclerView.*;

public class ScrollingActivity extends AppCompatActivity  {
    private RecyclerView mRecyclerView;
    private DeviceListRecyclerView mAdapter;
    private AdapterViewFlipper viewFlipper;
    private LinearLayoutManager mLayoutManager;
    private LineGraphSeries<DataPoint> SeriesArray;
    private PointsGraphSeries<DataPoint> PointsArray;
    private FragmentStatePagerAdapter adapterViewPager;
    Button b1, b2;
    private int LastX = 0;
    private final Handler mHandler = new Handler();
    private Thread mTimer1;
    private Thread mTimer2;
    private double graph2LastXValue = 5d;
    private GraphView graphView;
    private LineChart chart;
    Random mRand = new Random();
    Intent i;
    private static int LOAD_IMAGE_RESULTS = 1;
    //  private StaggeredGridLayoutManager g;
    private static String LOG_TAG = "RecyclerViewActivity";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        System.out.println("Passing Oncreate");
        addTypeFace();
        init1();
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ArrayList<Data> devicelist = prepareDataforDevices();
        mAdapter = new DeviceListRecyclerView(this, devicelist);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Button b1 = (Button) findViewById(R.id.contact);
        Button b2 = (Button) findViewById(R.id.feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OnItemClickListener om = new OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                setData(20,position+5);
                chart.fitScreen();
                chart.animateX(1000, Easing.EasingOption.Linear);
                chart.notifyDataSetChanged();
                chart.invalidate();
                System.out.println("added");
                Toast.makeText(getApplicationContext(),"Loading..",Toast.LENGTH_SHORT);
            }
        };
        mAdapter.setOnItemClickListener(om);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(getApplicationContext(),RealTimeLineChartActivity.class);
                startActivity(intent);
            }
        });
    }
    public void init1(){
        chart = (LineChart)findViewById(R.id.chart);


        chart.setDescription(" ");

        chart.setBackgroundColor(Color.rgb(8,54,100));
        chart.setPinchZoom(false);
        chart.setViewPortOffsets(0,0,0,0);
        chart.setVerticalScrollBarEnabled(true);
        chart.setTouchEnabled(true);
        chart.fitScreen();
        chart.setScaleEnabled(true);
        chart.setDragEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(20);
        XAxis x = chart.getXAxis();
        x.setAxisLineColor(Color.CYAN);
        x.setDrawAxisLine(true);
        x.setEnabled(true);
        x.setAxisLineColor(Color.WHITE);
        //x.setGridColor(Color.WHITE);
        x.setValueFormatter(new DayAxisValueFormatter(chart));
        x.setTextColor(Color.WHITE);

        YAxis y = chart.getAxisLeft();
        y.setTypeface(Typeface.SANS_SERIF);
        y.setLabelCount(10, false);
        y.setTextColor(Color.WHITE);
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);

        y.setAxisLineColor(Color.WHITE);
        chart.getAxisRight().setEnabled(true);
        setData(20,5);
        setData(35,5);
        chart.getLegend().setEnabled(false);
        chart.animateX(1000, Easing.EasingOption.Linear);

        chart.notifyDataSetChanged();
        chart.invalidate();



    }
    public void init(){
      //  graphView = (GraphView) findViewById(R.id.graph1);
        PointsArray = new PointsGraphSeries<DataPoint>(new DataPoint[]{new DataPoint(0,1),new DataPoint(1,2),new DataPoint(3,1),new DataPoint(4,5),new DataPoint(5,8),new DataPoint(6,1)});
        PointsArray.setColor(Color.WHITE);
        SeriesArray = new LineGraphSeries<DataPoint>(new DataPoint[]{new DataPoint(0,1),new DataPoint(1,2),new DataPoint(3,1),new DataPoint(4,5),new DataPoint(5,8),new DataPoint(6,1)});
        SeriesArray.setColor(Color.WHITE);
        graphView.addSeries(SeriesArray);
        graphView.setBackgroundColor(Color.rgb(8,53,100));
        graphView.getGridLabelRenderer().setGridColor(Color.rgb(8,54,100));
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.YELLOW);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.YELLOW);
        graphView.getViewport().setBackgroundColor(Color.rgb(8,53,100));
        graphView.addSeries(PointsArray);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(10);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(3.5);
        graphView.getViewport().setMaxY(8);
        graphView.setTitleColor(Color.WHITE);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScalable(true);
    }
    private ArrayList<LoadImage> prepareData() {
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_id);
        TypedArray names = getResources().obtainTypedArray(R.array.image_name);
        //mImgView1.setImageResource(imgs.getResourceId(i, -1));

        ArrayList<LoadImage> a = new ArrayList<>();
        for (int i = 0; i < imgs.length(); i++) {
            LoadImage a1 = new LoadImage();
            a1.setImageid(imgs.getResourceId(i, -1));
            a1.setTitle("text");
            a.add(a1);
        }
        imgs.recycle();
        return a;
    }



    private ArrayList<EventData> prepareDataforEventLog() {
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_title);
        TypedArray names = getResources().obtainTypedArray(R.array.event_image_id);
        //mImgView1.setImageResource(imgs.getResourceId(i, -1));

        ArrayList<EventData> a = new ArrayList<>();
        for (int i = 0; i < imgs.length(); i++) {
            System.out.println("pass" + i);
            EventData a1 = new EventData();
            a1.setImage_id(R.drawable.p8);
            a1.setImage_title("EventLogger");
            a.add(a1);
        }
        imgs.recycle();
        return a;
    }

    public void addTypeFace() {
        TextView tv = (TextView) findViewById(R.id.custom);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "RobotoTTF/RobotoCondensed-Light.ttf");

        tv.setTypeface(face);
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

    @Override
    public void onPause() {

        super.onPause();
    }

    // add random data to graph
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        SeriesArray.appendData(new DataPoint(LastX++, mRand.nextDouble() * 10d), true, 10);
    }

    private void addEntry1() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        SeriesArray.appendData(new DataPoint(mRand.nextDouble(), mRand.nextDouble() * 10d), true, 10);
    }


    private ArrayList<Data> prepareDataforDevices
            () {
        TypedArray device_name = getResources().obtainTypedArray(R.array.device_name);
        TypedArray device_id = getResources().obtainTypedArray(R.array.device_id);
        //mImgView1.setImageResource(imgs.getResourceId(i, -1));

        ArrayList<Data> a = new ArrayList<>();
        for (int i = 0; i < device_id.length(); i++) {
            Data a1 = new Data();
            a1.setmText2(device_id.getString(i));
            a1.setmText1(device_name.getString(i));
            a.add(a1);
        }
        return a;
    }

    public void setData(int count, float range){
        ArrayList<Entry> yvals = new ArrayList<Entry>();

        for(int i=0;i<count;i++){
            float mul = (range +1);
            float val = (float) (Math.random() + mul) + 20;
            yvals.add(new Entry(i,val));
        }
        LineDataSet set1;

        if(chart.getData() != null && chart.getData().getDataSetCount()>0){
            set1 = (LineDataSet)chart.getData().getDataSetByIndex(0);
            set1.setValues(yvals);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        }
        else{
            set1 = new LineDataSet(yvals,"DataSet1");
            set1.setMode(LineDataSet.Mode.LINEAR);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(0.4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244,117,117));
            set1.setColor(Color.MAGENTA);
            set1.setFillColor(Color.YELLOW);

            set1.setFillAlpha(1000);
            set1.setDrawHorizontalHighlightIndicator(true);
            set1.setFillFormatter(new FillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });
            LineData data = new LineData(set1);
            data.setValueTypeface(Typeface.SANS_SERIF);
            data.setValueTextSize(8f);
            data.setDrawValues(false);
            chart.setData(data);
            chart.notifyDataSetChanged();
            chart.invalidate();


        }
    }

    public void setData1(int count, float range){
        ArrayList<Entry> yvals1 = new ArrayList<Entry>();

        for(int i=0;i<count;i++){
            float mul = (range +1);
            float val = (float) (Math.random() + mul) + 20;
            yvals1.add(new Entry(i,val));
        }
        LineDataSet set2;

        if(chart.getData() != null && chart.getData().getDataSetCount()>0){
            set2 = (LineDataSet)chart.getData().getDataSetByIndex(0);
            set2.setValues(yvals1);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        }
        else{
            set2 = new LineDataSet(yvals1,"DataSet1");
            set2.setMode(LineDataSet.Mode.LINEAR);
            set2.setDrawCircles(false);
            set2.setLineWidth(1.8f);
            set2.setCircleRadius(0.4f);
            set2.setCircleColor(Color.WHITE);
            set2.setHighLightColor(Color.rgb(244,117,117));
            set2.setColor(Color.MAGENTA);
            set2.setFillColor(Color.YELLOW);

            set2.setFillAlpha(1000);
            set2.setDrawHorizontalHighlightIndicator(true);
            set2.setFillFormatter(new FillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });
            LineData data1 = new LineData(set2);
            data1.setValueTypeface(Typeface.SANS_SERIF);
            data1.setValueTextSize(8f);
            data1.setDrawValues(false);
            chart.setData(data1);
            chart.notifyDataSetChanged();
            chart.invalidate();

        }}
    public void prepareGallery(Intent data) {

        //  Uri selectedImageURI = data.getData();
        //  File imageFile = new File(getRealPathFromURI(selectedImageURI));
        //  File[] files = imageFile.listFiles();
        //  for (File file : files){
        //     Uri uri = Uri.fromFile(file);
        //mAdapter.add(
        //      mAdapter.getItemCount(),
        //    selectedImageURI);
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();

        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            System.out.println("ddd" + result);
            cursor.close();
        }
        return result;


    }






    // Extend from SmartFragmentStatePagerAdapter now instead for more dynamic ViewPager items
    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FirstFragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return FirstFragment.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}

