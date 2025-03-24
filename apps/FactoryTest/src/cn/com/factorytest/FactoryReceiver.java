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

	private static final String udiskfile = "khadas_test.xml";
	private static final String ageing_udiskfile0 = "khadas_test_test.xml";
	private static final String ageing_udiskfile4 = "khadas_test_4.xml";
	private static final String ageing_udiskfile8 = "khadas_test_8.xml";
	private static final String ageing_udiskfile12 = "khadas_test_12.xml";
	private static final String ageing_udiskfile24 = "khadas_test_24.xml";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			Log.d(TAG, "Factory action=" + action);
			try {
				String rec = Tools.execCommand(new String[]{"sh", "-c", "ls /mnt/media_rw/"});
				Log.e(TAG, "rec=" + rec);
				if (rec == null || rec.equals("")) {
					return;
				}

				for (int i = 0; i < rec.length(); i = i + 9) {
					String bootpath0 = "/storage/" + rec.substring(i, i + 9);
					String bootpath = bootpath0 + "/" + udiskfile;
					Log.e(TAG, "bootpath=" + bootpath);
					File file = new File(bootpath);
					if (file.exists() && file.isFile()) {
						MainActivity.udisk_backup = bootpath0;
						Log.e(TAG, "MainActivity.udisk_backup=" + MainActivity.udisk_backup);
						goto_factorytest(context, bootpath);
						return;
					}
					else if (is_file_exit(context, bootpath0, ageing_udiskfile4, 4)) {}
					else if (is_file_exit(context, bootpath0, ageing_udiskfile8, 8)) {}
					else if (is_file_exit(context, bootpath0, ageing_udiskfile12, 12)) {}
					else if (is_file_exit(context, bootpath0, ageing_udiskfile24, 24)) {}
					else if (is_file_exit(context, bootpath0, ageing_udiskfile0, 1)) {}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		Log.d(TAG, "Factory action=" + action);
		Uri uri = intent.getData();

		if (uri.getScheme().equals("file")) {
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

			String fullpath = path + "/" + udiskfile;
			Log.e(TAG, "fullpath=" + fullpath);
			File file = new File(fullpath);
			if (file.exists() && file.isFile()) {
				MainActivity.udisk_backup = path;
				Log.e(TAG, "MainActivity.udisk_backup=" + MainActivity.udisk_backup);
				goto_factorytest(context, fullpath);
			}
			else if (is_file_exit(context, path, ageing_udiskfile4, 4)) {}
			else if (is_file_exit(context, path, ageing_udiskfile8, 8)) {}
			else if (is_file_exit(context, path, ageing_udiskfile12, 12)) {}
			else if (is_file_exit(context, path, ageing_udiskfile24, 24)) {}
			else if (is_file_exit(context, path, ageing_udiskfile0, 1)) {}
		}
	}

	private boolean is_file_exit(Context context, String path, String udiskfile, int time) {
		String fullpath = path + "/" + udiskfile;
		File file = new File(fullpath);
		if(file.exists() && file.isFile()){
			try {
				MainActivity.ageing_flag = 1;
				MainActivity.ageing_time = time;
				Log.e(TAG, "hlm ageing_flag=" + MainActivity.ageing_flag + "  ageing_time=" + MainActivity.ageing_time);
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			goto_factorytest(context, fullpath);
			return true;
		}
		return false;
	}

	private void goto_config_test(Context context, String rec) {
		Pattern pattern_ageing_time = Pattern.compile("ageing_test_time=(\\d+)");
		Matcher matcher_ageing_time = pattern_ageing_time.matcher(rec);
		Pattern pattern_ageing_cpu_max = Pattern.compile("ageing_cpu_max=(\\d+)");
		Matcher matcher_ageing_cpu_max = pattern_ageing_cpu_max.matcher(rec);

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
		MainActivity.ageing_test = rec.contains("ageing_test=1");
		if (MainActivity.ageing_test) {
			MainActivity.ageing_flag = 1;
		} else {
			MainActivity.ageing_flag = 0;
		}
		if (MainActivity.ageing_test) {
			if (matcher_ageing_time.find()) {
				try {
					MainActivity.ageing_time = Integer.parseInt(matcher_ageing_time.group(1));
					Log.d(TAG, "Set ageing_time from config: " + MainActivity.ageing_time);
				} catch (NumberFormatException e) {
					MainActivity.ageing_time = 4;
					Log.e(TAG, "Invalid ageing_test_time format");
				}
			} else {
				MainActivity.ageing_time = 4;
			}
		}
		if (MainActivity.ageing_test) {
			if (matcher_ageing_cpu_max.find()) {
				try {
					int parsedValue = Integer.parseInt(matcher_ageing_cpu_max.group(1));
					if (parsedValue > 8) {
						MainActivity.ageing_cpu_max = 8;
						Log.w(TAG, "ageing_cpu_max exceeds 8, setting to 8");
					} else {
						MainActivity.ageing_cpu_max = parsedValue;
						Log.d(TAG, "Set ageing_cpu_max from config: " + MainActivity.ageing_cpu_max);
					}
				} catch (NumberFormatException e) {
					MainActivity.ageing_cpu_max = 8;
					Log.e(TAG, "Invalid ageing_cpu_max format, defaulting to 8");
				}
			} else {
				MainActivity.ageing_cpu_max = 8;
				Log.d(TAG, "ageing_cpu_max not found, defaulting to 8");
			}
		}
		Log.d(TAG, "ageing_flag=" + MainActivity.ageing_flag);
		Log.d(TAG, "ageing_time=" + MainActivity.ageing_time);
		Log.d(TAG, "ageing_cpu_max=" + MainActivity.ageing_cpu_max);

		MainActivity.led_test = rec.contains("led_test=1");
		MainActivity.mic_test = rec.contains("mic_test=1");
		MainActivity.irkey_test = rec.contains("irkey_test=1");
		MainActivity.mipi_camera_test = rec.contains("mipi_camera_test=1");
		MainActivity.mipi_lcd_test = rec.contains("mipi_lcd_test=1");
		MainActivity.tp_test = rec.contains("tp_test=1");
		MainActivity.wirte_mac = rec.contains("wirte_mac=1");
		MainActivity.reset_mcu = rec.contains("reset_mcu=1");

		Intent i = new Intent();
		i.setClassName("cn.com.factorytest", "cn.com.factorytest.MainActivity");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	private void goto_factorytest(Context context, String fullpath) {
		File file = new File(fullpath);

		if (file.exists() && file.isFile()) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				String rec = Tools.execCommand(new String[]{"sh", "-c", "cat " + fullpath});
				if (rec.contains("reboot_test=1")) {
					context.startActivity(
						new Intent(context, RebootTestActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
					);
				} else {
					if (rec.contains("test_board=Edge2")) {
						MainActivity.test_board = "Edge2";
						goto_config_test(context,rec);
					} else {
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
						MainActivity.ageing_test = true;
						if(!fullpath.contains("khadas_test_")){
							MainActivity.ageing_test = false;
							MainActivity.ageing_flag = 0;
							MainActivity.ageing_time = 0;
						}
						MainActivity.ageing_cpu_max = 8;
						Log.d(TAG, "ageing_flag=" + MainActivity.ageing_flag);
						Log.d(TAG, "ageing_time=" + MainActivity.ageing_time);
						Log.d(TAG, "ageing_cpu_max=" + MainActivity.ageing_cpu_max);

						MainActivity.led_test = true;
						MainActivity.mic_test = true;
						MainActivity.mipi_camera_test = true;
						MainActivity.mipi_lcd_test = true;
						MainActivity.tp_test = true;
						MainActivity.wirte_mac = true;
						MainActivity.reset_mcu = true;

						String io_board = "ls " + Tools.Edge2_IO;
						Log.d(TAG, "io_board : " + io_board);
						if(Tools.exec(io_board).contains("hp_inserted")){
							MainActivity.tfcard_test = true;
							MainActivity.irkey_test = true;
						} else {
							MainActivity.tfcard_test = false;
							MainActivity.irkey_test = false;
						}

						Intent i = new Intent();
						i.setClassName("cn.com.factorytest", "cn.com.factorytest.MainActivity");
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(i);
						return;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
