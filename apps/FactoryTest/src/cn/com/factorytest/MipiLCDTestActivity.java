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


public class MipiLCDTestActivity extends Activity {
    private final int[] colors = {
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.BLACK,
            Color.WHITE
    };
    private int currentColorIndex = 0;
    private Context mContext;
    private boolean isTestCompleted = false;
    private Button success, fail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.lcd_mipi);
        mContext = this;

        updateBackgroundColor();

        success = (Button) findViewById(R.id.btn_success);
        success.setVisibility(View.GONE);
        fail = (Button) findViewById(R.id.btn_fail);
        fail.setVisibility(View.GONE);

        findViewById(R.id.content).setOnClickListener(outerView -> {
            if (currentColorIndex < colors.length - 1) {
                currentColorIndex++;
                updateBackgroundColor();
            } else {
                isTestCompleted = true;

                success.setVisibility(View.VISIBLE);
                fail.setVisibility(View.VISIBLE);

                success.setOnClickListener(successView -> {
                    Settings.System.putInt(mContext.getContentResolver(), "Khadas_mipi_lcd_test", 1);
                    finish();
                });
                fail.setOnClickListener(failView -> {
                    Settings.System.putInt(mContext.getContentResolver(), "Khadas_mipi_lcd_test", 0);
                    finish();
                });
            }
        });
    }

    private void updateBackgroundColor() {
        getWindow().getDecorView().setBackgroundColor(colors[currentColorIndex]);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            updateBackgroundColor();
        }
    }
}
