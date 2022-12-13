package com.khadas.ksettings;

import java.io.IOException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.util.Log;


public class LedsReceiver extends BroadcastReceiver{
	private static final String TAG = "LedsReceiver";
	private String value;
	private int val ;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Log.e(TAG, "hlm5 action " + action);

		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			//System on muc led status
			value = SystemProperties.get("persist.sys.mcu_leds_on_modes_value");
			if(value.equals("")){
				value = "0";
			}
			Log.e(TAG, "hlm mcu_leds_on_modes_value val " + val);
			try {
				ComApi.execCommand(new String[]{"sh", "-c", "echo 0x230"+ value +" > /sys/class/mcu/mculed"});
			} catch (IOException e) {
				e.printStackTrace();
			}

			value = SystemProperties.get("persist.sys.mcu_red_on_bl_value");
			if(value.equals("")){
				value = "255";
			}
			val = Integer.valueOf(value, 10);
			Log.e(TAG, "hlm mcu_red_on_bl_value val " + val);
			try {
				if(val>=0 && val <=15){
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x250"+ val +" > /sys/class/mcu/mculed"});
				}else{
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x25"+ val +" > /sys/class/mcu/mculed"});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			value = SystemProperties.get("persist.sys.mcu_green_on_bl_value");
			if(value.equals("")){
				value = "255";
			}
			val = Integer.valueOf(value, 10);
			try {
				if(val>=0 && val <=15){
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x260"+ val +" > /sys/class/mcu/mculed"});
				}else{
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x26"+ val +" > /sys/class/mcu/mculed"});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			value = SystemProperties.get("persist.sys.mcu_blue_on_bl_value");
			if(value.equals("")){
				value = "255";
			}
			val = Integer.valueOf(value, 10);
			try {
				if(val>=0 && val <=15){
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x270"+ val +" > /sys/class/mcu/mculed"});
				}else{
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x27"+ val +" > /sys/class/mcu/mculed"});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//System Shutdown muc led status
			value = SystemProperties.get("persist.sys.mcu_leds_off_modes_value");
			if(value.equals("")){
				value = "1";
			}
			try {
				ComApi.execCommand(new String[]{"sh", "-c", "echo 0x240"+ value +" > /sys/class/mcu/mculed"});
			} catch (IOException e) {
				e.printStackTrace();
			}
			value = SystemProperties.get("persist.sys.mcu_red_off_bl_value");
			if(value.equals("")){
				value = "255";
			}
			val = Integer.valueOf(value, 10);
			try {
				if(val>=0 && val <=15){
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x280"+ val +" > /sys/class/mcu/mculed"});
				}else{
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x28"+ val +" > /sys/class/mcu/mculed"});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			value = SystemProperties.get("persist.sys.mcu_green_off_bl_value");
			if(value.equals("")){
				value = "0";
			}
			val = Integer.valueOf(value, 10);
			try {
				if(val>=0 && val <=15){
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x290"+ val +" > /sys/class/mcu/mculed"});
				}else{
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x29"+ val +" > /sys/class/mcu/mculed"});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			value = SystemProperties.get("persist.sys.mcu_blue_off_bl_value");
			if(value.equals("")){
				value = "0";
			}
			val = Integer.valueOf(value, 10);
			try {
				if(val>=0 && val <=15){
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A0"+ val +" > /sys/class/mcu/mculed"});
				}else{
					ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A"+ val +" > /sys/class/mcu/mculed"});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//sys led
			value = SystemProperties.get("persist.sys.Red_led_control");
			if(value.equals("")){
				value = "3";
			}
			val = Integer.valueOf(value, 16);
			switch(val){
				case 0:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/red_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 1:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo default-on > /sys/class/leds/red_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 2:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo timer > /sys/class/leds/red_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo heartbeat > /sys/class/leds/red_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
			}

			value = SystemProperties.get("persist.sys.Green_led_control");
			if(value.equals("")){
				value = "3";
			}
			val = Integer.valueOf(value, 16);
			switch(val){
				case 0:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/green_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 1:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo default-on > /sys/class/leds/green_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 2:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo timer > /sys/class/leds/green_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo heartbeat > /sys/class/leds/green_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
			}

			value = SystemProperties.get("persist.sys.Blue_led_control");
			if(value.equals("")){
				value = "3";
			}
			val = Integer.valueOf(value, 16);
			Log.e(TAG, "hlm persist.sys.Blue_led_control val " + val);
			switch(val){
				case 0:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/blue_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 1:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo default-on > /sys/class/leds/blue_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 2:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo timer > /sys/class/leds/blue_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						ComApi.execCommand(new String[]{"sh", "-c", "echo heartbeat > /sys/class/leds/blue_led/trigger"});
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
			}
		}
	}
}
