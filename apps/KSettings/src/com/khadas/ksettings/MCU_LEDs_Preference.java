package com.khadas.ksettings;

import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;


public class MCU_LEDs_Preference extends PreferenceActivity implements Preference.OnPreferenceClickListener {
	
	private ListPreference MCU_LED_ON_Preference;
	private ListPreference MCU_LED_OFF_Preference;
	private ListPreference MCU_LED_Breath_Preference;
	private ListPreference MCU_LED_HeartBeat_Preference;
	private ListPreference MCU_LED_Red_Brightness_Preference;
	private ListPreference MCU_LED_Green_Brightness_Preference;
	private ListPreference MCU_LED_Blue_Brightness_Preference;
	
	private static final String MCU_LED_ON_KEY = "MCU_LED_ON_KEY";
	private static final String MCU_LED_OFF_KEY = "MCU_LED_OFF_KEY";
	private static final String MCU_LED_Breath_KEY = "MCU_LED_Breath_KEY";
	private static final String MCU_LED_HeartBeat_KEY = "MCU_LED_HeartBeat_KEY";
	private static final String MCU_LED_RED_BRI_KEY = "MCU_LED_RED_BRI_KEY";
	private static final String MCU_LED_GREEN_BRI_KEY = "MCU_LED_GREEN_BRI_KEY";
	private static final String MCU_LED_BLUE_BRI_KEY = "MCU_LED_BLUE_BRI_KEY";

	private ListPreference MCU_LED_ON_OFF_Preference;
	private ListPreference MCU_LED_OFF_OFF_Preference;
	private ListPreference MCU_LED_Breath_OFF_Preference;
	private ListPreference MCU_LED_HeartBeat_OFF_Preference;
	private ListPreference MCU_LED_Red_Brightness_OFF_Preference;
	private ListPreference MCU_LED_Green_Brightness_OFF_Preference;
	private ListPreference MCU_LED_Blue_Brightness_OFF_Preference;
	
