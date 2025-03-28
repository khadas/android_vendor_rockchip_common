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
import java.util.Map;

public class TouchTestActivity extends Activity {

    private TouchTestView touchTestView;
    private LinearLayout resultButtons;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.touchtest);

        mContext = this;

        touchTestView = findViewById(R.id.touchView);
        resultButtons = findViewById(R.id.resultButtons);

        Button btnSuccess = findViewById(R.id.btn_success);
        Button btnFail = findViewById(R.id.btn_fail);

        btnSuccess.setOnClickListener(v -> handleTestResult(true));
        btnFail.setOnClickListener(v -> handleTestResult(false));
    }

    public void showResultButtons() {
        runOnUiThread(() -> resultButtons.setVisibility(View.VISIBLE));
    }

    public void hideResultButtons() {
        runOnUiThread(() -> resultButtons.setVisibility(View.GONE));
    }

    private void handleTestResult(boolean success) {
        if (success) {
            Settings.System.putInt(mContext.getContentResolver(), "Khadas_tp_test", 1);
            finish();
        } else {
            Settings.System.putInt(mContext.getContentResolver(), "Khadas_tp_test", 0);
            finish();
        }
        touchTestView.resetTest();
    }
}
