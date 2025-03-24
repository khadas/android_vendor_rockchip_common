package cn.com.factorytest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.provider.Settings;
import android.view.KeyEvent;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;

import java.util.Arrays;

import android.os.Build;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class WriteMacActivity extends Activity {
    private static final String TAG = "FactoryTest";
    private Context mContext;

    EditText m_EditMac;
    TextView m_MacAddr;
    TextView m_SnAddr;
    TextView m_UsidAddr;
    TextView m_DeviceidAddr;

    TextView m_MacAddr_Title;
    TextView m_SnAddr_Title;
    TextView m_UsbMacAddr_Title;
    TextView m_PcieMacAddr_Title;
    TextView m_UsidAddr_Title;
    TextView m_DeviceidAddr_Title;

	private boolean bIsKeyDown = false;

	private boolean MAC_SHOW = false;
	private boolean SN_SHOW = true;
	private boolean USID_SHOW = false;
	private boolean DEVICE_ID_SHOW = false;
	private boolean WriteMac_ok_flag = false;
	private boolean WriteSn_ok_flag = false;
	private int MAC_LENGTH = 17;
	private Button success, fail;

	private final int MSG_TIME = 777;
	private TimeHandler mHandler = new TimeHandler();
	private class TimeHandler extends Handler {
    	@Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TIME: {
                    if (bIsKeyDown) {
                        bIsKeyDown = false;
                        mHandler.removeMessages(MSG_TIME);
                        mHandler.sendEmptyMessageDelayed(MSG_TIME, 1 * 1000);
                    } else {
                        mHandler.removeMessages(MSG_TIME);
                        OnScanText();
                    }
                }
                break;
            }
        }    	
    }
    
	private void OnScanText() {
		int nTextlen = m_EditMac.getText().toString().length();
		String strMac = m_EditMac.getText().toString();
		if (strMac.isEmpty()) {
			return;
		}

		if (getResources().getInteger(R.integer.config_mac_length2) == nTextlen && (':' == strMac.charAt(2)) && (':' == strMac.charAt(5))) {
			OnWriteMac(true);
		} else if (getResources().getInteger(R.integer.config_mac_length) == nTextlen) {
			OnWriteMac(true);
		} else if(getResources().getInteger(R.integer.config_usid_length) == nTextlen) {
			OnWriteUsid();
		} else if(getResources().getInteger(R.integer.config_sn_length) == nTextlen) {
			OnWriteSn();
		} else if(getResources().getInteger(R.integer.config_deviceid_length) == nTextlen) {
			OnWriteDeviceid();
		} else {
			m_EditMac.setText("");
			m_EditMac.requestFocus();

//			Toast.makeText(this, R.string.ScanError, Toast.LENGTH_SHORT).show();
		}
	}

	public void OnWriteMac(boolean is_otp)
	{
		Log.e(TAG, "public void OnWriteMac()");
		
		String strMac = m_EditMac.getText().toString();
		if(strMac.isEmpty() ) { return; }
		Log.e(TAG, "is_otp = " + is_otp+ " strMac : " + strMac);
		
		WriteMac(strMac);
		if (is_otp) {
			ShowMac_OTP();
		} else {
			ShowMac();
		}
				
		m_EditMac.setText("");
		m_EditMac.requestFocus();
		
		if(WriteMac_ok_flag)
			this.finish();
	}
	
	public void OnWriteSn() {
		Log.d(TAG, "public void OnWriteSn()");
		
		String strSn = m_EditMac.getText().toString();
		if(strSn.isEmpty() ) {
			return;
		}
		Log.d(TAG, "strSn : " + strSn);
		
		WriteSn(strSn);
		strSn =  Tools.readFile(Tools.Key_OTP_Sn);
		ShowSn(strSn);
		
		m_EditMac.setText("");
		m_EditMac.requestFocus();

		Log.d(TAG, "WriteSn_ok_flag:" + WriteSn_ok_flag);
		if(WriteSn_ok_flag)
				this.finish();
	}
	
	public void OnWriteUsid() {
		Log.e(TAG, "public void OnWriteUsid()");
		
		String strUsid = m_EditMac.getText().toString();
		if(strUsid.isEmpty() ) { return; }
		Log.e(TAG, "strUsid : " + strUsid);
		//截取前20位
		//805写入usid不管多少位，最终生成的usid都会自动加上mac的12位
		//905写入的usid他不会自动去加mac了 所以不用截取前20位
		String newstrUsid = strUsid.substring(0, 20);
		Log.e(TAG, "newstrUsid : " + newstrUsid);
		if(Tools.isGxbaby()){
			WriteUsid(strUsid);
		} else {
			WriteUsid(newstrUsid);
		}
		ShowUsid();	
		
		m_EditMac.setText("");
		m_EditMac.requestFocus();
	}
	
	public void OnWriteDeviceid()
	{
		Log.e(TAG, "public void OnWriteDeviceid()");
		
		String strDeviceid = m_EditMac.getText().toString();
		if(strDeviceid.isEmpty() ) { return; }
		Log.e(TAG, "strDeviceid : " + strDeviceid);
		
		WriteDeviceid(strDeviceid);
		ShowDeviceid();	
		
		m_EditMac.setText("");
		m_EditMac.requestFocus();
	}
	
	public void WriteMac(String strMac)
	{	

		if (getResources().getBoolean(R.bool.config_write_mac_in_otp)) {
			if (strMac.length() == 17) {
				if((':' == strMac.charAt(2) ) && (':' == strMac.charAt(5) ) && (':' == strMac.charAt(8) ) && (':' == strMac.charAt(11) ) && (':' == strMac.charAt(14) ) ) {
					String mac = strMac.replaceAll(":","");
					int length = mac.length();
					boolean format_err = true;
					Log.e(TAG, "OTP MAC= " + mac);
					for (int i=0; i< length; i++) {
						int value = (int)mac.charAt(i);
						if(((value > 0x2f) && (value < 0x3a)) || ((value > 0x40) && (value < 0x47)) || ((value > 0x60) && (value < 0x67))) {
							if (i == 1) {
								if (value > 0x2f && value < 0x3a) {
									if (value%2 == 1) {
										format_err = true;
										break;
									}
								} else {
									if (value%2 == 0) {
										format_err = true;
										break;
									}
								}
							}
							format_err = false;
						} else {
							format_err = true;
							break;
						}

					}
					Log.d(TAG,"MAC ="+ mac + " format_err= "+format_err);
					if (!format_err) {
						Tools.writeFile(Tools.Key_OTP_Mac, mac);
						WriteMac_ok_flag = true;
					}
				}
			}
			return;
		}

		Tools.writeFile(Tools.Key_Name, Tools.Key_Mac);
		
		String strTmpMac = "";
		int nLength = strMac.length();
		
		if(getResources().getInteger(R.integer.config_mac_length) == nLength) {
			for(int i = 0; i < nLength; i += 2) {
				strTmpMac += strMac.substring(i, (i + 2) < nLength ? (i + 2) :  nLength );
				
				if( (i + 2) < nLength) strTmpMac += ':';
			}
		} else if(getResources().getInteger(R.integer.config_mac_length2) == nLength) {
			if( (':' == strMac.charAt(2) ) && (':' == strMac.charAt(5) ) && (':' == strMac.charAt(8) ) && (':' == strMac.charAt(11) ) && (':' == strMac.charAt(14) ) ) {
				strTmpMac = strMac;
			} else {
				strTmpMac = "";
			}				
		} else {
			strTmpMac = "";
		}
		
		Log.e(TAG, "strTmpMac : " + strTmpMac);
		
		String strNewMac = CHexConver.str2HexStr(strTmpMac);
		Log.e(TAG, "strNewMac : " + strNewMac);
		Tools.writeFile(Tools.Key_Write,  strNewMac);
	}
	
	public void WriteSn(String strSn)
	{
		String strSn_val = "";
		if (strSn.length() == getResources().getInteger(R.integer.config_sn_length)){
			boolean format_err = true;
			Log.d(TAG, "OTP Sn= " + strSn);
			for (int i=0; i<getResources().getInteger(R.integer.config_sn_length); i++) {
				int value = (int)strSn.charAt(i);
				Log.d(TAG, "Sn value= " + value + "i= " + i);

				if(((value > 0x2f) && (value < 0x3a)) || ((value > 0x40) && (value < 0x47)) || ((value > 0x60) && (value < 0x67)))
					format_err = false;
				else
					format_err = true;

				if(format_err)
					break;
			}
			Log.d(TAG,"SN =" + strSn + " format_err= " + format_err);
			strSn_val = "echo " + strSn + " > " + Tools.Key_OTP_Sn;
			if (!format_err) {
				Tools.exec(strSn_val);
			}
		}
	}
	
	public static void WriteUsid(String strUsid)
	{	
		Tools.writeFile(Tools.Key_Name, Tools.Key_Usid);
		String strNewUsid = CHexConver.str2HexStr(strUsid);
		Log.e(TAG, " : " + strNewUsid);
		 Tools.writeFile(Tools.Key_Write, strNewUsid);		 
	}
	
	public static void WriteDeviceid(String strDeviceid)
	{	
		Tools.writeFile(Tools.Key_Name, Tools.Key_Deviceid);
		String strNewDeviceid = CHexConver.str2HexStr(strDeviceid);
		Log.e(TAG, " : " + strNewDeviceid);
		 Tools.writeFile(Tools.Key_Write, strNewDeviceid);		 
	}
	
	public void ShowMac()
	{	
		Tools.writeFile(Tools.Key_Name, Tools.Key_Mac);
		String strMac =  Tools.readFile(Tools.Key_Read);
		
		Log.e(TAG, "strMac : " + strMac  + ";  length    : " + strMac.length() );				
		m_MacAddr.setText(CHexConver.hexStr2Str(strMac) );
	}

	public void ShowMac_OTP()
	{
		String strTmpMac = "";
		String strMac = Tools.readFile(Tools.Key_OTP_Mac);
		Log.e(TAG, "strMac : " + strMac  + ";  length    : " + strMac.length() );

		int length = strMac.length();
		if (length != 12) {
			m_MacAddr.setTextColor(Color.RED);
			m_MacAddr.setText("ERR");

		} else {
			for(int i = 0; i < length; i += 2) {

				strTmpMac += strMac.substring(i, (i + 2) < length ? (i + 2) :  length );
				if( (i + 2) < length) strTmpMac += ':';
				}
				m_MacAddr.setText(strTmpMac);
		}
	}
	
	public void ShowSn()
	{
		String strSn =  Tools.readFile(Tools.Key_OTP_Sn);
		
		Log.e(TAG, "strSn : " + strSn);
		m_SnAddr.setText(strSn);
	}

	public void ShowSn(String value)
	{
		String strSn =  Tools.readFile(Tools.Key_OTP_Sn);

		Log.d(TAG, "strSn : " + strSn);
		m_SnAddr.setText(strSn);
		WriteSn_ok_flag = value.contains(strSn);
		Log.d(TAG, "WriteSn_ok_flag : " + WriteSn_ok_flag);
	}
	
	public void ShowUsid()
	{
		Tools.writeFile(Tools.Key_Name, Tools.Key_Usid);
		String strUsid =  Tools.readFile(Tools.Key_Read);
		
		Log.e(TAG, "strUsid : " + strUsid);
		m_UsidAddr.setText(CHexConver.hexStr2Str(strUsid) );
	}
	
	public void ShowDeviceid()
	{
		Tools.writeFile(Tools.Key_Name, Tools.Key_Deviceid);
		String strDeviceid =  Tools.readFile(Tools.Key_Read);
		
		Log.e(TAG, "strDeviceid : " + strDeviceid);
		m_DeviceidAddr.setText(CHexConver.hexStr2Str(strDeviceid) );
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_mac);
		WriteMac_ok_flag = false;
		WriteSn_ok_flag = false;
		mContext = this;
		m_EditMac = (EditText)findViewById(R.id.EditTextMac);		
		m_EditMac.setInputType(InputType.TYPE_NULL);
		m_EditMac.addTextChangedListener(mTextWatcher);

		m_MacAddr = (TextView)findViewById(R.id.TextView_mac);
		m_MacAddr_Title = (TextView)findViewById(R.id.TextView_mac_title);
		m_MacAddr_Title.setText(m_MacAddr_Title.getText().toString() 
				+ "\t\t\t" + getResources().getInteger(R.integer.config_mac_length) + getResources().getString(R.string.showLength) );

		if (!MAC_SHOW) {
			m_MacAddr_Title.setVisibility(View.GONE);
			m_MacAddr.setVisibility(View.GONE);
		}

		m_SnAddr = (TextView)findViewById(R.id.TextView_sn);
		m_SnAddr_Title = (TextView)findViewById(R.id.TextView_sn_title);
		m_SnAddr_Title.setText(m_SnAddr_Title.getText().toString() 
				+ "\t\t\t" + getResources().getInteger(R.integer.config_sn_length) + getResources().getString(R.string.showLength) );

		if (!SN_SHOW) {
			m_SnAddr_Title.setVisibility(View.GONE);
			m_SnAddr.setVisibility(View.GONE);
		}

		m_UsidAddr = (TextView)findViewById(R.id.TextView_usid);
		m_UsidAddr_Title = (TextView)findViewById(R.id.TextView_usid_title);
		m_UsidAddr_Title.setText(m_UsidAddr_Title.getText().toString() 
				+ "\t\t\t" + getResources().getInteger(R.integer.config_usid_length) + getResources().getString(R.string.showLength) );

		if (!USID_SHOW) {
			m_UsidAddr_Title.setVisibility(View.GONE);
			m_UsidAddr.setVisibility(View.GONE);
		}

		m_DeviceidAddr = (TextView)findViewById(R.id.TextView_deviceid);
		m_DeviceidAddr_Title = (TextView)findViewById(R.id.TextView_deviceid_title);
		m_DeviceidAddr_Title.setText(m_DeviceidAddr_Title.getText().toString()
				+ "\t\t\t" + getResources().getInteger(R.integer.config_deviceid_length) + getResources().getString(R.string.showLength) );

		if (!DEVICE_ID_SHOW) {
			m_DeviceidAddr_Title.setVisibility(View.GONE);
			m_DeviceidAddr.setVisibility(View.GONE);
		}

		if (getResources().getBoolean(R.bool.config_write_mac_in_otp)) {
			String str_mac = Tools.readFile(Tools.Key_OTP_Mac);
			ShowMac_OTP();
			ShowSn();

		} else {
			if(Tools.isGxbaby()){
				Tools.writeFile(Tools.Key_Attach, Tools.Key_Attach_Value);
			}
			String strKeyList = Tools.readFile(Tools.Key_List);
			
			Log.e(TAG, strKeyList);
			if(-1 != strKeyList.indexOf(Tools.Key_Mac) ) {
				ShowMac();
			}			
			if(-1 != strKeyList.indexOf(Tools.Key_Sn) ) {
				ShowSn();
			}			
			if(-1 != strKeyList.indexOf(Tools.Key_Usid) ) {
				ShowUsid();
			}
			if(-1 != strKeyList.indexOf(Tools.Key_Deviceid) ) {
				ShowDeviceid();
			}
		}
		success = (Button) findViewById(R.id.btn_success);
		success.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Settings.System.putInt(mContext.getContentResolver(), "Khadas_write_mac_usid_test", 1);
				finish();
			}
		});

		fail = (Button) findViewById(R.id.btn_fail);
		fail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Settings.System.putInt(mContext.getContentResolver(), "Khadas_write_mac_usid_test", 0);
				finish();
			}
		});
	}
	
	TextWatcher mTextWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			bIsKeyDown = true;
			mHandler.sendEmptyMessageDelayed(MSG_TIME, 1 * 1000);
		}
	};

	public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
}
