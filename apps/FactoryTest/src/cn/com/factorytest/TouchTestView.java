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

public class TouchTestView extends View {

    private static final int[] TOUCH_COLORS = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN,
            Color.MAGENTA, Color.GRAY, Color.DKGRAY, Color.LTGRAY, Color.BLACK
    };
    
    private List<PointF> touchPoints = new ArrayList<>();
    private List<PointF> savedPoints = new ArrayList<>();
    private Path trackPath = new Path();
    private Paint paint = new Paint();
    private Paint textPaint = new Paint();
    private boolean isTenPointsTested = false;

    private Path freeDrawPath = new Path();
    private Paint freeDrawPaint = new Paint();

    private boolean isTrackCovered = false;
    private List<PointF> trackSamplingPoints = new ArrayList<>();
    private static final float SAMPLE_INTERVAL = 10f;

    @Override 
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createTrackPath();
    }

    public TouchTestView(Context context) {
        super(context);
        init();
    }

    public TouchTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
    }

    private void init() {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20f);

        freeDrawPaint.setAntiAlias(true);
        freeDrawPaint.setColor(Color.RED);
        freeDrawPaint.setStyle(Paint.Style.STROKE);
        freeDrawPaint.setStrokeWidth(40f);
        freeDrawPaint.setStrokeCap(Paint.Cap.ROUND);
        freeDrawPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.parseColor("#4CAF50"));
        canvas.drawPath(trackPath, paint);

        canvas.drawPath(freeDrawPath, freeDrawPaint);

        if (isTenPointsTested && !savedPoints.isEmpty()) {
            for (int i = 0; i < savedPoints.size(); i++) {
                PointF point = savedPoints.get(i);
                paint.setColor(TOUCH_COLORS[i % 10]);
                canvas.drawCircle(point.x, point.y, 50, paint);

                canvas.drawText(
                    String.valueOf(i + 1),
                    point.x,
                    point.y + textPaint.getTextSize()/3,
                    textPaint
                );
            }
        } else {
            for (int i = 0; i < touchPoints.size(); i++) {
                PointF point = touchPoints.get(i);
                paint.setColor(TOUCH_COLORS[i % 10]);
                canvas.drawCircle(point.x, point.y, 50, paint);

                canvas.drawText(
                    String.valueOf(i + 1),
                    point.x,
                    point.y + textPaint.getTextSize()/3,
                    textPaint
                );
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event);
        return true;
    }

    private void handleTouch(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerCount = event.getPointerCount();

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            return;
        }
    
        if (pointerCount == 10) {
            touchPoints.clear();
            for (int i = 0; i < 10; i++) {
                touchPoints.add(new PointF(event.getX(i), event.getY(i)));
            }
            savedPoints = new ArrayList<>(touchPoints);
            isTenPointsTested = true;
        } else {
            if (isTenPointsTested && pointerCount < 10) {
                resetTest();
                ((TouchTestActivity)getContext()).hideResultButtons();
            } else if (!isTenPointsTested) {
                if (pointerCount == 1) {
                    float x = event.getX();
                    float y = event.getY();

                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            freeDrawPath.moveTo(x, y);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            freeDrawPath.lineTo(x, y);
                            checkTrackCoverage();
                            break;
                    }
                    invalidate();
                }

                touchPoints.clear();
                for (int i = 0; i < event.getPointerCount(); i++) {
                    touchPoints.add(new PointF(event.getX(i), event.getY(i)));
                }
            }
        }
        invalidate();
    }

    private void createTrackPath() {
        trackPath.reset();
        float width = getWidth();
        float height = getHeight();

        //边框线
        trackPath.moveTo(0, 0);
        trackPath.lineTo(width, 0);
        trackPath.moveTo(width, 0);
        trackPath.lineTo(width, height);
        trackPath.moveTo(width, height);
        trackPath.lineTo(0, height);
        trackPath.moveTo(0, height);
        trackPath.lineTo(0, 0);
        //X交叉线左上角到右下角
        trackPath.moveTo(0, 0);
        trackPath.lineTo(width, height);
        //交叉线右上角到左下角
        trackPath.moveTo(width, 0);
        trackPath.lineTo(0, height);

        trackSamplingPoints.clear();
        PathMeasure pm = new PathMeasure(trackPath, false);
            do {
            float length = pm.getLength();
            for (float distance = 0; distance <= length; distance += SAMPLE_INTERVAL) {
                float[] pos = new float[2];
                pm.getPosTan(distance, pos, null);
                trackSamplingPoints.add(new PointF(pos[0], pos[1]));
            }
        } while (pm.nextContour());
    }

    private void checkTrackCoverage() {
        if (isTrackCovered) return;

        Path strokedPath = new Path();
        new Paint(freeDrawPaint).getFillPath(freeDrawPath, strokedPath);

        Region drawingRegion = new Region();
        drawingRegion.setPath(strokedPath, new Region(0, 0, getWidth(), getHeight()));

        boolean allCovered = true;
        for (PointF point : trackSamplingPoints) {
            Region pointArea = new Region(
                (int)(point.x - 30f), (int)(point.y - 30f),
                (int)(point.x + 30f), (int)(point.y + 30f)
            );

            Region overlap = new Region();
            overlap.set(pointArea);
            overlap.op(drawingRegion, Region.Op.INTERSECT);
        
            if (overlap.isEmpty()) {
                allCovered = false;
                break;
            }
        }

        if (allCovered) {
            isTrackCovered = true;
            ((TouchTestActivity) getContext()).showResultButtons();
            invalidate();
        }
    }

    public void resetTest() {
        touchPoints.clear();
        savedPoints.clear();
        isTenPointsTested = false;
        freeDrawPath.reset();
        isTrackCovered = false;
        invalidate();
    }
}
