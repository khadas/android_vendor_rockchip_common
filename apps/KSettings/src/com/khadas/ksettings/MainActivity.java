package com.khadas.ksettings;

import android.content.Context;
import android.os.SystemProperties;
import android.preference.ListPreference;
import android.preference.PreferenceScreen;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    private ListPreference FAN_Preference;
    private SwitchPreference WOL_Preference;
	private SwitchPreference CAM1_IR_CUT_Preference;
	private SwitchPreference CAM2_IR_CUT_Preference;
	private SwitchPreference CAM3_IR_CUT_Preference;

    private Context mContext;

    private static final String FAN_KEY = "FAN_KEY";
	private static final String CAM1_IR_CUT_KEY = "CAM1_IR_CUT_KEY";
	private static final String CAM2_IR_CUT_KEY = "CAM2_IR_CUT_KEY";
	private static final String CAM3_IR_CUT_KEY = "CAM3_IR_CUT_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main);
        mContext = this;
		PreferenceScreen preferenceScreen = getPreferenceScreen();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        FAN_Preference = (ListPreference) findPreference(FAN_KEY);
        //bindPreferenceSummaryToValue(FAN_Preference);
        FAN_Preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener); 
        int fanCtl = SystemProperties.getInt("persist.sys.fan_control", 1);
        String[] fanArray = getResources().getStringArray(R.array.FAN_array);
        FAN_Preference.setValueIndex(fanCtl);
        FAN_Preference.setSummary(fanArray[fanCtl]);

		CAM1_IR_CUT_Preference = (SwitchPreference)findPreference(CAM1_IR_CUT_KEY);
		CAM1_IR_CUT_Preference.setOnPreferenceClickListener(this);
		CAM2_IR_CUT_Preference = (SwitchPreference)findPreference(CAM2_IR_CUT_KEY);
		CAM2_IR_CUT_Preference.setOnPreferenceClickListener(this);
		CAM3_IR_CUT_Preference = (SwitchPreference)findPreference(CAM3_IR_CUT_KEY);
		CAM3_IR_CUT_Preference.setOnPreferenceClickListener(this);

		CAM1_IR_CUT_Preference = (SwitchPreference)findPreference(CAM1_IR_CUT_KEY);
		CAM1_IR_CUT_Preference.setOnPreferenceClickListener(this);
		CAM2_IR_CUT_Preference = (SwitchPreference)findPreference(CAM2_IR_CUT_KEY);
		CAM2_IR_CUT_Preference.setOnPreferenceClickListener(this);
		CAM3_IR_CUT_Preference = (SwitchPreference)findPreference(CAM3_IR_CUT_KEY);
		CAM3_IR_CUT_Preference.setOnPreferenceClickListener(this);

        File file = new File("/sys/bus/i2c/drivers/os08a10/3-0036");
        if (!file.exists()){
			//CAM1_IR_CUT_Preference.setEnabled(false);
			preferenceScreen.removePreference(CAM1_IR_CUT_Preference);
		}

		file = new File("/sys/bus/i2c/drivers/os08a10/4-0036");
        if (!file.exists()){
			//CAM2_IR_CUT_Preference.setEnabled(false);
			preferenceScreen.removePreference(CAM2_IR_CUT_Preference);
		}

		file = new File("/sys/bus/i2c/drivers/os08a10/8-0036");
        if (!file.exists()){
			//CAM3_IR_CUT_Preference.setEnabled(false);
			preferenceScreen.removePreference(CAM3_IR_CUT_Preference);
		}
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

                if(FAN_KEY.equals(key)){
                    //Log.d("wjh","f===" + index);
                    //set Fan Level
                    switch (index){
                        case 0:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0 > /sys/class/fan/enable"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 1 > /sys/class/fan/enable"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 1 > /sys/class/fan/mode"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 1 > /sys/class/fan/enable"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0 > /sys/class/fan/mode"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 1 > /sys/class/fan/level"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 1 > /sys/class/fan/enable"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0 > /sys/class/fan/mode"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 2 > /sys/class/fan/level"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 1 > /sys/class/fan/enable"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0 > /sys/class/fan/mode"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 3 > /sys/class/fan/level"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 1 > /sys/class/fan/enable"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0 > /sys/class/fan/mode"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 4 > /sys/class/fan/level"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 6:
                            try {
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 1 > /sys/class/fan/enable"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 0 > /sys/class/fan/mode"});
                                ComApi.execCommand(new String[]{"sh", "-c", "echo 5 > /sys/class/fan/level"});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    SystemProperties.set("persist.sys.fan_control", "" + index);

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
        final String key = preference.getKey();

		if (CAM1_IR_CUT_KEY.equals(key)){
            if (CAM1_IR_CUT_Preference.isChecked()) {
                su_exec("echo 118 > /sys/class/gpio/export;echo out > sys/class/gpio/gpio118/direction;echo 0 > sys/class/gpio/gpio118/value");
				SystemProperties.set("persist.sys.cam1", "" + 1);
            }else {
                su_exec("echo 118 > /sys/class/gpio/export;echo out > sys/class/gpio/gpio118/direction;echo 1 > sys/class/gpio/gpio118/value");
				SystemProperties.set("persist.sys.cam1", "" + 0);
			}
        }else if (CAM2_IR_CUT_KEY.equals(key)){
            if (CAM2_IR_CUT_Preference.isChecked()) {
                su_exec("echo 42 > /sys/class/gpio/export;echo out > sys/class/gpio/gpio42/direction;echo 0 > sys/class/gpio/gpio42/value");
				SystemProperties.set("persist.sys.cam2", "" + 1);
            }else {
                su_exec("echo 42 > /sys/class/gpio/export;echo out > sys/class/gpio/gpio42/direction;echo 1 > sys/class/gpio/gpio42/value");
				SystemProperties.set("persist.sys.cam2", "" + 0);
			}
        }else if (CAM3_IR_CUT_KEY.equals(key)){
            if (CAM3_IR_CUT_Preference.isChecked()) {
                su_exec("echo 108 > /sys/class/gpio/export;echo out > sys/class/gpio/gpio108/direction;echo 0 > sys/class/gpio/gpio108/value");
				SystemProperties.set("persist.sys.cam3", "" + 1);
            }else {
                su_exec("echo 108 > /sys/class/gpio/export;echo out > sys/class/gpio/gpio108/direction;echo 1 > sys/class/gpio/gpio108/value");
				SystemProperties.set("persist.sys.cam3", "" + 0);
			}
		}
        return true;
    }

	public static String su_exec(String command) {

	    Process process = null;
	    BufferedReader reader = null;
	    InputStreamReader is = null;
	    DataOutputStream os = null;

	    try {
	        process = Runtime.getRuntime().exec("su");
	        is = new InputStreamReader(process.getInputStream());
	        reader = new BufferedReader(is);
	        os = new DataOutputStream(process.getOutputStream());
	        os.writeBytes(command + "\n");
	        os.writeBytes("exit\n");
	        os.flush();
	        int read;
	        char[] buffer = new char[4096];
	        StringBuilder output = new StringBuilder();
	        while ((read = reader.read(buffer)) > 0) {
	            output.append(buffer, 0, read);
	        }
	        process.waitFor();
	        return output.toString();
	    } catch (IOException | InterruptedException e) {
	        throw new RuntimeException(e);
	    } finally {
	        try {
	            if (os != null) {
	                os.close();
	            }

	            if (reader != null) {
	                reader.close();
	            }

	            if (is != null) {
	                is.close();
	            }

	            if (process != null) {
	                process.destroy();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
