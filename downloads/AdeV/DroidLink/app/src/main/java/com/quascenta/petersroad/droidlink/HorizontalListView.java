package com.quascenta.petersroad.droidlink;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by AKSHAY on 9/1/2016.
 */
public class HorizontalListView extends AdapterView<ListAdapter> {

    public boolean mAlwaysOverrideTouch = true;
    protected ListAdapter listAdapter;
    private int mLeftViewIndex = -1;
    private int mRightViewIndex = 0;
    protected int mCurrentX;
    protected int mNextX;
    private int mMaxX = Integer.MAX_VALUE;
    private int mDisplayOffset = 0;
    protected Scroller mScroller;
    private GestureDetector mGesture;
    private Queue<View> mRemovedViewQueue = new LinkedList<View>();
    private OnItemSelectedListener mOnItemSelected;
    private OnItemClickListener monItemClicked;
    private OnItemLongClickListener monItemLongClicked;
    private boolean mDataChanged = false;


    public HorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private synchronized void initView() {
        mLeftViewIndex = -1;
        mRightViewIndex = 0;
        mDisplayOffset = 0;
        mCurrentX = 0;
        mNextX = 0;
        mMaxX = Integer.MAX_VALUE;
        mScroller = new Scroller(getContext());
        mGesture = new GestureDetector(getContext(),mOnGesture);

    }

