package cn.com.factorytest;

import java.io.File;
import java.io.IOException;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.factorytest.MainActivity;

public class FactoryReceiver extends BroadcastReceiver {
    private static final String TAG = Tools.TAG;
    private static final String[] UDISK_FILES = {
            "khadas_test.xml",
            "khadas_test_n.xml",
            "khadas_test_mcu.xml",
            "khadas_test_test.xml",
            "khadas_test_2.xml",
            "khadas_test_4.xml",
            "khadas_test_8.xml",
            "khadas_test_12.xml",
            "khadas_test_24.xml",
            "khadas_test_48.xml"
    };
    private static final int[] AGEING_TIMES = {1, 2, 4, 8, 12, 24, 48};

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "Factory action=" + action);

        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            handleBootCompleted(context);
            return;
        }

        Uri uri = intent.getData();
        if (uri != null && "file".equals(uri.getScheme())) {
            handleFileUri(context, uri);
        }
    }

    private void handleBootCompleted(Context context) {
        try {
            String rec = Tools.execCommand(new String[]{"sh", "-c", "ls /mnt/media_rw/"});
            Log.e(TAG, "rec=" + rec);
            if (rec == null || rec.isEmpty()) {
                return;
            }
            String[] directories = rec.trim().split("\\s+");
            for (String dir : directories) {
                String bootpath0 = "/storage/" + dir;
                if (checkFiles(context, bootpath0)) {
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleFileUri(Context context, Uri uri) {
        String path = uri.getPath();
        String externalStoragePath = Environment.getExternalStorageDirectory().getPath();
        String legacyPath = Environment.getLegacyExternalStorageDirectory().getPath();

        try {
            path = new File(path).getCanonicalPath();
        } catch (IOException e) {
            Log.e(TAG, "couldn't canonicalize " + path);
            return;
        }
        if (path.startsWith(legacyPath)) {
            path = externalStoragePath + path.substring(legacyPath.length());
        }

        checkFiles(context, path);
    }

    private boolean checkFiles(Context context, String path) {
        for (int i = 0; i < UDISK_FILES.length; i++) {
            String fullpath = path + "/" + UDISK_FILES[i];
            Log.e(TAG, "fullpath=" + fullpath);
            File file = new File(fullpath);
            if (file.exists() && file.isFile()) {
                MainActivity.udisk_backup = path;
                Log.e(TAG, "MainActivity.udisk_backup=" + MainActivity.udisk_backup);
                if (i == 2) {
                    MainActivity.burn_efuse_flag = false;
                }else{
                    MainActivity.burn_efuse_flag = true;
                }
                if (i == 1) {
                    try {
                        String rec = Tools.execCommand(new String[]{"sh", "-c", "cat " + fullpath});
                        MainActivity.ageing_flag = 1;
                        setAgeingCpuMax(rec);
                        setAgeingTime(rec);
                        MainActivity.ageing_test = true;
                        Log.e(TAG, "hlm ageing_flag=" + MainActivity.ageing_flag + "  ageing_time=" + MainActivity.ageing_time);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (i >= 3) {
                    return is_file_exit(context, path, UDISK_FILES[i], AGEING_TIMES[i - 3]);
                }
                goto_factorytest(context, fullpath);
                return true;
            }
        }
        return false;
    }

    private boolean is_file_exit(Context context, String path, String udiskfile, int time) {
        String fullpath = path + "/" + udiskfile;
        File file = new File(fullpath);
        if (file.exists() && file.isFile()) {
            MainActivity.ageing_flag = 1;
            MainActivity.ageing_cpu_max = 2;
            MainActivity.ageing_time = time;
            MainActivity.ageing_test = true;
            Log.e(TAG, "hlm ageing_flag=" + MainActivity.ageing_flag + "  ageing_time=" + MainActivity.ageing_time);

            goto_factorytest(context, fullpath);
            return true;
        }
        return false;
    }

    private void goto_factorytest(Context context, String fullpath) {
        File file = new File(fullpath);
        if (file.exists() && file.isFile()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                String rec = Tools.execCommand(new String[]{"sh", "-c", "cat " + fullpath});
                if (rec.contains("reboot_test=1")) {
                    context.startActivity(
                        new Intent(context, RebootTestActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                }
                if(setTestBoard(rec)){
                   setTestFlags(rec);
                }
                startMainActivity(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean setTestBoard(String rec) {
        if (rec.contains("test_board=VIM1S")) {
            MainActivity.test_board = "VIM1S";
        } else if (rec.contains("test_board=VIM2")) {
            MainActivity.test_board = "VIM2";
        } else if (rec.contains("test_board=VIM3")) {
            MainActivity.test_board = "VIM3";
        } else if (rec.contains("test_board=VIM4")) {
            MainActivity.test_board = "VIM4";
        } else if (rec.contains("test_board=Edge2")) {
            MainActivity.test_board = "Edge2";
        } else {
            MainActivity.test_board = "Edge2";
            setTestFlags(rec);
            MainActivity.usb20_test = true;
            MainActivity.usb30_test = true;
            MainActivity.spi_test = true;
            MainActivity.key_test = true;
            MainActivity.bt_test = true;
            MainActivity.wifi_test = true;

            MainActivity.mcu_test = true;
            MainActivity.hdmi_test = true;
            MainActivity.dp_test = true;
            MainActivity.fusb302_test = true;
            MainActivity.gsensor_test = true;
            MainActivity.rtc_test = true;

            MainActivity.led_test = true;
            MainActivity.mic_test = true;
            MainActivity.mipi_camera_test = false;
            MainActivity.mipi_lcd_test = false;
            MainActivity.tp_test = false;
            MainActivity.wirte_mac = true;
            MainActivity.reset_mcu = true;

            String io_board = "ls " + Tools.Edge2_IO;
            Log.d(TAG, "io_board : " + io_board);
            if(Tools.exec(io_board).contains("hp_inserted")){
                MainActivity.tfcard_test = true;
                MainActivity.irkey_test = false;
            } else {
                MainActivity.tfcard_test = false;
                MainActivity.irkey_test = false;
            }
            return false;
        }
        return true;
    }

    private void setTestFlags(String rec) {
        MainActivity.tfcard_test = rec.contains("tfcard_test=1");
        MainActivity.usb20_test = rec.contains("usb20_test=1");
        MainActivity.usb30_test = rec.contains("usb30_test=1");
        MainActivity.spi_test = rec.contains("spi_test=1");
        MainActivity.key_test = rec.contains("key_test=1");
        MainActivity.bt_test = rec.contains("bt_test=1");
        MainActivity.wifi_test = rec.contains("wifi_test=1");

        MainActivity.mcu_test = rec.contains("mcu_test=1");
        MainActivity.hdmi_test = rec.contains("hdmi_test=1");
        MainActivity.dp_test = rec.contains("dp_test=1");
        MainActivity.fusb302_test = rec.contains("fusb302_test=1");
        MainActivity.gsensor_test = rec.contains("gsensor_test=1");
        MainActivity.rtc_test = rec.contains("rtc_test=1");

        MainActivity.led_test = rec.contains("led_test=1");
        MainActivity.mic_test = rec.contains("mic_test=1");
        MainActivity.irkey_test = rec.contains("irkey_test=1");
        MainActivity.mipi_camera_test = rec.contains("mipi_camera_test=1");
        MainActivity.mipi_lcd_test = rec.contains("mipi_lcd_test=1");
        MainActivity.tp_test = rec.contains("tp_test=1");
        MainActivity.wirte_mac = rec.contains("wirte_mac=1");
        MainActivity.reset_mcu = rec.contains("reset_mcu=1");
    }

    private void setAgeingTime(String rec) {
        Pattern pattern = Pattern.compile("ageing_time=(\\d+)");
        Matcher matcher = pattern.matcher(rec);
        if (matcher.find()) {
            try {
                int time = Integer.parseInt(matcher.group(1));
                if (time > 0) {
                    MainActivity.ageing_time = time;
                    Log.e(TAG, "Set ageing_time from file: " + time);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid ageing_time value");
            }
        }
    }

    private void setAgeingCpuMax(String rec) {
        Pattern pattern = Pattern.compile("ageing_cpu_max=(\\d+)");
        Matcher matcher = pattern.matcher(rec);
        if (matcher.find()) {
            try {
                int max = Integer.parseInt(matcher.group(1));
                if (max >= 0 && max <= 8) {
                    MainActivity.ageing_cpu_max = max;
                    Log.e(TAG, "Set ageing_cpu_max from file:  " + max);
                } else {
                    Log.e(TAG, "ageing_cpu_max out of range (0-8): " + max);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid ageing_cpu_max value");
            }
        }
    }

    private void startMainActivity(Context context) {
        Intent i = new Intent();
        i.setClassName("cn.com.factorytest", "cn.com.factorytest.MainActivity");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
