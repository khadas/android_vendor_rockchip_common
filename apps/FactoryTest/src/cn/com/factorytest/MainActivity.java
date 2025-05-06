package cn.com.factorytest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.StatFs;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.text.Editable;
import android.text.format.Formatter;
import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.hardware.Camera;
import android.view.Gravity;

import com.google.gson.Gson;
import cn.com.factorytest.encoding.EncodingUtils;
import android.graphics.Bitmap;

import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.*;
import android.os.SystemProperties;
import android.text.TextUtils;

public class MainActivity extends Activity {

    public static final String TAG = Tools.TAG;

    public static String test_board="Edge2";
    public static String udisk_backup = "";

    public static boolean tfcard_test = false;
    private static boolean tfcard_test_ret = false;

    public static boolean usb20_test = false;
    private static boolean usb20_test_ret = false;

    public static boolean usb30_test = false;
    private static boolean usb30_test_ret = false;

    public static boolean spi_test = false;
    private static boolean spi_test_ret = false;

    public static boolean key_test = false;
    private static boolean key_test_ret = false;

    public static boolean bt_test = false;
    private static boolean bt_test_ret = false;

    public static boolean wifi_test = false;
    private static boolean wifi_test_ret = false;

    public static boolean mcu_test = false;
    private static boolean mcu_test_ret = false;

    public static boolean hdmi_test = false;
    private static boolean hdmi_test_ret = false;

    public static boolean dp_test = false;
    private static boolean dp_test_ret = false;

    public static boolean fusb302_test = false;
    private static boolean fusb302_test_ret = false;

    public static boolean gsensor_test = false;
    private static boolean gsensor_test_ret = false;

    public static boolean rtc_test = false;
    private static boolean rtc_test_ret = false;

    public static boolean board_key_test = false;
    private static boolean board_key_test_ret = false;

    public static boolean ageing_test = false;
    private static boolean ageing_test_ret = false;

    private static boolean ageing_test_ok_flag = false;

    public static boolean led_test = false;
    private static boolean led_test_ret = false;

    public static boolean mic_test = false;
    private static boolean mic_test_ret = false;

    public static boolean irkey_test = false;
    private static boolean irkey_test_ret = false;

    public static boolean mipi_camera_test = false;
    private static boolean mipi_camera_test_ret = false;

    public static boolean mipi_lcd_test = false;
    private static boolean mipi_lcd_test_ret = false;

    public static boolean tp_test = false;
    private static boolean tp_test_ret = false;

    public static boolean wirte_mac = false;
    private static boolean wirte_mac_ret = false;

    public static boolean burn_efuse_flag = false;
    private static boolean burn_efuse_flag_ret = false;

    public static boolean reset_mcu = false;
    private static boolean reset_mcu_ret = false;

    public static boolean power_led_test = false;
    private static boolean power_led_test_ret = false;

    public static boolean device_id_test = false;
    private static boolean device_id_test_ret = false;

    TextView m_mcu_version;
    TextView m_firmware_version;
    TextView m_ddr_size;
    TextView m_nand_size;
    TextView m_device_type;
    TextView m_macvalue;
    TextView m_snvalue;
    TextView m_ip;
    TextView m_wifimac;
    TextView m_wifiip;
    TextView m_device_id;
    TextView m_TextView_CPU_THERMAL;
    TextView m_TextView_CPU_FREQ;

    TextView m_mactitle;
    EditText m_maccheck;
    TextView m_TextView_Time;
    TextView m_TextView_TF;
    TextView m_TextView_USB1;
    TextView m_TextView_USB2;
    TextView m_TextView_SPIFLASH;
    TextView m_TextView_KEY;
    TextView m_TextView_BT;
    TextView m_TextView_Wifi;

    TextView m_TextView_MCU;
    TextView m_TextView_HDMI;
    TextView m_TextView_DP;
    TextView m_TextView_PD12;
    TextView m_TextView_Gsensor;
    TextView m_TextView_Rtc;
    TextView m_TextView_AGEING;

    TextView m_TextView_NetLed;
    Button m_Button_NetLed;
    Button m_Button_PowerLed;
    TextView m_TextView_Led;
    Button m_Button_Key;
    Button m_Button_IRKey;
    Button m_Button_Mipi_Camera;
    Button m_Button_Mipi_LCD;
    Button m_Button_TP;
    Button m_Button_speaker_MIC;
    Button m_Button_write_mac_usid;
    Button m_Button_Restore_MCU_settings;
    ImageView m_ImageView_infoBarCode;

    Handler mHandler = new FactoryHandler();

    private final int MSG_WIFI_TEST_ERROR = 77;
    private final int MSG_WIFI_TEST_OK = 78;
    private final int MSG_TF_TEST_ERROR = 79;
    private final int MSG_TF_TEST_OK = 80;
    private final int MSG_USB1_TEST_ERROR = 81;
    private final int MSG_USB1_TEST_OK = 82;
    private final int MSG_USB2_TEST_ERROR = 83;
    private final int MSG_USB2_TEST_OK = 84;
    private final int MSG_NETLED_TEST_Start = 85;
    private final int MSG_NETLED_TEST_End = 86;
    private final int MSG_POWERLED_TEST_Start = 87;
    private final int MSG_POWERLED_TEST_End = 88;
    private final int MSG_WIFI_TOAST = 89;
    private final int MSG_PLAY_VIDEO = 90;
    private final int MSG_TF_TEST_XL_OK = 91;
    private final int MSG_TF_TEST_XL_ERROR = 92;
    private final int MSG_USB1_TEST_XL_OK = 93;
    private final int MSG_USB1_TEST_XL_ERROR = 94;
    private final int MSG_android_6_0_TEXT_LAYOUT = 95;
    private final int MSG_USB2_TEST_XL_OK = 96;
    private final int MSG_USB2_TEST_XL_ERROR = 97;
    private final int MSG_RTC_TEST_OK = 98;
    private final int MSG_RTC_TEST_ERROR = 99;
    private final int MSG_BT_TEST_ERROR = 100;
    private final int MSG_BT_TEST_OK = 101;
    private final int MSG_GSENSOR_TEST_OK = 102;
    private final int MSG_GSENSOR_TEST_ERROR = 103;
    private final int MSG_MCU_TEST_ERROR = 104;
    private final int MSG_MCU_TEST_OK = 105;
    private final int MSG_SPIFLASH_TEST_ERROR = 106;
    private final int MSG_SPIFLASH_TEST_OK = 107;
    private final int MSG_HDMI_TEST_ERROR = 108;
    private final int MSG_HDMI_TEST_OK = 109;
    private final int MSG_DP_TEST_ERROR =  110;
    private final int MSG_DP_TEST_OK =  111;
    private final int MSG_PD12_TEST_ERROR = 112;
    private final int MSG_PD12_TEST_OK = 113;
    private final int MSG_PD2_TEST_ERROR = 114;
    private final int MSG_PD1_TEST_ERROR = 115;
    private final int MSG_AGEING_TEST_ERROR = 116;
    private final int MSG_AGEING_TEST_OK = 117;
    private final int MSG_KEY_TEST_ERROR = 118;
    private final int MSG_KEY_TEST_OK = 119;
    private final int MSG_GET_CPU_STATUS = 120;
    private final int MSG_TIME = 777;

    private final int MSG_TEST_RET_UPDATE = 1000;
    private static final String nullip = "0.0.0.0";
    private static final String USB_PATH = (Tools.isAndroid5_1_1()?"/storage/udisk":"/storage/external_storage/sd");
    private static final String USB1_PATH = (Tools.isAndroid5_1_1()?"/storage/udisk0":"/storage/external_storage/sda");
    private static final String USB2_PATH = (Tools.isAndroid5_1_1()?"/storage/udisk1":"/storage/external_storage/sdb");
    private static final String TFCARD_PATH = (Tools.isAndroid5_1_1()?"/storage/sdcard":"/storage/external_storage/sdcard");
    private List<ScanResult> wifiList;

    String configSSID =  "";
    int configLevel = 60;

    int wifiLevel = 0;
    String usb_path = "";
    LinearLayout mLeftLayout, mBottomLayout, mBottomLayout2, mBottomLayout3, mBottomLayout4, mBottomLayout5, mBottomLayout6;
    String configFile = "";
    int tag_net = 0;
    int tag_power = 0;
    AudioManager mAudioManager = null;
    int maxVolume;
    int currentVolume;
    String lssue_value = "";
    String client_value = "";
    String readMac = "";
    String readSn = "";
    String readDeviceid = "";

    private boolean bIsKeyDown = false;
    //系统灯和网络灯测试时间 单位s
    int ledtime = 60;
    //videoview 全屏播放时间
    //private final long  MSG_PLAY_VIDEO_TIME= 30 * 60 * 1000;

