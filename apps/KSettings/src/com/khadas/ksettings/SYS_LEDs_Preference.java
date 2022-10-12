package com.khadas.ksettings;

import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;


public class SYS_LEDs_Preference extends PreferenceActivity implements Preference.OnPreferenceClickListener {
	
    private ListPreference SYS_Always_On_Off_Preference;
    private ListPreference SYS_Breath_mode_Preference;
    private ListPreference SYS_HeartBeat_mode_Preference;

    private static final String SYS_Always_On_Off_KEY = "SYS_Always_On_Off_KEY";
    private static final String SYS_Breath_mode_KEY = "SYS_Breath_mode_KEY";
    private static final String SYS_HeartBeat_mode_KEY= "SYS_HeartBeat_mode_KEY";
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.sys_leds_control);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        SYS_Always_On_Off_Preference = (ListPreference) findPreference(SYS_Always_On_Off_KEY);
        bindPreferenceSummaryToValue(SYS_Always_On_Off_Preference);
        SYS_Breath_mode_Preference = (ListPreference) findPreference(SYS_Breath_mode_KEY);
        bindPreferenceSummaryToValue(SYS_Breath_mode_Preference);
        SYS_HeartBeat_mode_Preference = (ListPreference) findPreference(SYS_HeartBeat_mode_KEY);
        bindPreferenceSummaryToValue(SYS_HeartBeat_mode_Preference);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * bindPreferenceSummaryToValue 拷贝至as自动生成的preferences的代码，用于绑定显示实时值
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            String key = preference.getKey();
            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                // Set the summary to reflect the new value.
                preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

                if (SYS_Always_On_Off_KEY.equals(key)){
                    //Log.d("wjh","1===" + index);
                    switch(index){
                        case 0:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/blue_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo default-on > /sys/class/leds/red_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/blue_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo default-on > /sys/class/leds/green_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo default-on > /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
					    case 4:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo default-on > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo default-on > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo default-on > /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }

                }
                else if (SYS_Breath_mode_KEY.equals(key)){
                    //Log.d("wjh","1===" + index);
                    switch(index){
                        case 0:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo timer > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo timer > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off> /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo timer > /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo timer > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo timer > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo timer > /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }

                }else if(SYS_HeartBeat_mode_KEY.equals(key)){
                    //Log.d("wjh","1===" + index);
                    switch(index){
                        case 0:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo heartbeat > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo heartbeat > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo off > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo heartbeat > /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo heartbeat > /sys/class/leds/red_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo heartbeat > /sys/class/leds/green_led/trigger"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo heartbeat > /sys/class/leds/blue_led/trigger"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }

                }

            }  else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return true;
    }
}
