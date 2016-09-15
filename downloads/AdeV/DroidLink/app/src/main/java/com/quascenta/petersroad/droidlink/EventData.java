package com.quascenta.petersroad.droidlink;

/**
 * Created by AKSHAY on 9/6/2016.
 */
public class EventData {
    private int image_id;
    private String image_title;

        public EventData(int image_id,String title){
            this.image_id = image_id;
            this.image_title =  title;
        }
        public EventData(){}

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }
}
