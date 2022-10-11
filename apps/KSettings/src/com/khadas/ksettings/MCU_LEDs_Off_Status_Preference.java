package com.khadas.ksettings;

import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;


public class MCU_LEDs_Off_Status_Preference extends PreferenceActivity implements Preference.OnPreferenceClickListener {

	private ListPreference MCU_LED_OFF_Preference;
	private ListPreference MCU_LED_Breath_OFF_Preference;
	private ListPreference MCU_LED_HeartBeat_OFF_Preference;
	
	private static final String MCU_LED_OFF_KEY = "MCU_LED_OFF_KEY";
	private static final String MCU_LED_Breath_OFF_KEY = "MCU_LED_Breath_OFF_KEY";
	private static final String MCU_LED_HeartBeat_OFF_KEY = "MCU_LED_HeartBeat_OFF_KEY";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mcu_leds_off_status_control);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

		MCU_LED_OFF_Preference = (ListPreference) findPreference(MCU_LED_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_OFF_Preference);
		MCU_LED_Breath_OFF_Preference = (ListPreference) findPreference(MCU_LED_Breath_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_Breath_OFF_Preference);
		MCU_LED_HeartBeat_OFF_Preference = (ListPreference) findPreference(MCU_LED_HeartBeat_OFF_KEY);
        bindPreferenceSummaryToValue(MCU_LED_HeartBeat_OFF_Preference);
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
				//Off Status
				if(MCU_LED_OFF_KEY.equals(key)){
					//Log.d("hay","OFF===" + index);
                    switch(index){
                        case 0:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0x2400 > /sys/class/mcu/mculed"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
						case 1:
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
				}else if(MCU_LED_Breath_OFF_KEY.equals(key)){
					//Log.d("hay","Breath===" + index);
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
				}else if(MCU_LED_HeartBeat_OFF_KEY.equals(key)){
					//Log.d("hay","HeartBeat===" + index);
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
