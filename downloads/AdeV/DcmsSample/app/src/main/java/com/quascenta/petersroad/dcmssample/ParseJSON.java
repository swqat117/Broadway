package com.quascenta.petersroad.dcmssample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AKSHAY on 8/12/2016.
 */
public class ParseJSON {
    public  String[] dataflow;
    public  String[] status;
    public  String[] name;
    public  String[] value;
    public  String[] unit;
    public  String[] type;
    public int count;
    public static final String JSON_OBJ = "sen0";
    public static final String KEY_ID = "pid";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "price";
    public static final String KEY_TIMESTAMP= "created_at";


    private JSONObject products = null;
    private String json;

    public ParseJSON(String json){
        this.json = json;
    }
    protected void parseJSON(){
        JSONObject jsonObject  = null;
        try{
            products = new JSONObject(json);
           // System.out.println("------------------------------------------"+products);
            status = new String[products.length()];
            name =   new String[products.length()];
            value =  new String[products.length()];
            unit =   new String[products.length()];
            type =   new String [products.length()];
            for(int i=0;i<products.length();i++){
                count++;
                System.out.println(count);
                status[i] = products.optString(KEY_ID);
              //  System.out.println("--------------------------------------"+ids[i]);
                name[i] = products.optString(KEY_NAME);
                value[i] = products.optString(KEY_EMAIL);
                unit[i] = products.optString(KEY_TIMESTAMP);
                System.out.println("------------------------------------------"+name[i]);
                System.out.println("----------------------------------------------"+value[i]);
                System.out.println("--------------------------------------------------"+unit[i]);
            }
                         } catch (JSONException e) {
                e.printStackTrace();
        }
    }


}
