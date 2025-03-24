package cn.com.factorytest;

import java.io.File;
import java.io.IOException;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.provider.Settings;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.TextView;

public class RebootTestActivity extends Activity {
    private static final String TAG = "RebootTestActivity";
    private TextView mRebootNumValue;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reboot);

        mRebootNumValue = findViewById(R.id.reboot_num_value);

        int reboot_num = Settings.System.getInt(getContentResolver(), "Khadas_reboot_test_num", 0);
        Log.d(TAG, "Current reboot count: " + reboot_num);
        mRebootNumValue.setText("Number of restarts : " + reboot_num);
        new Thread(() -> {
        try {
            Thread.sleep(2 * 1000);
            mHandler.post(() -> {
                Settings.System.putInt(getContentResolver(), "Khadas_reboot_test_num", reboot_num + 1);
            });
            Thread.sleep(10 * 1000);
            Process proc = Runtime.getRuntime().exec(new String[]{"reboot"});
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }).start();
    }

}
