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

public class PinMultiple extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    private ListPreference PIN_MULTIPLE_Preference;
    private static final String PIN_MULTIPLE_KEY = "PIN_MULTIPLE_KEY";

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pin_multiple);
        mContext = this;
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        PIN_MULTIPLE_Preference = (ListPreference) findPreference(PIN_MULTIPLE_KEY);
        bindPreferenceSummaryToValue(PIN_MULTIPLE_Preference);
    }

    private Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            String key = preference.getKey();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

                if(PIN_MULTIPLE_KEY.equals(key)){
                    switch (index){
                        case 0:
                            su_exec("echo fdt_overlays= > /vendor/custom/boot/dtb/rockchip/rk3588s-khadas-edge2.dtb.overlay.env");
                            break;
                        case 1:
                            su_exec("echo fdt_overlays=edge2-io-spi > /vendor/custom/boot/dtb/rockchip/rk3588s-khadas-edge2.dtb.overlay.env");
                            break;
                    }
                }
            }  else {
                preference.setSummary(stringValue);
            }
            return true;
            }
        };

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
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