    private Context mContext;
    private BTDeviceReceiver mBTDeviceReceiver;
    private int CONFIG_BT_RSSI = -100;
    private boolean BT_ERR =true;
    private int BT_try_count = 1;
    private int btLevel = 0;
    private final String BTSSID="Khadas";
    String[] usbStatus = new String[4];
    private BTAdmin localBTAdmin;
    public static int key_flag = 0;
    public static int ageing_flag = 0;
    public static int ageing_time = 0;
    public static int ageing_cpu_max = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        //最大音量
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //当前音量
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //进入产测apk设置最大音量
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);

        m_TextView_TF = (TextView)findViewById(R.id.TextView_TF);
        m_TextView_USB1 = (TextView)findViewById(R.id.TextView_USB1);
        m_TextView_USB2 = (TextView)findViewById(R.id.TextView_USB2);
        m_TextView_SPIFLASH = (TextView)findViewById(R.id.TextView_SPIFLASH);
        m_TextView_KEY = (TextView) findViewById(R.id.TextView_KEY);
        m_TextView_BT = (TextView)findViewById(R.id.TextView_BT);
        m_TextView_Wifi = (TextView)findViewById(R.id.TextView_Wifi);

        m_TextView_MCU = (TextView)findViewById(R.id.TextView_MCU);
        m_TextView_HDMI = (TextView)findViewById(R.id.TextView_HDMI);
        m_TextView_DP = (TextView)findViewById(R.id.TextView_DP);
        m_TextView_PD12 = (TextView)findViewById(R.id.TextView_PD12);
        m_TextView_Gsensor = (TextView)findViewById(R.id.TextView_Gsensor);
        m_TextView_Rtc = (TextView)findViewById(R.id.TextView_Rtc);
        m_TextView_AGEING = (TextView)findViewById(R.id.TextView_AGEING);

        m_TextView_Led = (TextView)findViewById(R.id.LedTest);
        m_Button_speaker_MIC = (Button)findViewById(R.id.speaker_MIC);
        m_Button_Key = (Button) findViewById(R.id.KeyTest);
        m_Button_IRKey = (Button) findViewById(R.id.IRKeyTest);
        m_Button_Mipi_Camera = (Button) findViewById(R.id.Mipi_Camera);
        m_Button_Mipi_LCD = (Button) findViewById(R.id.Mipi_LCD);
        m_Button_TP = (Button) findViewById(R.id.TP_Test);
        m_Button_write_mac_usid = (Button)findViewById(R.id.Button_Writemac);
        m_Button_Restore_MCU_settings = (Button)findViewById(R.id.Restore_MCU_settings);

        m_TextView_Time = (TextView)findViewById(R.id.TextView_Time);

        m_macvalue = (TextView)findViewById(R.id.mac_value);
        m_ip = (TextView)findViewById(R.id.ip_value);
        m_wifimac = (TextView)findViewById(R.id.wifi_mac_value);
        m_wifiip = (TextView)findViewById(R.id.wifi_ip_value);
        m_device_id = (TextView)findViewById(R.id.device_id_value);
        m_device_type = (TextView)findViewById(R.id.device_type_value);
        m_snvalue = (TextView)findViewById(R.id.sn_value);
        m_mcu_version = (TextView)findViewById(R.id.mcu_version_value);
        m_firmware_version = (TextView)findViewById(R.id.firmware_version_value);
        m_ddr_size = (TextView)findViewById(R.id.ddr_size_value);
        m_nand_size = (TextView)findViewById(R.id.nand_size_value);
        m_TextView_CPU_THERMAL = (TextView) findViewById(R.id.cpu_thermal_value);
        m_TextView_CPU_FREQ = (TextView) findViewById(R.id.cpu_freq_value);

        m_Button_PowerLed = (Button)findViewById(R.id.Button_PowerLed);
        m_TextView_NetLed = (TextView)findViewById(R.id.Button_NetLed);

        if (!tfcard_test) {
            m_TextView_TF.setVisibility(View.GONE);
        }

        if (!usb20_test) {
            m_TextView_USB1.setVisibility(View.GONE);
        }

        if (!usb30_test) {
            m_TextView_USB2.setVisibility(View.GONE);
        }

        if (!spi_test) {
            m_TextView_SPIFLASH.setVisibility(View.GONE);
        }

        if (!key_test) {
            m_TextView_KEY.setVisibility(View.GONE);
        }

        if (key_test) {
			Tools.exec("echo 1 > /sys/class/mcu/key_test");
        }

        if (!bt_test) {
            m_TextView_BT.setVisibility(View.GONE);
        }

        if (!wifi_test) {
            m_TextView_Wifi.setVisibility(View.GONE);
        }

        if (!mcu_test) {
            m_TextView_MCU.setVisibility(View.GONE);
        }

        if (!hdmi_test) {
            m_TextView_HDMI.setVisibility(View.GONE);
        }

        if (!dp_test) {
            m_TextView_DP.setVisibility(View.GONE);
        }

        if (!fusb302_test) {
            m_TextView_PD12.setVisibility(View.GONE);
        }

        if (!gsensor_test) {
            m_TextView_Gsensor.setVisibility(View.GONE);
        }

        if (!rtc_test) {
            m_TextView_Rtc.setVisibility(View.GONE);
        }

        if (!board_key_test) {
            m_Button_Key.setVisibility(View.GONE);
        }

        if (!ageing_test) {
            m_TextView_AGEING.setVisibility(View.GONE);
        }

        if (!led_test) {
            m_TextView_Led.setVisibility(View.GONE);
        }

        if (!mic_test) {
            m_Button_speaker_MIC.setVisibility(View.GONE);
        }

        if (!irkey_test) {
            m_Button_IRKey.setVisibility(View.GONE);
        }

        if (!mipi_camera_test) {
            m_Button_Mipi_Camera.setVisibility(View.GONE);
        }

        if (!mipi_lcd_test) {
            m_Button_Mipi_LCD.setVisibility(View.GONE);
        }

        if (!tp_test) {
            m_Button_TP.setVisibility(View.GONE);
        }

        if (!wirte_mac) {
            m_Button_write_mac_usid.setVisibility(View.GONE);
        }

        if (!reset_mcu) {
            m_Button_Restore_MCU_settings.setVisibility(View.GONE);
        }

        if (!power_led_test) {
            m_Button_PowerLed.setVisibility(View.GONE);
        }

        if (!device_id_test) {
            m_device_id.setVisibility(View.GONE);
        }

        m_maccheck = (EditText)findViewById(R.id.EditTextMac); 
        m_maccheck.setInputType(InputType.TYPE_NULL);
        m_maccheck.addTextChangedListener(mTextWatcher);
        m_mactitle = (TextView)findViewById(R.id.MacTitle);

        mLeftLayout = (LinearLayout) findViewById(R.id.Layout_Left);
        //mBottomLayout = (LinearLayout) findViewById(R.id.Layout_Bottom);
        mBottomLayout2 = (LinearLayout) findViewById(R.id.Layout_Bottom2);
        mBottomLayout3 = (LinearLayout) findViewById(R.id.Layout_Bottom3);
        mBottomLayout4 = (LinearLayout) findViewById(R.id.Layout_Bottom4);
        mBottomLayout5 = (LinearLayout) findViewById(R.id.Layout_Bottom5);
        mBottomLayout6 = (LinearLayout) findViewById(R.id.Layout_Bottom6);

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiManager.setWifiEnabled(true);

        updateTime();
        new Thread() {
            public void run() {
                if (ageing_test) {
                    test_cpu_ageing();
                }
                while (true) {
                    try {
                        unregisterBTReceiver();
                        test_Thread();
                        Thread.sleep(10 * 1000);
                    } catch (Exception localException1) {
                    }
                }
            }
        }.start();

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        mHandler.sendEmptyMessage(MSG_GET_CPU_STATUS);
                        mHandler.sendEmptyMessage(MSG_TEST_RET_UPDATE);
                        Thread.sleep(1000);
                        if(2 == VideoFragment.ageing_test_step && !ageing_test_ok_flag) {
                            mHandler.sendEmptyMessage(MSG_AGEING_TEST_OK);
                            ageing_test_ok_flag = true;
                        } else if(1 == VideoFragment.ageing_test_step && ageing_test_ok_flag) {
                            mHandler.sendEmptyMessage(MSG_AGEING_TEST_ERROR);
                            ageing_test_ok_flag = false;
                        }
                    }  catch(Exception localException1){
                    }
                }
            }
        }.start();

        m_ImageView_infoBarCode = (ImageView)findViewById(R.id.iv_infoBarCode);
        m_ImageView_infoBarCode.setVisibility(View.GONE);
    }

    private void checkTestRetUpate() {

        Log.d("TESTINFO", "=================================checkTestRetUpate start===========================================");
        Log.d("TESTINFO", "tfcard_test " + tfcard_test +  " tfcard_test_ret " +  tfcard_test_ret + " RET " + (tfcard_test ? (tfcard_test & tfcard_test_ret) : true));
        Log.d("TESTINFO", "usb20_test " + usb20_test +  " usb20_test_ret " +  usb20_test_ret + " RET " + (usb20_test ? (usb20_test & usb20_test_ret) : true));
        Log.d("TESTINFO", "usb30_test " + usb30_test +  " usb30_test_ret " +  usb30_test_ret + " RET " + (usb30_test ? (usb30_test & usb30_test_ret) : true));
        Log.d("TESTINFO", "spi_test " + spi_test +  " spi_test_ret " +  spi_test_ret + " RET " + (spi_test ? (spi_test & spi_test_ret) : true));
        Log.d("TESTINFO", "gsensor_test " + gsensor_test +  " gsensor_test_ret " +  gsensor_test_ret + " RET " +  (gsensor_test ? (gsensor_test & gsensor_test_ret) : true));
        Log.d("TESTINFO", "mcu_test " + mcu_test +  " mcu_test_ret " +  mcu_test_ret + " RET " + (mcu_test ? (mcu_test & mcu_test_ret) : true));
        Log.d("TESTINFO", "hdmi_test " + hdmi_test +  " hdmi_test_ret " +  hdmi_test_ret + " RET " +  (hdmi_test ? (hdmi_test & hdmi_test_ret) : true));
        Log.d("TESTINFO", "dp_test " + dp_test +  " dp_test_ret " +  dp_test_ret + " RET " +  (dp_test ? (dp_test & dp_test_ret) : true));
        Log.d("TESTINFO", "fusb302_test " + fusb302_test +  " fusb302_test_ret " +  fusb302_test_ret + " RET " + (fusb302_test ? (fusb302_test & fusb302_test_ret) : true));
        Log.d("TESTINFO", "wifi_test " + wifi_test +  " wifi_test_ret " +  wifi_test_ret + " RET " + (wifi_test ? (wifi_test & wifi_test_ret) : true));
        Log.d("TESTINFO", "bt_test " + bt_test +  " bt_test_ret " +  bt_test_ret + " RET " + (bt_test ? (bt_test & bt_test_ret) : true));
        Log.d("TESTINFO", "rtc_test " + rtc_test +  " rtc_test_ret " +  rtc_test_ret + " RET " + (rtc_test ? (rtc_test & rtc_test_ret) : true));
        Log.d("TESTINFO", "ageing_test " + ageing_test +  " ageing_test_ret " +  ageing_test_ret + " RET " + (ageing_test ? (ageing_test & ageing_test_ret) : true));
        Log.d("TESTINFO", "power_led_test " + power_led_test +  " power_led_test_ret " +  power_led_test_ret + " RET " + (power_led_test ? (power_led_test & power_led_test_ret) : true));
        Log.d("TESTINFO", "irkey_test " + irkey_test +  " irkey_test_ret " +  irkey_test_ret + " RET " + (irkey_test ? (irkey_test & irkey_test_ret) : true));
        Log.d("TESTINFO", "mic_test " + mic_test +  " mic_test_ret " +  mic_test_ret + " RET " + (mic_test ? (mic_test & mic_test_ret) : true));
        Log.d("TESTINFO", "mipi_camera_test " + mipi_camera_test +  " mipi_camera_test_ret " +  mipi_camera_test_ret + " RET " + (mipi_camera_test ? (mipi_camera_test & mipi_camera_test_ret) : true));
        Log.d("TESTINFO", "board_key_test " + board_key_test +  " board_key_test_ret " +  board_key_test_ret + " RET " + (board_key_test ? (board_key_test & board_key_test_ret) : true));
        Log.d("TESTINFO", "key_test " + key_test +  " key_test_ret " +  key_test_ret + " RET " + (key_test ? (key_test & key_test_ret) : true));
        Log.d("TESTINFO", "reset_mcu " + reset_mcu +  " reset_mcu_ret " +  reset_mcu_ret + " RET " + (reset_mcu ? (reset_mcu & reset_mcu_ret) : true));
        Log.d("TESTINFO", "mipi_lcd_test " + mipi_lcd_test +  " mipi_lcd_test_ret " +  mipi_lcd_test_ret + " RET " + (mipi_lcd_test ? (mipi_lcd_test & mipi_lcd_test_ret) : true));
        Log.d("TESTINFO", "tp_test " + tp_test +  " tp_test_ret " +  tp_test_ret + " RET " + (tp_test ? (tp_test & tp_test_ret) : true));
        Log.d("TESTINFO", "wirte_mac " + wirte_mac +  " wirte_mac_ret " +  wirte_mac_ret + " RET " + (wirte_mac ? (wirte_mac & wirte_mac_ret) : true));
        Log.d("TESTINFO", "ageing_test_ok_flag " + ageing_test_ok_flag);
        Log.d("TESTINFO", "=================================checkTestRetUpate end===========================================");

        if((tfcard_test ? (tfcard_test & tfcard_test_ret) : true) && (usb20_test ? (usb20_test & usb20_test_ret) : true) &&
                (usb30_test ? (usb30_test & usb30_test_ret) : true) &&
                (spi_test ? (spi_test & spi_test_ret) : true) &&
                (gsensor_test ? (gsensor_test & gsensor_test_ret) : true) && (mcu_test ? (mcu_test & mcu_test_ret) : true) &&
                (hdmi_test ? (hdmi_test & hdmi_test_ret) : true) && (dp_test ? (dp_test & dp_test_ret) : true) &&
                (fusb302_test ? (fusb302_test & fusb302_test_ret) : true) &&
                (wifi_test ? (wifi_test & wifi_test_ret) : true) &&
                (bt_test ? (bt_test & bt_test_ret) : true) && (rtc_test ? (rtc_test & rtc_test_ret) : true) &&
                (ageing_test ? (ageing_test & ageing_test_ret) : true) && (power_led_test ? (power_led_test & power_led_test_ret) : true) &&
                (irkey_test ? (irkey_test & irkey_test_ret) : true) &&
                (mic_test ? (mic_test & mic_test_ret) : true) && (mipi_camera_test ? (mipi_camera_test & mipi_camera_test_ret) : true) &&
                (board_key_test ? (board_key_test & board_key_test_ret) : true) && (key_test ? (key_test & key_test_ret) : true) &&
                (reset_mcu ? (reset_mcu & reset_mcu_ret) : true) &&
                (mipi_lcd_test ? (mipi_lcd_test & mipi_lcd_test_ret) : true) && (tp_test ? (tp_test & tp_test_ret) : true) &&
                (wirte_mac ? (wirte_mac & wirte_mac_ret) : true)) {
            DevBarcodeInfo devBarcodeInfo = new DevBarcodeInfo();
            devBarcodeInfo.setModel(""+MainActivity.test_board);
            devBarcodeInfo.setMac(""+getMac());
            devBarcodeInfo.setSn(""+getSn());
            devBarcodeInfo.setMcu(""+getMCUVersion());
            devBarcodeInfo.setFw(""+Build.DISPLAY);
            devBarcodeInfo.setDdr(""+Tools.getMemSize());
            devBarcodeInfo.setFlash(""+Tools.getRomSize(this));
            devBarcodeInfo.setRemark("");
            Gson json = new Gson();
            String showBardCode = json.toJson(devBarcodeInfo);
            Bitmap bitmap = EncodingUtils.createQRCode(showBardCode, 500, 500, null);
            m_ImageView_infoBarCode.setImageBitmap(bitmap);
            m_ImageView_infoBarCode.setVisibility(View.VISIBLE);
            Log.d(TAG, "showBardCode:" + showBardCode);
        }
    }
    private String execSuCmd(String cmd) {
        try {
            Process mProcess = Runtime.getRuntime().exec("cmdclient "+cmd);
            BufferedReader mInputReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            BufferedReader mErrorReader = new BufferedReader(new InputStreamReader(mProcess.getErrorStream()));
            String msg = "";
            String line;
            int i = 0;
            while ((line = mInputReader.readLine()) != null) {
                if(0!=i)
                    msg += '\n';
                    msg += line;
                    i = 1;
            }
            mInputReader.close();

            i = 0;
            while ((line = mErrorReader.readLine()) != null) {
                if(0!=i)
                    msg += '\n';
                    msg += line;
                    i = 1;
            }
            mErrorReader.close();
            mProcess.destroy();
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
            return "execSuCmd Error";
        }
    }

    public void test_Thread() {
        test_AGEING();
        mHandler.sendEmptyMessage(MSG_USB1_TEST_XL_ERROR);
        mHandler.sendEmptyMessage(MSG_USB2_TEST_XL_ERROR);
        test_volumes();
        test_SPIFLASH();
        test_BT();
        test_MCU();
        test_HDMI();
        test_DP();
        test_PD();
        test_Gsensor();
        test_RTC();
        if(wifi_test)
            test_Wifi();
    }

    private void registerBTReceiver() {
        if(bt_test) {
            mBTDeviceReceiver = new BTDeviceReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            mContext.registerReceiver(mBTDeviceReceiver, filter);
            Log.d(TAG, "registerBTReceiver");
        }
    }

    private class BTDeviceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice btd = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                short rssi = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
                if (btd != null) {
                    String name = btd.getName();
                    if (name != null) {
                        //if (name.equals(BTSSID)) {
                            if (rssi > CONFIG_BT_RSSI) {
                                btLevel = -rssi;
                                BT_ERR = false;
                                mHandler.sendEmptyMessage(MSG_BT_TEST_OK);
                            } else {
                                BT_ERR = true;
                            }
                        //}
                        Log.d(TAG, "BT Found device name= " + btd.getName() + " "  + "rssi = " + rssi);
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (BT_ERR) {
                    BT_try_count--;
                    if (BT_try_count > 0) {
                        test_BT();
                    }
                    if (BT_try_count <= 0) {
                        mHandler.sendEmptyMessage(MSG_BT_TEST_ERROR);
                    }
                }
                Log.d(TAG, "BT Found End");
            }
        }
    }

    private void updateEthandWifi() {
        boolean isEthConnected = NetworkUtils.isEthConnected(this);

        if (isEthConnected) {
            //m_ip.setText(NetworkUtils.getLocalIpAddress(this));
        } else {
            m_ip.setText(nullip);
        }

        WifiManager manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = manager.getDhcpInfo();
        WifiInfo wifiinfo = manager.getConnectionInfo();
        if (wifiinfo != null) {
            m_wifiip.setText(NetworkUtils.int2ip(wifiinfo.getIpAddress()));
            m_wifimac.setText(wifiinfo.getMacAddress());
        } else {
            m_wifiip.setText(nullip);
            m_wifimac.setText(" ");
        }
    }

    private String getMCUVersion() {
        String mcuversion = Tools.exec("i2cget -f -y 2 0x18 0x13");
        String formattedVersion = "ERR";
        if (mcuversion != null && mcuversion.startsWith("0x")) {
            formattedVersion = "" + mcuversion.substring(2).toUpperCase();
        }
        return formattedVersion.trim();
    }

    private String getSn() {
        String strSn = Tools.readFile(Tools.Key_OTP_Sn);
        int len = strSn.length();
        if (len != getResources().getInteger(R.integer.config_sn_length)) {
            strSn = "ERR";
        }
        return strSn;
    }

    private String getMac() {
        String strMac = "ERR";
        int length = 0;

        strMac = Tools.readFile(Tools.Key_OTP_Mac);
        length = strMac.length();
        if (length != 12) {
            strMac = "ERR";
        } else {
            String strTmpMac = "";
            for(int i = 0; i < length; i += 2) {
                strTmpMac += strMac.substring(i, (i + 2) < length ? (i + 2) :  length );
                if( (i + 2) < length) strTmpMac += ':';
            }
            strMac = strTmpMac;
        }
        return strMac;
    }

    private boolean checkMacAndSnOk() {
//        String mac = getMac();
//        if (TextUtils.isEmpty(mac) || mac.equals("ERR") || mac.equals("00:00:00:00:00:00")) {
//    	      return false;
//    	  }

          String sn = getSn();
          if (TextUtils.isEmpty(sn) || "".equals(sn.replaceAll("0", ""))) {
              return false;
          }
          return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //readVersion();
        //sendBroadcast(new Intent("com.android.show_upper_bar"));
        //sendBroadcast(new Intent("com.android.show_bottom_bar"));

        m_ddr_size.setText(Tools.getMemSize());
        m_nand_size.setText(Tools.getRomSize(this));

//        String mcuversion = Tools.exec("i2cget -f -y 2 0x18 0x13");
//        if (mcuversion != null && mcuversion.startsWith("0x")) {
//            String formattedVersion = "V" + mcuversion.substring(2).toUpperCase();
//            m_mcu_version.setText(formattedVersion);
//        } else {
//            m_mcu_version.setText("Error");
//        }
        m_mcu_version.setText(getMCUVersion());

        String display = Build.DISPLAY;
        m_firmware_version.setText(display);
//        StringBuilder result = new StringBuilder();
//        if (display != null) {
//        String[] segments = display.split("-");
//            for (String segment : segments) {
//                if (!"Edge2".equals(segment)) {
//                    result.append(segment).append("-");
//                }
//            }
//            if (result.length() > 0) {
//                result.setLength(result.length() - 1);
//                String finalStr = result.toString();
//                finalStr = Character.toUpperCase(finalStr.charAt(0)) + finalStr.substring(1);
//                m_firmware_version.setText(finalStr);
//            }
//        }

        m_device_type.setText(Build.MODEL);

        updateEthandWifi();

        //mHandler.sendEmptyMessageDelayed(MSG_PLAY_VIDEO, MSG_PLAY_VIDEO_TIME);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mFactoryReceiver, filter);

        IntentFilter mountfilter = new IntentFilter();
        mountfilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        mountfilter.addDataScheme("file");
        registerReceiver(mountReceiver, mountfilter);

//        String strMac = Tools.readFile(Tools.Key_OTP_Mac);
//        int length = strMac.length();
//        if (length != 12) {
//            m_macvalue.setTextColor(Color.RED);
//            m_macvalue.setText("ERR");
//        } else {
//           String strTmpMac = "";
//            for(int i = 0; i < length; i += 2) {
//                strTmpMac += strMac.substring(i, (i + 2) < length ? (i + 2) :  length );
//                if( (i + 2) < length) strTmpMac += ':';
//            }
//            m_macvalue.setTextColor(Color.RED);
//            m_macvalue.setText(strTmpMac+" ");
//        }

//        String strSn = Tools.readFile(Tools.Key_OTP_Sn);
//        int length = strSn.length();
//        Log.d(TAG,"SN length= "+length);
//        if (length != getResources().getInteger(R.integer.config_sn_length)) {
//            m_snvalue.setTextColor(Color.RED);
//            m_snvalue.setText("ERR");
//        } else {
//            m_snvalue.setTextColor(Color.RED);
//            m_snvalue.setText(strSn+" ");
//        }
        m_snvalue.setText(getSn());

        m_maccheck.requestFocus();
        int rec = 2;
        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_speaker_mic_test", 2);
        if (rec == 1) {
            m_Button_speaker_MIC.setTextColor(Color.GREEN);
            mic_test_ret = true;
        } else if (rec == 0) {
            m_Button_speaker_MIC.setTextColor(Color.RED);
        }

        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_irkey_test", 2);
        if (rec == 1) {
            m_Button_IRKey.setTextColor(Color.GREEN);
             irkey_test_ret = true;
        } else if (rec == 0) {
            m_Button_IRKey.setTextColor(Color.RED);
        }

        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_mipi_camera_test", 2);
        if (rec == 1) {
            m_Button_Mipi_Camera.setTextColor(Color.GREEN);
            mipi_camera_test_ret = true;
        } else if (rec == 0) {
            m_Button_Mipi_Camera.setTextColor(Color.RED);
        }

        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_mipi_lcd_test", 2);
        if (rec == 1) {
            m_Button_Mipi_LCD.setTextColor(Color.GREEN);
            mipi_lcd_test_ret = true;
        } else if (rec == 0) {
            m_Button_Mipi_LCD.setTextColor(Color.RED);
        }

        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_tp_test", 2);
        if (rec == 1) {
            m_Button_TP.setTextColor(Color.GREEN);
            tp_test_ret = true;
        } else if (rec == 0) {
            m_Button_TP.setTextColor(Color.RED);
        }

        if (checkMacAndSnOk()) {
            wirte_mac_ret = true;
            m_Button_write_mac_usid.setTextColor(Color.GREEN);
        } else {
            m_Button_write_mac_usid.setTextColor(Color.RED);
        }

//        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_write_mac_usid_test", 2);
//        if (rec == 1) {
//            m_Button_write_mac_usid.setTextColor(Color.GREEN);
//        } else if (rec == 0) {
//            m_Button_write_mac_usid.setTextColor(Color.RED);
//        }

    }

    TextWatcher mTextWatcher = new TextWatcher()
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                int after)
        {
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            //bIsKeyDown = true;
            mHandler.sendEmptyMessageDelayed(MSG_TIME, 1 * 1000);
         }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeMessages(MSG_NETLED_TEST_Start);
        //mHandler.removeMessages(MSG_POWERLED_TEST_Start);
        mHandler.removeMessages(MSG_PLAY_VIDEO);
        unregisterReceiver(mFactoryReceiver);
        unregisterReceiver(mountReceiver);
        if(mAudioManager != null)
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO Auto-generated method stub
        if(mAudioManager != null)
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
        unregisterBTReceiver();
    }

    private void unregisterBTReceiver() {
        if (bt_test) {
            Log.d(TAG, "unregisterBTReceiver");
            if (mBTDeviceReceiver != null) {
                unregisterReceiver(mBTDeviceReceiver);
                mBTDeviceReceiver = null;
            }
        }
    }

    public void NetLed_Test(View view){
        Log.e(TAG, "NetLed_Test()");
        m_Button_NetLed.setTag(0);
        mHandler.removeMessages(MSG_NETLED_TEST_Start);
        mHandler.sendEmptyMessage(MSG_NETLED_TEST_Start);
    }

    public void PowerLed_Test(View view){
        Log.e(TAG, "PowerLed_Test()");
        m_Button_PowerLed.setTag(0);
        mHandler.removeMessages(MSG_POWERLED_TEST_Start);
        mHandler.sendEmptyMessage(MSG_POWERLED_TEST_Start);
    }

    public void speaker_MIC(View view){
        Log.e(TAG, "hlm MIC");
        Intent intent = new Intent(this, PhoneMicTestActivity.class);
        //sendBroadcast(new Intent("com.android.hide_upper_bar"));
        //sendBroadcast(new Intent("com.android.hide_bottom_bar"));
        startActivity(intent);
    }

    public void IRKeyTest(View view){
        Log.e(TAG, "IRKeyTest()");
        Intent intent = new Intent(this, IRKeyTestActivity.class);
        //sendBroadcast(new Intent("com.android.hide_upper_bar"));
        //sendBroadcast(new Intent("com.android.hide_bottom_bar"));
        startActivity(intent);
    }

    public void Mipi_Camera(View view) {
        Log.d(TAG, "Mipi_Camera()");
        Intent intent = new Intent(this, MipiCameraTestActivity.class);
        //sendBroadcast(new Intent("com.android.hide_upper_bar"));
        //sendBroadcast(new Intent("com.android.hide_bottom_bar"));
        startActivity(intent);
    }

    public void Mipi_LCD(View view) {
        Log.d(TAG, "Mipi_LCD()");
        Intent intent = new Intent(this, MipiLCDTestActivity.class);
        //sendBroadcast(new Intent("com.android.hide_upper_bar"));
        //sendBroadcast(new Intent("com.android.hide_bottom_bar"));
        startActivity(intent);
    }

    public void TP_Test(View view) {
        Log.d(TAG, "TP_Test()");
        Intent intent = new Intent(this, TouchTestActivity.class);
        //sendBroadcast(new Intent("com.android.hide_upper_bar"));
        //sendBroadcast(new Intent("com.android.hide_bottom_bar"));
        startActivity(intent);
    }

    public void Write_mac_usid(View view){
        Log.e(TAG, "Write_mac_usid()");
        m_Button_write_mac_usid.setTag(0);
        Intent intent = new Intent(this, WriteMacActivity.class);
        //sendBroadcast(new Intent("com.android.hide_upper_bar"));
        //sendBroadcast(new Intent("com.android.hide_bottom_bar"));
        startActivity(intent);
    }

    public void Restore_MCU_settings(View view){
        String strSn = "";
        Log.e(TAG, "hlm Restore_MCU_settings");
        Tools.writeFile("/sys/class/mcu/rst", "0");
        strSn =  Tools.readFile(Tools.Key_OTP_Sn);
        Log.d(TAG, "strSn : " + strSn);

        String path = MainActivity.udisk_backup + "/";
        String cmd_val = "ls " + path;
        Log.d(TAG, "cmd_val : " + cmd_val);
        if(Tools.exec(cmd_val).contains("No such file or directory")){
            cmd_val = "mkdir -p " + path;
            Tools.exec(cmd_val);
        }
        cmd_val = "screencap -p " + path + strSn + ".png";
        Log.d(TAG, "screencap cmd_val : " + cmd_val);
        if(Tools.exec(cmd_val).contains("")){
            Tools.exec("sync");
            m_Button_Restore_MCU_settings.setTextColor(Color.GREEN);
            reset_mcu_ret = true;
        }
    }

    public void KeyTest(View view){
        Log.e(TAG, "hlm KeyTest()");
        //Intent intent = new Intent(this, IRKeyTestActivity.class);
        //startActivity(intent);
    }

    private void test_BT() {
        if(bt_test) {
            try {
                Thread.sleep(1000);
            } catch (Exception localException1) {
            }
            BTAdmin localBTAdmin = new BTAdmin();
            registerBTReceiver();
            localBTAdmin.OpenBT();
            if (!localBTAdmin.ScanBT()) {
                mHandler.sendEmptyMessage(MSG_BT_TEST_ERROR);
            }
        }
    }

    private void test_PD() {
        int usb0,usb1,usb2;
        String msg = execSuCmd("i2cget -f -y 4 0x22 1");
        if (msg.contains("failed") || msg.contains("Error")) {
            usb1=0;
        } else {
            usb1=2;
        }

        String msg0 = execSuCmd("i2cget -f -y 8 0x22 1");
        if (msg0.contains("failed") || msg0.contains("Error")) {
            usb0=0;
        } else {
            usb0=1;
        }

        usb2 = usb1|usb0;
        Log.d(TAG, "hlm fusb302: " + usb2);
        if (3 == usb2) {
            mHandler.sendEmptyMessage(MSG_PD12_TEST_OK);
        } else if (2 == usb2) {
            mHandler.sendEmptyMessage(MSG_PD1_TEST_ERROR);
        } else if (1 == usb2) {
            mHandler.sendEmptyMessage(MSG_PD2_TEST_ERROR);
        } else {
            mHandler.sendEmptyMessage(MSG_PD12_TEST_ERROR);
        }
    }
  
    private void test_MCU() {
        String msg = execSuCmd("i2cget -f -y 2 0x18 0x6");
        if (msg.contains("failed") || msg.contains("Error")) {
            mHandler.sendEmptyMessage(MSG_MCU_TEST_ERROR);
        } else {
            mHandler.sendEmptyMessage(MSG_MCU_TEST_OK);
        }
    }

    private void test_RTC() {
        String content = "";
        String node = "/sys/class/rtc/rtc0/time";
        File file = new File(node);
        if (file.exists()) {
            String val = Tools.readFile(node);
            try {
                FileInputStream instream = new FileInputStream(node);
                if(instream != null)
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    Log.d(TAG, "buffreader = " + buffreader.toString());
                    String line;
                    while( (line = buffreader.readLine() )  !=  null)
                    {
                        content = content + line;
                    }
                    instream.close();
                }
            } catch(FileNotFoundException e) {
                Log.e(TAG, "The File doesn\'t not exist.");
                mHandler.sendEmptyMessage(MSG_RTC_TEST_ERROR);
            } catch(IOException e) {
                Log.e(TAG, " readFile error!");
                Log.e(TAG, e.getMessage() );
                mHandler.sendEmptyMessage(MSG_RTC_TEST_ERROR);
                return;
            }
            mHandler.sendEmptyMessage(MSG_RTC_TEST_OK);
        }
        else
            mHandler.sendEmptyMessage(MSG_RTC_TEST_ERROR);
    }

    private void test_SPIFLASH() {
        String pathname = "/dev/spidev1.1";
        File file = new File(pathname);

        if (file.exists()) {
            Log.d(TAG, "Device /dev/spidev1.1 exists.");
            mHandler.sendEmptyMessage(MSG_SPIFLASH_TEST_OK);
        } else {
            Log.e(TAG, "Device /dev/spidev1.1 does not exist.");
            mHandler.sendEmptyMessage(MSG_SPIFLASH_TEST_ERROR);
        }
    }

    private void test_HDMI() {
        String pathname = "/sys/devices/platform/display-subsystem/drm/card0/card0-HDMI-A-1/edid";
        try (FileReader reader = new FileReader(pathname);
            BufferedReader br = new BufferedReader(reader)) {
            String line;
            if ((line = br.readLine()) != null) {
                Log.d(TAG, "hlm edid=" + line);
                mHandler.sendEmptyMessage(MSG_HDMI_TEST_OK);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mHandler.sendEmptyMessage(MSG_HDMI_TEST_ERROR);
    }

    private void test_DP() {
        String pathname = "/sys/class/drm/card0-DP-1/status";
        File file = new File(pathname);

        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String status = reader.readLine();
                reader.close();

                if (status != null) {
                    if (status.trim().equals("connected")) {
                        mHandler.sendEmptyMessage(MSG_DP_TEST_OK);
                        Log.d(TAG, "hlm DP connected");
                    } else if (status.trim().equals("disconnected")) {
                        mHandler.sendEmptyMessage(MSG_DP_TEST_ERROR);
                        Log.d(TAG, "hlm DP disconnected");
                    } else {
                        Log.e(TAG, "Unexpected status: " + status);
                        mHandler.sendEmptyMessage(MSG_DP_TEST_ERROR);
                    }
                } else {
                    Log.e(TAG, "Status is null");
                    mHandler.sendEmptyMessage(MSG_DP_TEST_ERROR);
                }
            } catch (IOException e) {
                Log.e(TAG, "Error reading status file", e);
                mHandler.sendEmptyMessage(MSG_DP_TEST_ERROR);
            }
        } else {
            Log.e(TAG, "Status file does not exist");
            mHandler.sendEmptyMessage(MSG_DP_TEST_ERROR);
        }
    }

    private void test_AGEING() {
        String pathname = "/sys/class/mcu/ageing_test";
        try (FileReader reader = new FileReader(pathname);
            BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                int id = Integer.parseInt(line);
                Log.d(TAG, "hlm AGEING: " + id);
                if (1 == id) {
                    ageing_test_ok_flag = true;
                    if (!ageing_test) {
                        m_TextView_AGEING.setVisibility(View.VISIBLE);
                    }
                    mHandler.sendEmptyMessage(MSG_AGEING_TEST_OK);
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ageing_test_ok_flag = false;
        mHandler.sendEmptyMessage(MSG_AGEING_TEST_ERROR);
    }

    private List<File> get_input_list(String path) {
        int fileNum = 0;
        File file = new File(path);
        List<File> list = new ArrayList<File>();
        if (file.exists()) {
           File[] files = file.listFiles();
           for (File file2 : files) {
              String name = file2.getName().substring(0,5);;
              Log.d(TAG, "get name="+name);
              if (name.equals("input")) 
              list.add(file2);
           }
        }  
        return list;
    }

    private void test_Gsensor() {
        List<File> list = get_input_list("/sys/class/input");
        if (list != null) {
           int size = list.size();
           for (int i = 0; i< size; i++) {
               String file = list.get(i).getAbsolutePath()+"/name";
               String name = Tools.readFile(file);
               if (name.equals("gsensor")) {
                   mHandler.sendEmptyMessage(MSG_GSENSOR_TEST_OK);
                   return;
               }
           }
        }
        mHandler.sendEmptyMessage(MSG_GSENSOR_TEST_ERROR);
    }

    private void test_cpu_ageing() {
        String shpath = copyAssetGetFilePath("test_cpu_ageing.sh");

        Log.d(TAG, "===shpath====" + shpath);

        File file = new File(shpath);
        if (file.exists()) {
            try {
                Tools.execCommand(new String[]{"sh", "-c", "chmod 777 " + shpath});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < ageing_cpu_max; i++) {
            try {
                Process ps = Runtime.getRuntime().exec(shpath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String copyAssetGetFilePath(String fileName) {
        try {
            File cacheDir = mContext.getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            File outFile = new File(cacheDir, fileName);
            if (!outFile.exists()) {
                boolean res = outFile.createNewFile();
                if (!res) {
                    return null;
                }
            } else {
                if (outFile.length() > 10) {
                    return outFile.getPath();
                }
            }
            InputStream is = mContext.getAssets().open(fileName);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            return outFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void test_Wifi() {
        boolean bWifiScaned = false;
        try {
            Thread.sleep(2000);
        } catch (Exception localException1) {
        }
        configSSID = getResources().getString(R.string.config_ap_ssid);
        WifiAdmin localWifiAdmin = new WifiAdmin(this);
        localWifiAdmin.openWifi();
        localWifiAdmin.startScan();
        wifiList = new ArrayList<ScanResult>();
        wifiList = localWifiAdmin.getWifiList();
        Log.d(TAG, "wifi size: " + wifiList.size());
        if (wifiList != null) {
            for (ScanResult result : wifiList) {
                //if (result.SSID.equals(configSSID)) {
                    wifiLevel = WifiManager.calculateSignalLevel(result.level, 100);
                    Log.d(TAG, "wifiLevel: " + wifiLevel);
                    if (wifiLevel >= configLevel) {
                        bWifiScaned = true;
                    }
                //}
            }
        }
        if (bWifiScaned) {
            mHandler.sendEmptyMessage(MSG_WIFI_TEST_OK);
        } else {
            mHandler.sendEmptyMessage(MSG_WIFI_TEST_ERROR);
        }
    }

    /**
     * 判断USB与TF
     */
    private void test_volumes() {
        if (Tools.isAndroid5_1_1()) {
            test_android5_1();
        } else {
            test_android6_0();
        }
    }

    private void test_android6_0() {
        Log.d(TAG, "----- kernel 6.0 -----");
        mHandler.sendEmptyMessage(MSG_android_6_0_TEXT_LAYOUT);
        Boolean[] usbOrSd = Tools.isUsbOrSd(MainActivity.this);
        int usb3_0_flag = 0;

        if (usbOrSd[0]) {
            mHandler.sendEmptyMessage(MSG_TF_TEST_XL_OK);
        } else {
            mHandler.sendEmptyMessage(MSG_TF_TEST_XL_ERROR);
        }

        for (int i=0; i< usbStatus.length; i++) {
            usbStatus[i] = getResources().getString(R.string.Test_Fail);
        }

        String val = Tools.readFile("/sys/kernel/debug/usb/devices");
        if (val.indexOf("(O)") == -1) {
            Log.e(TAG, "=========USB2.0 and USB3.0 is bad");
            mHandler.sendEmptyMessage(MSG_USB1_TEST_XL_ERROR);
            mHandler.sendEmptyMessage(MSG_USB2_TEST_XL_ERROR);
            return;
        }

        int length;
        String[] list = val.split("T:|B:|D:|P:|S:|C:|I:|E:");
        int num = -1;
        int count = getSubCount(val, "Bus=");
        String[] tmp = new String[count];
        for (int z=0; z< list.length; z++) {
            if (list[z].indexOf("Bus=") != -1) {
                num++;
            }
            if (num == count)
                break;
            if (num == -1)
                continue;
            tmp[num] = tmp[num] + list[z];
        }
        for (int i=0; i< tmp.length; i++) {
            if ((tmp[i].indexOf("(O)") != -1) && ((tmp[i].indexOf("Ver= 3.") != -1))) {
                Log.d(TAG, "USB3.0 is OK");
                usb3_0_flag = 1;
            }else if ((tmp[i].indexOf("(O)") != -1) && (tmp[i].indexOf("Ver= 2.") != -1)) {
                Log.d("TAG", "USB2.0-0 is OK");
                usbStatus[0] = getResources().getString(R.string.Test_Ok);
            }
        }
        if(usbStatus[0].equals(getResources().getString(R.string.Test_Ok))) {
            mHandler.sendEmptyMessage(MSG_USB1_TEST_XL_OK);
        }else{
            mHandler.sendEmptyMessage(MSG_USB1_TEST_XL_ERROR);
        }

        if(1 == usb3_0_flag)
            mHandler.sendEmptyMessage(MSG_USB2_TEST_XL_OK);
        else
            mHandler.sendEmptyMessage(MSG_USB2_TEST_XL_ERROR);
    }

    private  int getSubCount(String str, String key) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(key, index)) != -1) {
             index = index + key.length();
             count++;
        }
        return count;
    }

    private void test_android5_1(){
        Log.d(TAG, "----- kernel 5.1 -----");
        List<String> volumes = getVolumes();
        boolean bSdcard = false;
        boolean bSda = false;
        boolean bSdb = false;
        for(String volume : volumes){
            if (volume.contains(TFCARD_PATH)) {
                bSdcard = true;
            } else if(volume.contains(USB1_PATH)) {
            	Log.d(TAG, USB1_PATH + " usb1 "+volume.toString());
                usb_path = volume;
                bSda = true;
            } else if(volume.contains(USB2_PATH)) {
                Log.d(TAG, USB2_PATH + " usb2 "+ volume.toString());
                bSdb = true;
            }
        }
        if(bSdcard) {
            mHandler.sendEmptyMessage(MSG_TF_TEST_OK);
        } else {
            mHandler.sendEmptyMessage(MSG_TF_TEST_ERROR);
        }
        
    }

    private List<String> getVolumes(){
        List<String> volumes = new ArrayList<String>();
        try{
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("df").getInputStream()));
            String readline;
            while ((readline = bufferReader.readLine()) != null) {
                Log.d(TAG, "df State:" + readline);
                if(readline.contains(USB_PATH) || readline.contains(TFCARD_PATH)){
                    String[] result = readline.split(" ");
                    if(result.length > 0){
                        volumes.add(result[0]);
                    }
                }
            }
        } catch (FileNotFoundException e){
            return volumes;
        } catch (IOException e){
            return volumes;
        }
        return volumes;
    }

    //仅仅在一个U盘接入情况下判断接入那个USB口
    private boolean isUsb1(){
        try {
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("lsusb").getInputStream()));
            String readline = bufferReader.readLine();
            //Bus 001 Device 008: ID 05e3:0723
            String USBBus = readline.substring(readline.indexOf("00")+2, readline.lastIndexOf("Device")).trim();
            Log.d(TAG, "lsusb :  " + USBBus);
            if (USBBus.equals("1")) {
                Log.d(TAG, "lsusb :  is USB1 mount");
                return true;
            }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    class FactoryHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  MSG_TF_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.TF_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_TF.setText(strTxt);
                    m_TextView_TF.setTextColor(0xFFFF5555);
                }
                break;
                case  MSG_TF_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.TF_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_TF.setText(strTxt);
                    m_TextView_TF.setTextColor(0xFF55FF55);
                     tfcard_test_ret = true;
                }
                break;

                case  MSG_USB1_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.USB1_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_USB1.setText(strTxt);
                    m_TextView_USB1.setTextColor(0xFFFF5555);
                }
                break;
                case  MSG_USB1_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.USB1_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_USB1.setText(strTxt);
                    m_TextView_USB1.setTextColor(0xFF55FF55);
                }
                break;
                case  MSG_USB2_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.USB2_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_USB2.setText(strTxt);
                    m_TextView_USB2.setTextColor(0xFFFF5555);
                }
                break;
                case  MSG_USB2_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.USB2_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_USB2.setText(strTxt);
                    m_TextView_USB2.setTextColor(0xFF55FF55);
                }
                break;

                case  MSG_HDMI_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.HDMI_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_HDMI.setText(strTxt);
                    m_TextView_HDMI.setTextColor(0xFF55FF55);
                     hdmi_test_ret = true;
                    Log.d(TAG,"MSG_HDMI_TEST_OK");
                }
                break;
                case  MSG_HDMI_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.HDMI_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_HDMI.setText(strTxt);
                    m_TextView_HDMI.setTextColor(0xFFFF5555);
                    Log.d(TAG,"MSG_HDMI_TEST_ERROR");
                }
                break;

                case  MSG_DP_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.DP_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_DP.setText(strTxt);
                    m_TextView_DP.setTextColor(0xFF55FF55);
                    dp_test_ret  = true;
                    Log.d(TAG,"MSG_DP_TEST_OK");
                }
                break;
                case  MSG_DP_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.DP_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_DP.setText(strTxt);
                    m_TextView_DP.setTextColor(0xFFFF5555);
                    Log.d(TAG,"MSG_DP_TEST_ERROR");
                }
                break;

                case MSG_KEY_TEST_OK: {
                    String strTxt = getResources().getString(R.string.KEY_Test);
                    if(key_test) {
                        strTxt = strTxt + "    " + getResources().getString(R.string.KEY_Test_1_Ok)
                        + "    " + getResources().getString(R.string.KEY_Test_2_Ok);
                    } else {
                        strTxt = strTxt + "    " + getResources().getString(R.string.Test_Ok);
                    }
                    m_TextView_KEY.setText(strTxt);
                    m_TextView_KEY.setTextColor(0xFF55FF55);
                     key_test_ret = true;
                    Log.d(TAG, "MSG_KEY_TEST_OK");
                }
                break;
                case MSG_KEY_TEST_ERROR: {
                    String strTxt = getResources().getString(R.string.KEY_Test);
                    if(key_test) {
                        if(key_flag == 0x1 || key_flag == 0x5)
                            strTxt = strTxt + "    " + getResources().getString(R.string.KEY_Test_1_Ok);
                        else
                            strTxt = strTxt + "    " + getResources().getString(R.string.KEY_Test_1_Fail);

                        if(key_flag == 0x6)
                            strTxt = strTxt + "    " + getResources().getString(R.string.KEY_Test_2_Ok);
                        else
                            strTxt = strTxt + "    " + getResources().getString(R.string.KEY_Test_2_Fail);
                    } else {
                        strTxt = strTxt + "    " + getResources().getString(R.string.Test_Fail);
                    }
                    m_TextView_KEY.setText(strTxt);
                    m_TextView_KEY.setTextColor(0xFFFF5555);
                    Log.d(TAG, "MSG_KEY_TEST_ERROR");
                }
                break;

                case  MSG_AGEING_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.AGEING_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_AGEING.setText(strTxt);
                    m_TextView_AGEING.setTextColor(0xFF55FF55);
                     ageing_test_ret = true;
                    Log.d(TAG,"MSG_AGEING_TEST_OK");
                }
                break;
                case  MSG_AGEING_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.AGEING_Test) + "    " + getResources().getString(R.string.Test_In);
                    m_TextView_AGEING.setText(strTxt);
                    m_TextView_AGEING.setTextColor(0xFFFF5555);
                    Log.d(TAG,"MSG_AGEING_TEST_ERROR");
                }
                break;

                case  MSG_MCU_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.MCU_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_MCU.setText(strTxt);
                    m_TextView_MCU.setTextColor(0xFF55FF55);
                     mcu_test_ret = true;
                    Log.d(TAG,"MSG_MCU_TEST_OK");
                }
                break;
                case  MSG_MCU_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.MCU_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_MCU.setText(strTxt);
                    m_TextView_MCU.setTextColor(0xFFFF5555);
                    Log.d(TAG,"MSG_MCU_TEST_ERROR");
                }
                break;

                case  MSG_SPIFLASH_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.SPIFLASH_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_SPIFLASH.setText(strTxt);
                    m_TextView_SPIFLASH.setTextColor(0xFF55FF55);
                    spi_test_ret = true;
                    Log.d(TAG,"MSG_SPIFLASH_TEST_OK");
                }
                break;
                case  MSG_SPIFLASH_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.SPIFLASH_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_SPIFLASH.setText(strTxt);
                    m_TextView_SPIFLASH.setTextColor(0xFFFF5555);
                    Log.d(TAG,"MSG_SPIFLASH_TEST_ERROR");
                }
                break;

                case  MSG_PD12_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.PD12_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_PD12.setText(strTxt);
                    m_TextView_PD12.setTextColor(0xFF55FF55);
                    fusb302_test_ret  = true;
                    Log.d(TAG,"MSG_PD12_TEST_OK");
                }
                break;
                case  MSG_PD12_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.PD12_Test) + "    " + getResources().getString(R.string.PD12_Test_Fail);
                    m_TextView_PD12.setText(strTxt);
                    m_TextView_PD12.setTextColor(0xFFFF5555);
                    Log.d(TAG,"MSG_PD12_TEST_ERROR");
                }
                break;
                case  MSG_PD1_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.PD12_Test) + "    " + getResources().getString(R.string.PD1_Test_Fail);
                    m_TextView_PD12.setText(strTxt);
                    m_TextView_PD12.setTextColor(0xFFFF5555);
                    Log.d(TAG,"MSG_PD1_TEST_ERROR");
                }
                break;
                case  MSG_PD2_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.PD12_Test) + "    " + getResources().getString(R.string.PD2_Test_Fail);
                    m_TextView_PD12.setText(strTxt);
                    m_TextView_PD12.setTextColor(0xFFFF5555);
                    Log.d(TAG,"MSG_PD2_TEST_ERROR");
                }
                break;

                case  MSG_GSENSOR_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.Gsensor_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_Gsensor.setText(strTxt);
                    m_TextView_Gsensor.setTextColor(0xFF55FF55);
                     gsensor_test_ret = true;
                    Log.d(TAG,"MSG_GSENSOR_TEST_OK");
                }
                break;
                case  MSG_GSENSOR_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.Gsensor_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_Gsensor.setText(strTxt);
                    m_TextView_Gsensor.setTextColor(0xFFFF5555);
                    Log.d(TAG,"MSG_GSENSOR_TEST_ERROR");
                }
                break;

                case MSG_WIFI_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.Wifi_Test) + "    " + wifiLevel + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_Wifi.setText(strTxt);
                    m_TextView_Wifi.setTextColor(0xFF55FF55);
                     wifi_test_ret = true;
                }
                break;
                case MSG_WIFI_TEST_ERROR:
                {
                    String  strTxt = getResources().getString(R.string.Wifi_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_Wifi.setText(strTxt);
                    m_TextView_Wifi.setTextColor(0xFFFF5555);
                }
                break;

                case MSG_BT_TEST_OK:
                {
                    String strTxt = getResources().getString(R.string.BT_Test) +"    " + btLevel+"    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_BT.setText(strTxt);
                    m_TextView_BT.setTextColor(0xFF55FF55);
                     bt_test_ret = true;
                }
                break;
                case MSG_BT_TEST_ERROR:
                {
                    String strTxt = getResources().getString(R.string.BT_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_BT.setText(strTxt);
                    m_TextView_BT.setTextColor(0xFFFF5555);
                }
                break;

                case MSG_RTC_TEST_OK:
                {
                   String  strTxt = getResources().getString(R.string.Rtc_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_Rtc.setText(strTxt);
                    m_TextView_Rtc.setTextColor(0xFF55FF55);
                    rtc_test_ret = true;
                }
                break;
                case MSG_RTC_TEST_ERROR:
                {
                    String  strTxt = getResources().getString(R.string.Rtc_Test) + "    " + getResources().getString(R.string.Test_Fail);
                    m_TextView_Rtc.setText(strTxt);
                    m_TextView_Rtc.setTextColor(0xFFFF5555);
                }
                break;

                case MSG_NETLED_TEST_Start:
                    tag_net ++;
                    if(tag_net > ledtime){
                   	 mHandler.removeMessages(MSG_NETLED_TEST_Start);
                   	 mHandler.sendEmptyMessage(MSG_NETLED_TEST_End);
                   	 return ;
                   } 
                    Log.d(TAG, "MSG_NETLED_TEST_Start: " + tag_net);
                    if(tag_net % 2 == 1 ){
                    	m_Button_NetLed.setText(getResources().getString(R.string.Led_TestIng)+"!");
                        Tools.writeFile(Tools.Ethernet_Led,"off");
                        mHandler.removeMessages(MSG_NETLED_TEST_Start);
                        mHandler.sendEmptyMessageDelayed(MSG_NETLED_TEST_Start, 1000);
                    }else if(tag_net % 2 == 0){
                    	 m_Button_NetLed.setText(getResources().getString(R.string.Led_TestIng)+"!!");
                        Tools.writeFile(Tools.Ethernet_Led,"default-on");
                        mHandler.removeMessages(MSG_NETLED_TEST_Start);
                        mHandler.sendEmptyMessageDelayed(MSG_NETLED_TEST_Start, 1000);
                    }
                break;
                case MSG_NETLED_TEST_End:
                    tag_net = 0;
                    m_Button_NetLed.setText(getResources().getString(R.string.Led_Test));
                    if(Tools.isNetworkAvailable(MainActivity.this)){
                        Tools.writeFile(Tools.Ethernet_Led,"on");
                    }else{
                        Tools.writeFile(Tools.Ethernet_Led,"default-on");
                    }
                break;

                case MSG_POWERLED_TEST_Start:
                    tag_power ++;
                    if(tag_power > ledtime){
                    	 mHandler.removeMessages(MSG_POWERLED_TEST_Start);
                    	 mHandler.sendEmptyMessage(MSG_POWERLED_TEST_End);
                    	 return ;
                    } 
                    Log.d(TAG, "MSG_POWERLED_TEST_Start: " + tag_power);
                    if(tag_power % 2 == 1){
                    	m_Button_PowerLed.setText(getResources().getString(R.string.PowerKey_TestIng)+"!");
                        Tools.writeFile(Tools.Power_Led,"off");
                        mHandler.removeMessages(MSG_POWERLED_TEST_Start);
                        mHandler.sendEmptyMessageDelayed(MSG_POWERLED_TEST_Start, 1000);
                    }else if(tag_power % 2 == 0){
                    	m_Button_PowerLed.setText(getResources().getString(R.string.PowerKey_TestIng)+"!!");
                        Tools.writeFile(Tools.Power_Led,"on");
                        mHandler.removeMessages(MSG_POWERLED_TEST_Start);
                        mHandler.sendEmptyMessageDelayed(MSG_POWERLED_TEST_Start, 1000);
                    }
                break;
                case MSG_POWERLED_TEST_End:
                    tag_power = 0;
                    m_Button_PowerLed.setText(getResources().getString(R.string.PowerKey_Test));
                    Tools.writeFile(Tools.Power_Led,"on");
                break;

                case MSG_PLAY_VIDEO:
                    mLeftLayout.setVisibility(View.GONE);
                    //mBottomLayout.setVisibility(View.GONE);
                    mBottomLayout2.setVisibility(View.GONE);
                    mBottomLayout3.setVisibility(View.GONE);
                    mBottomLayout4.setVisibility(View.GONE);
                    mBottomLayout5.setVisibility(View.GONE);
                    mBottomLayout6.setVisibility(View.GONE);
                break;
                case MSG_TIME:
                {
                /*    if(bIsKeyDown)
                    {
                        bIsKeyDown = false;
                        mHandler.removeMessages(MSG_TIME);
                        mHandler.sendEmptyMessageDelayed(MSG_TIME, 1 * 1000); 
                    }
                    else*/
                    {
                        mHandler.removeMessages(MSG_TIME);
                        OnScanText();  
                    }
                }
                break;  

                case MSG_TF_TEST_XL_ERROR:
                    {
                        String strTxt = getResources().getString(R.string.TF_Test) + "    " + getResources().getString(R.string.Test_Fail);
                        m_TextView_TF.setText(strTxt);
                        m_TextView_TF.setTextColor(0xFFFF5555);
                    }
                break;
                case MSG_TF_TEST_XL_OK:
                    {
                        String strTxt = getResources().getString(R.string.TF_Test) + "    " + getResources().getString(R.string.Test_Ok);
                        m_TextView_TF.setText(strTxt);
                        m_TextView_TF.setTextColor(0xFF55FF55);
                        tfcard_test_ret = true;
                    }
                break;

                case MSG_USB1_TEST_XL_ERROR:
                    {
                        String strTxt = getResources().getString(R.string.USB1_Test) + "    " + getResources().getString(R.string.Test_Fail);
                        m_TextView_USB1.setText(strTxt);
                        m_TextView_USB1.setTextColor(0xFFFF5555);
                }
                break;
                case MSG_USB1_TEST_XL_OK:
                    {
                        String strTxt = getResources().getString(R.string.USB1_Test) + "    " + getResources().getString(R.string.Test_Ok);
                        m_TextView_USB1.setText(strTxt);
                        m_TextView_USB1.setTextColor(0xFF55FF55);
                        usb20_test_ret = true;
                    }
                break;
                case  MSG_USB2_TEST_XL_ERROR:
                    {
                        String strTxt = getResources().getString(R.string.USB2_Test) + "    " + getResources().getString(R.string.Test_Fail);
                        m_TextView_USB2.setText(strTxt);
                        m_TextView_USB2.setTextColor(0xFFFF5555);
                    }
                break;
                case  MSG_USB2_TEST_XL_OK:
                    {
                    String strTxt = getResources().getString(R.string.USB2_Test) + "    " + getResources().getString(R.string.Test_Ok);
                    m_TextView_USB2.setText(strTxt);
                    m_TextView_USB2.setTextColor(0xFF55FF55);
                    usb30_test_ret = true;
                    }
                break;

                case MSG_GET_CPU_STATUS:
                    m_TextView_CPU_THERMAL.setText(Tools.readFile(Tools.cpu_thermal));
                    m_TextView_CPU_FREQ.setText("0-3:" + Tools.readFile(Tools.cpu0_cpufreq).trim().substring(0, 4) + " 4-7: " + Tools.readFile(Tools.cpu4_cpufreq).trim().substring(0, 4));
                    break;
            case MSG_TEST_RET_UPDATE:
                checkTestRetUpate();
                break;
            }
        }
    }

    private void CheckSameMac(String Scanmac){
        if (Scanmac.equalsIgnoreCase(readMac)) {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.testled), Toast.LENGTH_LONG).show();
            m_mactitle.setText(readMac + "   "+ getResources().getString(R.string.the_same_mac));
            m_mactitle.setTextColor(Color.GREEN);
            Log.e(TAG, "NetLed_Test()");
            m_Button_NetLed.setTag(0);
            mHandler.removeMessages(MSG_NETLED_TEST_Start);
            mHandler.sendEmptyMessage(MSG_NETLED_TEST_Start);

            //Log.e(TAG, "PowerLed_Test()");
            //m_Button_PowerLed.setTag(0);
            //mHandler.removeMessages(MSG_POWERLED_TEST_Start);
            //mHandler.sendEmptyMessage(MSG_POWERLED_TEST_Start);
        } else {
            m_mactitle.setText(Scanmac+"   "+getResources().getString(R.string.the_diff_mac));
            m_mactitle.setTextColor(Color.RED);
            m_maccheck.requestFocus();
        }
    }

    private void OnScanText(){
    
        String strMac = m_maccheck.getText().toString();
        int nLength = m_maccheck.getText().toString().length();

        if(strMac.isEmpty() ) { return; }
        m_maccheck.setText("");

        String strTmpMac = "";

        if(getResources().getInteger(R.integer.config_mac_length) == nLength)
        {
            for (int i = 0; i < nLength; i += 2) {
                strTmpMac += strMac.substring(i, (i + 2) < nLength ? (i + 2) :  nLength );
                if( (i + 2) < nLength) strTmpMac += ':';
            }
            strMac = strTmpMac;
            CheckSameMac(strMac);
        } else if(getResources().getInteger(R.integer.config_mac_length2) == nLength) {
                CheckSameMac(strMac);
        } else {
            strTmpMac = "";
            m_mactitle.setText(strMac+"   "+getResources().getString(R.string.the_diff_mac));
            m_mactitle.setTextColor(Color.RED);
            m_maccheck.requestFocus();
        }
    }

    private static final int BAIDU_READ_PHONE_STATE = 100;
    private static WifiManager mWifiManager;
    private BroadcastReceiver mFactoryReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                updateEthandWifi();
            } else if (action.equals(Intent.ACTION_TIME_TICK) || action.equals(Intent.ACTION_TIME_CHANGED)) {
                updateTime();
            }
        }
    };

    private BroadcastReceiver mountReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Uri uri = intent.getData();
            if (uri.getScheme().equals("file")) {
                if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
                    String path = uri.getPath();
                    Log.d(TAG,"mFactoryReceiver mount patch is "+path);
                        if (path.contains(USB2_PATH)) {
                        List<String> volumes = getVolumes();
                        boolean isUSB1MOUNT = false;
                        for (String volume : volumes) {
                            if (volume.contains(USB1_PATH)) {
                                isUSB1MOUNT = true;
                            }
                        }
                    } else if(path.contains(TFCARD_PATH)) {
                        mHandler.sendEmptyMessage(MSG_TF_TEST_OK);
                    }
                }
            }
        }
    };

    private void updateTime() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd/  E ");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        m_TextView_Time.setText(sdf1.format(new Date()) + sdf2.format(new Date()));
    }

    private void readVersion() {

        String lssue = getResources().getString(R.string.lssue_ver);
        String client = getResources().getString(R.string.client_ver);
        String verfile = getResources().getString(R.string.versionfile);

        File OutputFile = new File(verfile);
        if (!OutputFile.exists()) {
            Toast.makeText(getApplicationContext(),verfile + getResources().getString(R.string.noexist), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            FileInputStream instream = new FileInputStream(verfile);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                Log.d(TAG, "buffreader = " + buffreader.toString());

                String line;
                while( (line = buffreader.readLine() )  !=  null) {
                    if (line.startsWith(lssue)) {
                        lssue_value = line.replace(lssue,"").replace("=", "").trim().toString();
                    }
                    if(line.startsWith(client)) {
                        client_value = line.replace(client, "").replace("=", "").trim().toString();
                    }
                }

                instream.close();
            }
            } catch(FileNotFoundException e) 
            {
                Log.e(TAG, "The File doesn\'t not exist.");
            } catch(IOException e) {
                Log.e(TAG, " readFile error!");
                Log.e(TAG, e.getMessage() );
            }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mHandler.removeMessages(MSG_PLAY_VIDEO);
        //mHandler.sendEmptyMessageDelayed(MSG_PLAY_VIDEO, MSG_PLAY_VIDEO_TIME);
        mLeftLayout.setVisibility(View.VISIBLE);
        //mBottomLayout.setVisibility(View.VISIBLE);
        mBottomLayout2.setVisibility(View.VISIBLE);
        mBottomLayout3.setVisibility(View.VISIBLE);
        mBottomLayout4.setVisibility(View.VISIBLE);
        mBottomLayout5.setVisibility(View.VISIBLE);
        mBottomLayout6.setVisibility(View.VISIBLE);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG,"onKeyUp ----> " + keyCode);
        if(KeyEvent.KEYCODE_VOLUME_DOWN == keyCode) {
            key_flag = key_flag|0x1;
            if(7 == key_flag)
                mHandler.sendEmptyMessage(MSG_KEY_TEST_OK);
            else
                mHandler.sendEmptyMessage(MSG_KEY_TEST_ERROR);
        } else if(KeyEvent.KEYCODE_VOLUME_UP == keyCode) {//Two power keys need to be pressed
            if(4 == key_flag || 5 == key_flag)
                key_flag = key_flag|0x2;
            else
                key_flag = key_flag|0x4;

            if(7 == key_flag)
                mHandler.sendEmptyMessage(MSG_KEY_TEST_OK);
            else
                mHandler.sendEmptyMessage(MSG_KEY_TEST_ERROR);
        }
        return super.onKeyUp(keyCode, event);
    }
}
