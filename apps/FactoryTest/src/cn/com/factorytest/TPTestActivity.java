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

public class TPTestActivity extends Activity {

    private TouchView touchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendBroadcast(new Intent("com.android.hide_upper_bar"));
        setContentView(R.layout.tp_test);

        touchView = findViewById(R.id.touch_view); 
    }

}