	private static final String MCU_LED_ON_OFF_KEY = "MCU_LED_ON_OFF_KEY";
	private static final String MCU_LED_OFF_OFF_KEY = "MCU_LED_OFF_OFF_KEY";
	private static final String MCU_LED_Breath_OFF_KEY = "MCU_LED_Breath_OFF_KEY";
	private static final String MCU_LED_HeartBeat_OFF_KEY = "MCU_LED_HeartBeat_OFF_KEY";
	private static final String MCU_LED_RED_BRI_OFF_KEY = "MCU_LED_RED_BRI_OFF_KEY";
	private static final String MCU_LED_GREEN_BRI_OFF_KEY = "MCU_LED_GREEN_BRI_OFF_KEY";
	private static final String MCU_LED_BLUE_BRI_OFF_KEY = "MCU_LED_BLUE_BRI_OFF_KEY";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mcu_leds_control);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		
		MCU_LED_ON_Preference = (ListPreference) findPreference(MCU_LED_ON_KEY);
        bindPreferenceSummaryToValue(MCU_LED_ON_Preference);
		MCU_LED_OFF_Preference = (ListPreference) findPreference(MCU_LED_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_OFF_Preference);
		MCU_LED_Breath_Preference = (ListPreference) findPreference(MCU_LED_Breath_KEY);
        bindPreferenceSummaryToValue(MCU_LED_Breath_Preference);
		MCU_LED_HeartBeat_Preference = (ListPreference) findPreference(MCU_LED_HeartBeat_KEY);
        bindPreferenceSummaryToValue(MCU_LED_HeartBeat_Preference);
		MCU_LED_Red_Brightness_Preference = (ListPreference) findPreference(MCU_LED_RED_BRI_KEY);
        bindPreferenceSummaryToValue(MCU_LED_Red_Brightness_Preference);
		MCU_LED_Green_Brightness_Preference = (ListPreference) findPreference(MCU_LED_GREEN_BRI_KEY);
        bindPreferenceSummaryToValue(MCU_LED_Green_Brightness_Preference);
		MCU_LED_Blue_Brightness_Preference = (ListPreference) findPreference(MCU_LED_BLUE_BRI_KEY);
        bindPreferenceSummaryToValue(MCU_LED_Blue_Brightness_Preference);
		
		MCU_LED_ON_OFF_Preference = (ListPreference) findPreference(MCU_LED_ON_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_ON_OFF_Preference);
		MCU_LED_OFF_OFF_Preference = (ListPreference) findPreference(MCU_LED_OFF_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_OFF_OFF_Preference);
		MCU_LED_Breath_OFF_Preference = (ListPreference) findPreference(MCU_LED_Breath_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_Breath_OFF_Preference);
		MCU_LED_HeartBeat_OFF_Preference = (ListPreference) findPreference(MCU_LED_HeartBeat_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_HeartBeat_OFF_Preference);
		MCU_LED_Red_Brightness_OFF_Preference = (ListPreference) findPreference(MCU_LED_RED_BRI_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_Red_Brightness_OFF_Preference);
		MCU_LED_Green_Brightness_OFF_Preference = (ListPreference) findPreference(MCU_LED_GREEN_BRI_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_Green_Brightness_OFF_Preference);
		MCU_LED_Blue_Brightness_OFF_Preference = (ListPreference) findPreference(MCU_LED_BLUE_BRI_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_Blue_Brightness_OFF_Preference);
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
				if(MCU_LED_OFF_KEY.equals(key)){
					//Log.d("hay","OFF===" + index);
                    switch(index){
                        case 0:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2300 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_OFF_control", "" + index);
				
				
				}else if(MCU_LED_ON_KEY.equals(key)){
					//Log.d("hay","ON===" + index);
                    switch(index){
                        case 0:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x25FF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x26FF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x27FF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {						    
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x25FF > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x26FF > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x27FF > /sys/class/mcu/mculed"});	
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
					
                    SystemProperties.set("persist.sys.MCU_led_ON_control", "" + index);
				}else if(MCU_LED_Breath_KEY.equals(key)){
					//Log.d("hay","Breath===" + index);
                    switch(index){
                        case 0:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2302 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2303 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2304 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2305 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2306 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2307 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 6:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2308 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_Breath_control", "" + index);
				}else if(MCU_LED_HeartBeat_KEY.equals(key)){
					//Log.d("hay","HeartBeat===" + index);
                    switch(index){
                        case 0:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2309 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x230A > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x230B > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x230C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 4:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x230D > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x230E > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 6:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x230F > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_HeartBeat_control", "" + index);
				}else if(MCU_LED_RED_BRI_KEY.equals(key)){
					//Log.d("hay","HeartBeat===" + index);
                    switch(index){
                        case 0:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2514 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2528 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x253C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2550 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 4:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2564 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2578 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 6:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x258C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 7:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x25A0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 8:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x25B4 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 9:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x25C8 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 10:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x25DC > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 11:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x25F0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 12:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x25FF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_HeartBeat_control", "" + index);
				}else if(MCU_LED_GREEN_BRI_KEY.equals(key)){
					//Log.d("hay","HeartBeat===" + index);
                    switch(index){
                        case 0:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2614 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2628 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x263C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2650 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 4:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2664 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2678 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 6:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x268C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 7:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x26A0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 8:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x26B4 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 9:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x26C8 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 10:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x26DC > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 11:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x26F0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 12:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2700 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x26FF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_HeartBeat_control", "" + index);
				}else if(MCU_LED_BLUE_BRI_KEY.equals(key)){
					//Log.d("hay","HeartBeat===" + index);
                    switch(index){
                        case 0:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2714 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2728 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x273C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2750 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 4:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2764 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2778 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 6:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x278C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 7:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x27A0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 8:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x27B4 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 9:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x27C8 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 10:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x27DC > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 11:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x27F0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 12:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2301 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2500 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2600 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x27FF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_HeartBeat_control", "" + index);
				}
				// system off
				else if(MCU_LED_OFF_OFF_KEY.equals(key)){
					Log.d("hay","OFF=OFF==" + index);
                    switch(index){
                        case 0:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2400 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_OFF_control", "" + index);
				
				
				}else if(MCU_LED_ON_OFF_KEY.equals(key)){
					//Log.d("hay","ON=OFF==" + index);
                    switch(index){
                        case 0:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x28FF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x29FF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2AFF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {						    
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x28FF > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x29FF > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2AFF > /sys/class/mcu/mculed"});	
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
					
                    SystemProperties.set("persist.sys.MCU_led_ON_control", "" + index);
				}else if(MCU_LED_Breath_OFF_KEY.equals(key)){
					//Log.d("hay","Breath=OFF==" + index);
                    switch(index){
                        case 0:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2402 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2403 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2404 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2405 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2406 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2407 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 6:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2408 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_Breath_control", "" + index);
				}else if(MCU_LED_HeartBeat_OFF_KEY.equals(key)){
					//Log.d("hay","HeartBeat=OFF==" + index);
                    switch(index){
                        case 0:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2409 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x240A > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x240B > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x240C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 4:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x240D > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x240E > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 6:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x240F > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_HeartBeat_control", "" + index);
				}else if(MCU_LED_RED_BRI_OFF_KEY.equals(key)){
					//Log.d("hay","HeartBeat=OFF==" + index);
                    switch(index){
                        case 0:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2814 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2828 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x283C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2850 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 4:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2864 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2878 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 6:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x288C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 7:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x28A0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 8:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x28B4 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 9:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x28C8 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 10:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x28DC > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 11:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x28F0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 12:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x28FF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_HeartBeat_control", "" + index);
				}else if(MCU_LED_GREEN_BRI_OFF_KEY.equals(key)){
					//Log.d("hay","HeartBeat=OFF==" + index);
                    switch(index){
                        case 0:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2914 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2928 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x293C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2950 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 4:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2964 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2978 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 6:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x298C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 7:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x29A0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 8:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x29B4 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 9:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x29C8 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 10:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x29DC > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 11:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x29F0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 12:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A00 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x29FF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_HeartBeat_control", "" + index);
				}else if(MCU_LED_BLUE_BRI_OFF_KEY.equals(key)){
					//Log.d("hay","HeartBeat===" + index);
                    switch(index){
                        case 0:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A14 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A28 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A3C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A50 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 4:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A64 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A78 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 6:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2A8C > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 7:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2AA0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 8:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2AB4 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 9:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2AC8 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 10:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2ADC > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 11:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2AF0 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 12:
                            try {
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2401 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2800 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2900 > /sys/class/mcu/mculed"});
								ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2AFF > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
										
                    SystemProperties.set("persist.sys.MCU_led_HeartBeat_control", "" + index);
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