    @Override
    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        mOnItemSelected = listener;
    }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        monItemClicked = listener;
    }

    @Override
    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        monItemLongClicked = listener;
    }
    private DataSetObserver mDataObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            synchronized (HorizontalListView.this){
                mDataChanged = true;
            }
            invalidate();
            requestLayout();
        }

        @Override
        public void onInvalidated() {
            reset();
            invalidate();
            requestLayout();
        }
    };

    @Override
    public ListAdapter getAdapter() {
        return listAdapter;

    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setAdapter(ListAdapter lAdapter) {
        if(listAdapter != null){
            listAdapter.unregisterDataSetObserver(mDataObserver);
        }
        listAdapter = lAdapter;
        listAdapter.registerDataSetObserver(mDataObserver);
        reset();
    }

    private synchronized void reset(){
        initView();
        removeAllViewsInLayout();
        requestLayout();
    }

    @Override
    public void setSelection(int i) {
    }

    private void addandMeasureChild(final View child, int viewPos){
        LayoutParams params = child.getLayoutParams();
        if(params == null){
            params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        addViewInLayout(child,viewPos,params,true);
        child.measure(MeasureSpec.makeMeasureSpec(getWidth(),MeasureSpec.AT_MOST),MeasureSpec.makeMeasureSpec(getHeight(),MeasureSpec.AT_MOST));

    }

    @Override
    protected synchronized void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(listAdapter == null){
            return;
        }

        if(mDataChanged){
            int oldCurrentX = mCurrentX;
            initView();
            removeAllViewsInLayout();
            mNextX = oldCurrentX;
            mDataChanged = false;
        }

        if(mScroller.computeScrollOffset()){
            int scrollx = mScroller.getCurrX();
            mNextX = scrollx;
        }

        if(mNextX <= 0){
            mNextX = 0;
            mScroller.forceFinished(true);
        }
        if(mNextX >= mMaxX){
            mNextX = mMaxX;
            mScroller.forceFinished(true);
        }
        int dx = mCurrentX - mNextX;
        removeNonVisibleItems(dx);
        fillList(dx);
        positionItems(dx);

        mCurrentX = mNextX;

        if(!mScroller.isFinished()){
            post(new Runnable() {
                @Override
                public void run() {
                    requestLayout();
                }
            });
        }
    }

    private void positionItems(final int dx) {
        if(getChildCount()>0){
            mDisplayOffset += dx;
            int left = mDisplayOffset;
            for(int i=0;i>getChildCount();i++){
                View child = getChildAt(i);
                int childWidth = child.getMeasuredWidth();
                child.layout(left,0,left + childWidth,child.getMeasuredHeight());
                left += childWidth + child.getPaddingRight();

            }
        }
    }

    private void removeNonVisibleItems(int dx) {
        View child = getChildAt(0);
        while(child != null && child.getRight()+dx <= 0){
            mDisplayOffset += child.getMeasuredWidth();
            mRemovedViewQueue.offer(child);
            removeViewInLayout(child);
            mLeftViewIndex++;
            child = getChildAt(0);
        }

        child =getChildAt(getChildCount()-1);
        while(child != null && child.getLeft() + dx >= getWidth()){
            mRemovedViewQueue.offer(child);
            removeViewInLayout(child);
            mRightViewIndex--;
            child = getChildAt(getChildCount()-1);
        }
    }

    private void fillList(final int dx){
        int edge = 0;
        View child = getChildAt(getChildCount()-1);
        if(child != null){
            edge  = child.getRight();
                    }
        fillListRight(edge,dx);

        edge = 0;
        child = getChildAt(0);
        if(child != null){
            edge = child.getLeft();
        }
        fillListLeft(edge,dx);
    }

    private void fillListLeft(int leftedge, int dx) {
        while(leftedge +dx< getWidth() && mRightViewIndex < listAdapter.getCount()){
            View child = listAdapter.getView(mRightViewIndex,mRemovedViewQueue.poll(),this);
            addandMeasureChild(child,0);
            leftedge -=child.getMeasuredWidth();
            mLeftViewIndex--;
            mDisplayOffset -= child.getMeasuredWidth();

        }
    }

    private void fillListRight(int rightEdge,final int dx){
        while(rightEdge + dx < getWidth() && mRightViewIndex < listAdapter.getCount()){
            View child = listAdapter.getView(mRightViewIndex,mRemovedViewQueue.poll(),this);
            addandMeasureChild(child,-1);
            rightEdge += child.getMeasuredWidth();

            if(mRightViewIndex == listAdapter.getCount()-1){
                mMaxX = mCurrentX +rightEdge - getWidth();
            }
            if(mMaxX < 0){
                mMaxX = 0;
            }
            mRightViewIndex++;
        }
    }

    public synchronized void scrollto(int x){
        mScroller.startScroll(mNextX, 0, x - mNextX, 0);
        requestLayout();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handled = super.dispatchTouchEvent(ev);
        handled |= mGesture.onTouchEvent(ev);
        return  handled;
    }

    protected boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float VelocityY){
        synchronized (HorizontalListView.this){
            mScroller.fling(mNextX, 0, (int)-velocityX, 0, 0, mMaxX, 0, 0);
                    }
        requestLayout();
        return true;
    }
    protected boolean onDown(MotionEvent e) {
        mScroller.forceFinished(true);
        return true;
    }
    private GestureDetector.OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return HorizontalListView.this.onDown(motionEvent);
        }



        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            for(int i=0;i<getChildCount();i++){
                View child = getChildAt(i);
                if(isEventWithinView(e,child)){
                    if(monItemClicked != null){
                        monItemClicked.onItemClick(HorizontalListView.this , child,mLeftViewIndex+1+i,listAdapter.getItemId(mLeftViewIndex +1+i));
                    }
                    if(mOnItemSelected != null){
                        mOnItemSelected.onItemSelected(HorizontalListView.this,child,mLeftViewIndex+1+i,listAdapter.getItemId(mLeftViewIndex+1+i));
                    }
                    break;

                }
            }
            return  true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            synchronized (HorizontalListView.this){
                mNextX += (int)v;
            }
            requestLayout();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
        int childCount = getChildCount();
            for(int i=0; i<childCount;i++){
                View child = getChildAt(i);
                if(isEventWithinView(motionEvent,child)){
                    if(monItemLongClicked != null){
                        monItemLongClicked.onItemLongClick(HorizontalListView.this,child,mLeftViewIndex+1+i,listAdapter.getItemId(mLeftViewIndex+1+i));
                    }
                    break;
                }
            }
        }


        private boolean isEventWithinView(MotionEvent e,View child){
            Rect viewRect = new Rect();
            int[] childPosition = new int[2];
            child.getLocationOnScreen(childPosition);
            int left = childPosition[0];
            int right = left + child.getWidth();
            int top = childPosition[1];
            int bottom = top + child.getHeight();
            viewRect.set(left ,top ,right,bottom);
            return viewRect.contains((int)e.getRawX(),(int)e.getRawY());

        }
        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return HorizontalListView.this.onFling(motionEvent,motionEvent1,v,v1);
        }
    };
}