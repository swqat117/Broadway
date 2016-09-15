package com.quascenta.petersroad.droidlink;

import android.content.Context;
import android.graphics.Typeface;

import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AKSHAY on 9/6/2016.
 */
public class DeviceListRecyclerView extends RecyclerView
        .Adapter<DeviceListRecyclerView
        .DataObjectHolder> implements View.OnClickListener {
    Context context;
    OnItemClickListener mItemClickListener;
    private static String LOG_TAG = "DeviceListRecyclerViewAdapter";
    private ArrayList<Data> mDataset;

    public DeviceListRecyclerView(ArrayList<Data> myDataset) {
        mDataset = myDataset;
    }
    public DeviceListRecyclerView(Context context, ArrayList<Data> android) {
        this.mDataset = android;
        this.context = context;
    }


    @Override
    public void onClick(View v) {
        System.out.println("added1");
    }

    public void setOnItemClickListener(final OnItemClickListener mqItemClickListener) {
        this.mItemClickListener = mqItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cardview, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.Device_Name.setText(mDataset.get(position).getmText1());
        holder.Device_Id.setText(mDataset.get(position).getmText2());
        //load image and bind it to the adapter
        System.out.println(mDataset.get(position).getmText1());
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
    public int getItemCount() {
        return mDataset.size();
    }








    public  class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView Device_Name, Device_Id;


        public DataObjectHolder(View itemView) {
            super(itemView);
            Device_Name = (TextView) itemView.findViewById(R.id.textView3);

            Device_Id = (TextView) itemView.findViewById(R.id.textView4);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                System.out.println("added 2");
                mItemClickListener.onItemClick(itemView, getAdapterPosition());
            }
        }



    }}

