package com.quascenta.petersroad.droidlink;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AKSHAY on 9/6/2016.
 */
public class EventLoggerAdapter extends RecyclerView.Adapter<EventLoggerAdapter.ViewHolder> {

    Context context;
    private static String LOG_TAG = "EventLogViewAdapter";
    private ArrayList<EventData> mDataset;
   // private static MyClickListener myClickListener;
// 2
public EventLoggerAdapter(Context context,ArrayList<EventData> android) {
    this.mDataset = android;
    this.context = context;
    Log.i(LOG_TAG, "Adding Listener");
        }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_eventslog_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        System.out.println("-------------------------"+mDataset.get(position).getImage_title());
        holder.placeName.setText(mDataset.get(position).getImage_title());
        Picasso.with(context).load(mDataset.get(position).getImage_id()).resize(400,200).into(holder.placeImage);

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // 3
public class ViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;

    public ViewHolder(View itemView) {
        super(itemView);
        placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
        placeName = (TextView) itemView.findViewById(R.id.placeName);
        placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
        placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
    }
        public void addItem(EventData dataObj, int index) {
            mDataset.add(dataObj);
            notifyItemInserted(index);
        }

        public void deleteItem(int index) {
            mDataset.remove(index);
            notifyItemRemoved(index);
        }
}
}
