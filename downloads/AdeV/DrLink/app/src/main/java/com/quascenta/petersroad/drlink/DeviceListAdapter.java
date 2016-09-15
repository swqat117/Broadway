package com.quascenta.petersroad.drlink;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by AKSHAY on 9/7/2016.
 */
public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder>  {
    Context mcontext;
    private static String LOG_TAG = "DeviceListRecyclerViewAdapter";
    private ArrayList<Data> mDataset;
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    OnItemClickListener mItemClickListener;

    public DeviceListAdapter(Context context,ArrayList<Data> mDataset){
        this.mcontext = context;
        this.mDataset = mDataset;
    }
    @Override
    public DeviceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.graph_cardview, parent, false);

        ViewHolder dataObjectHolder = new ViewHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DeviceListAdapter.ViewHolder holder, int position) {
        System.out.println(mDataset.get(position).getTitle()+"ID: "+mDataset.get(position).getId());
        holder.placeName.setText(mDataset.get(position).getTitle());
        series = new LineGraphSeries<DataPoint>();
        holder.placeImage.addSeries(series);
        // customize a little bit viewport
        Viewport viewport = holder.placeImage.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(10);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        onResume();
    }
    protected void onResume() {

        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    }).start();

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 5d), true, 20);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public TextView placeName;
        public GraphView placeImage;


        public ViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.placeName);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            placeImage = (GraphView) itemView.findViewById(R.id.graph);
        }


    public void addItem(Data dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(itemView, getAdapterPosition());
        }
    }
        private DataPoint[] generateData() {
            int count = 30;
            DataPoint[] values = new DataPoint[count];
            for (int i=0; i<count; i++) {
                double x = i;
                double f = mRand.nextDouble()*0.15+0.3;
                double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
                DataPoint v = new DataPoint(x, y);
                values[i] = v;
            }
            return values;
        }

        double mLastRandom = 2;
        Random mRand = new Random();
        private double getRandom() {
            return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
        }
        private void addEntry() {
            // here, we choose to display max 10 points on the viewport and we scroll to end
           // series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), true, 10);
        }
    }

public interface OnItemClickListener {
    void onItemClick(View view, int position);
}

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



}