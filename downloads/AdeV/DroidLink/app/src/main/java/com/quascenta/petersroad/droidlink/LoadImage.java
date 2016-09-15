package com.quascenta.petersroad.droidlink;

/**
 * Created by AKSHAY on 9/6/2016.
 */
public class LoadImage
{
    private String title;
    private int imageid;

    public LoadImage(String title, int imageid){
        this.title = title;
        this.imageid = imageid;
    }
    public LoadImage(){};
    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
