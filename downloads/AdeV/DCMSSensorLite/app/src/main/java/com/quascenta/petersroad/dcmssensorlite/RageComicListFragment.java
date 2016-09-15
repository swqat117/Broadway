/*
 * Copyright (c) 2015 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.quascenta.petersroad.dcmssensorlite;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class RageComicListFragment extends Fragment {
  private OnRageComicSelected mlistener;
  private int[] mImageResIds;
  private String[] mSensor;
  private String[] mvalue1;
  private String[] alert;


  public static RageComicListFragment newInstance() {
    return new RageComicListFragment();
  }

  public RageComicListFragment() {
    // Required empty public constructor
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
   final View view = inflater.inflate(R.layout.fragment_rage_comic_list,container,false);
    final Activity activity =getActivity();
    final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new GridLayoutManager(activity, 1));
    recyclerView.setAdapter(new RageComicAdapter(activity));
    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    final Resources resources = context.getResources();
  //Adding test data to check the layout
    mSensor = resources.getStringArray(R.array.names);

    final TypedArray typedArray = resources.obtainTypedArray(R.array.images);
    final int imageCount = mSensor.length;
    mImageResIds = new int[imageCount];
    for(int i =0;i<imageCount;i++){
      mImageResIds[i] = typedArray.getResourceId(i,0);
          }
    typedArray.recycle();
  if(context instanceof OnRageComicSelected) {
    mlistener = (OnRageComicSelected) context;
  }
    else{
    throw new ClassCastException(context.toString() + "must implement OnRageSelected");
  }
  }


  class RageComicAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mLayoutInflater;

    public RageComicAdapter(Context context) {
      mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      return new ViewHolder(mLayoutInflater
          .inflate(R.layout.sensor_list_fragment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
      final int imageResId = mImageResIds[position];
      final String name = mSensor[position];


      viewHolder.setData(imageResId, name);
      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mlistener.onRageComicSelected(imageResId,name);
        }
      });
    }

    @Override
    public int getItemCount() {
      return mSensor.length;
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    // Views
    private ImageButton mImageView;
    private TextView mNameTextView;
    private RadioButton radioButton1,radioButton2,radioButton3,radioButton4;

    private ViewHolder(View itemView) {
      super(itemView);

      // Get references to image and name.
      mImageView = (ImageButton) itemView.findViewById(R.id.imageButton);
      mNameTextView = (TextView) itemView.findViewById(R.id.textView);
      radioButton1 = (RadioButton)itemView.findViewById(R.id.s1);
      radioButton2 = (RadioButton)itemView.findViewById(R.id.s2);
      radioButton3 = (RadioButton)itemView.findViewById(R.id.s3);
      radioButton4 = (RadioButton)itemView.findViewById(R.id.s4);


    }

    private void setData(int imageResId, String name) {
      mImageView.setImageResource(imageResId);
      mNameTextView.setText(name);
    }
  }
  public interface OnRageComicSelected{
    void onRageComicSelected(int ImageResid, String name);


  }

}
