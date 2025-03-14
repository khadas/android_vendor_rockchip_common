package cn.com.factorytest;

import java.util.HashMap;
import android.graphics.Color;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import android.annotation.SuppressLint;
import android.graphics.*;
import android.os.Handler;
import android.util.SparseArray;
import android.view.*;
import java.util.Locale;
import android.os.Build;
import android.os.Build.VERSION_CODES;

import android.util.AttributeSet;
import android.widget.*;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.List;
import android.util.DisplayMetrics;

public class TouchView extends View {

    private final String TAG = "TouchView";

    private Activity mActivity;
    private int mScreenHeight;
    private int mScreenWidth;
    private final int mGridHeight = 70;
    private final int mGridWidth = 70;
    private int mRowCount;
    private int mColCount;
    private int mRectCount;
    private int mIndex = 0;
    private Rect[] rect;
    private boolean[] isTouch;
    private int[] coordinateX;
    private int[] coordinateY;

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (Activity) context;
        init();
    }

    private void init() {
        getScreenHeightAndWidth();

        mRowCount = mScreenWidth / mGridWidth + 1;
        mColCount = mScreenHeight / mGridHeight + 1;

        Log.v(TAG, "mRowCount = " + mRowCount);
        Log.v(TAG, "mColCount = " + mColCount);

        mRectCount = (mRowCount - 1) * (mColCount - 1);
        Log.v(TAG, "mRectCount = " + mRectCount);
		
        coordinateX = new int[mRowCount];
        coordinateY = new int[mColCount];

        rect = new Rect[mRectCount];
        isTouch = new boolean[mRectCount];

        for(int i = 0; i < mRectCount; i++){
            isTouch[i] = false;
        }

        for (int i = 0; i < mRowCount; i++) {
            coordinateX[i] = i * mGridWidth;
            Log.v(TAG, "coordinateX[" + i + "] = " + coordinateX[i]);
        }

        for (int i = 0; i < mColCount; i++) {
            coordinateY[i] = i * mGridHeight;
            Log.v(TAG, "coordinateY[" + i + "] = " + coordinateY[i]);
        }

        for (int i = 0; i < mRowCount - 1; i++) {
            for (int j = 0; j < mColCount - 1; j++) {
                rect[mIndex++] = new Rect(coordinateX[i], coordinateY[j], coordinateX[i + 1], coordinateY[j + 1]);
                Log.v(TAG, "coordinateX[" + i + "] = " + coordinateX[i]);
                Log.v(TAG, "coordinateY[" + j + "] = " + coordinateY[j]);
                Log.v(TAG, "coordinateX[" + (i+1) + "] = " + coordinateX[i+1]);
                Log.v(TAG, "coordinateY[" + (j+1) + "] = " + coordinateY[j+1]);
            }
        }
    }

    private void getScreenHeightAndWidth() {
        DisplayMetrics displayMestrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMestrics);
        mScreenHeight = displayMestrics.heightPixels;
        mScreenWidth = displayMestrics.widthPixels;
        Log.v(TAG, "mScreenHeight = " + mScreenHeight);
        Log.v(TAG, "mScreenWidth = " + mScreenWidth);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v(TAG, "onDraw");	
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i < mRectCount; i++){
            //Log.v(TAG, "index="+i);
            canvas.drawRect(rect[i], paint);
        }

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < mRectCount; i++){
            if(isTouch[i] == true){
                canvas.drawRect(rect[i], paint);
            }
        }
    }

    private boolean allRectChange(){
        boolean isAllRectChange = false;
        int index = 0;
        for(int i = 0; i < mRectCount; i++){
            index = i;
            if(isTouch[i]){
                continue;
            }
            else{
                break;
            }
        }
        Log.v(TAG,"index = "+index);
        if(index + 1 == mRectCount){
            isAllRectChange = true;
            Log.v(TAG,"isAllRectChange = true");
        }

        return isAllRectChange;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(allRectChange()){
            mActivity.finish();
        }
        int touchX = (int)event.getX();
        int touchY = (int)event.getY();
        if(touchX == mScreenWidth){
            touchX = touchX -1;
        }
        if(touchY == mScreenHeight){
            touchY = touchY -1;
        }
        Log.v(TAG, "touchX = "+touchX);
        Log.v(TAG, "touchY = "+touchY);
        touchWhere(touchX, touchY);
        invalidate();
        return true;
    }

    private void touchWhere(int touchX, int touchY){
        int indexX = touchX / mGridWidth;
        int indexY = touchY / mGridHeight;
        Log.v(TAG, "indexX = "+indexX);
        Log.v(TAG, "indexY = "+indexY);
        isTouch[indexX * (mColCount - 1) + indexY] = true;
        Log.v(TAG, indexX * (mColCount - 1) + indexY+"");
    }

}
