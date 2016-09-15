package com.quascenta.petersroad.droidlink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

/**
 * Created by AKSHAY on 9/9/2016.
 */
public class GraphAdapter extends BaseAdapter {
    Context context;
    private LineGraphSeries<DataPoint> SeriesArray;
    private PointsGraphSeries<DataPoint> PointsArray;
    LayoutInflater inflater;


    public GraphAdapter(Context context,LineGraphSeries<DataPoint> series,PointsGraphSeries<DataPoint> points){
        this.context = context;
        this.SeriesArray = series;
        this.PointsArray = points;
        inflater = LayoutInflater.from(context);
    }
    public GraphAdapter(){}
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_graph,null);
        GraphView graph1 = (GraphView)view.findViewById(R.id.g1);

            graph1.addSeries(SeriesArray);
            graph1.addSeries(PointsArray);


        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMaxX(60);
        graph1.getViewport().setMinX(0);
        return view;
    }
}
