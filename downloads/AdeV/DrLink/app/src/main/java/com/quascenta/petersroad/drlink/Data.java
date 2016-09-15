package com.quascenta.petersroad.drlink;

import com.jjoe64.graphview.GraphView;

/**
 * Created by AKSHAY on 9/7/2016.
 */
public class Data {
    private String title;
    private String id;
    private GraphView graphView;

    Data(String text1, String text2,GraphView graphView){
        this.title = text1;
        this.id = text2;
        this.graphView = graphView;
    }
    Data(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GraphView getGraphView() {
        return graphView;
    }

    public void setGraphView(GraphView graphView) {
        this.graphView = graphView;
    }
}

