package cn.com.factorytest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemProperties;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import java.util.List;

import android.os.storage.VolumeInfo;
import android.os.storage.StorageVolume;
import android.os.storage.DiskInfo;


public class Tools {
	public static final String TAG = "FactoryTest";
	
	public static final String Gxbaby_platform_name = "gxbaby";
	public static final String Gxl_platform_name  = "gxl";
	public static final String Gxm_platform_name  = "gxm";

    public static final String Key_List =(isGxbaby()?"/sys/class/unifykeys/list":"/sys/class/aml_keys/aml_keys/key_list");
    public static final String Key_Name = (isGxbaby()?"/sys/class/unifykeys/name":"/sys/class/aml_keys/aml_keys/key_name");
    public static final String Key_Read = (isGxbaby()?"/sys/class/unifykeys/read":"/sys/class/aml_keys/aml_keys/key_read");
    public static final String Key_Write = (isGxbaby()?"/sys/class/unifykeys/write":"/sys/class/aml_keys/aml_keys/key_write");
    //public static final String Key_OTP_Mac = "/sys/class/w25q128fw/mac_addr";
    public static final String Key_OTP_Mac = "/sys/class/mcu/mac_addr";
    public static final String Key_OTP_Sn = "/sys/class/mcu/sn_addr";
    public static final String Key_OTP_Usid = "/sys/class/mcu/usid";

    public static final String Key_Attach = "/sys/class/unifykeys/attach";
    public static final String Key_Attach_Value = "1";
    
    public static final String Key_Mac = "mac";
    public static final String Key_Usid = "usid";
    public static final String Key_Deviceid = "deviceid";
    public static final String Key_Result = "result";
    public static final String Key_Sn = "sn";
    public static final int KHADAS_UNKNOW = 0;
    public static final int KHADAS_EDGE = 1;
    public static final int KHADAS_EDGEV = 2;
    public static final int KHADAS_CAPTAIN = 3;
    public static final String KHADAS_EDGE_STR = "Edge";
    public static final String KHADAS_EDGEV_STR = "EdgeV";
    public static final String KHADAS_CAPTAIN_STR = "Captain";

    //public static  final String Ethernet_Led = "/proc/ledlight/netled/state";
    public static  final String Power_Led = "/proc/ledlight/powerled/state";
	public static  final String Ethernet_Led = "/sys/class/leds/led-sys/trigger";
    public static  final String Ethernet_status = "/sys/class/net/eth0/operstate";
	//public static  final String White_Led = "/sys/class/leds/sys_led/trigger";
	public static  final String Red_Led = "/sys/class/leds/red_led/trigger";
    public static  final String Green_Led = "/sys/class/leds/green_led/trigger";
    public static  final String Blue_Led = "/sys/class/leds/blue_led/trigger";
	public static  final String Rtc_time = "/sys/class/rtc/rtc0/time";
	public static  final String ageing_status = "/sys/class/mcu/ageing_test";
    public static  final String Edge2_IO = "/sys/class/es8316";

    public static final String cpu_thermal = "/sys/class/thermal/thermal_zone0/temp";
    public static final String cpu0_cpufreq = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_cur_freq";
    public static final String cpu4_cpufreq = "/sys/devices/system/cpu/cpu4/cpufreq/cpuinfo_cur_freq";

    public static String exec(String command) {

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

    public static String execCommand(String[] command) throws IOException {
        // start the ls command running
        //String[] args =  new String[]{"sh", "-c", command};
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(command);
        InputStream inputstream = proc.getInputStream();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        String line = "";
        String sb = "";
        while ((line = bufferedreader.readLine()) != null) {
            sb = sb + line;
        }
        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        return sb;
    }
	public static String readFile(String file) {
        String content = "";
        File OutputFile = new File(file);
        if (!OutputFile.exists() ) {
            return content;
        }
        
        try {
            FileInputStream instream = new FileInputStream(file);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                
                Log.d(TAG, "buffreader = " + buffreader.toString());
                
                String line;
                while( (line = buffreader.readLine() )  !=  null) {
                        content = content + line;

                }
                
                instream.close();
            }
        } catch(FileNotFoundException e) {
            Log.e(TAG, "The File doesn\'t not exist.");
        } catch(IOException e) {
            Log.e(TAG, " readFile error!");
            Log.e(TAG, e.getMessage() );
        }
        
        Log.d(TAG, " : " + content);
        
        return content;
    }

    public static void writeFile(String file, String value) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(value);
            fw.close();
        } catch (IOException e) {
            Log.e(TAG, e.toString() );
        }
    }

    public static String getUsid() {
        String temp = readFile(Tools.Key_OTP_Usid);
        Log.e("hlm", "temp=" + temp);
        if (temp.contains("0x00: 00 00 00 00 00 00 00 00 00 00")) {
            return "0000000000";
        }
        /*String srtUsid = asciiToString(decodeHEX(temp.split("\\s+")[1])) +
                asciiToString(decodeHEX(temp.split("\\s+")[2])) +
                asciiToString(decodeHEX(temp.split("\\s+")[3])) +
                asciiToString(decodeHEX(temp.split("\\s+")[4])) +
                asciiToString(decodeHEX(temp.split("\\s+")[5])) +
                asciiToString(decodeHEX(temp.split("\\s+")[6])) +
                asciiToString(decodeHEX(temp.split("\\s+")[7])) +
                asciiToString(decodeHEX(temp.split("\\s+")[8])) +
                asciiToString(decodeHEX(temp.split("\\s+")[9])) +
                asciiToString(decodeHEX(temp.split("\\s+")[10]));*/

        String srtUsid = temp.split("\\s+")[1].substring(1,2) +
                        temp.split("\\s+")[2].substring(1,2) +
                        temp.split("\\s+")[3].substring(1,2) +
                        temp.split("\\s+")[4].substring(1,2) +
                        temp.split("\\s+")[5].substring(1,2) +
                        temp.split("\\s+")[6].substring(1,2) +
                        temp.split("\\s+")[7].substring(1,2) +
                        temp.split("\\s+")[8].substring(1,2) +
                        temp.split("\\s+")[9].substring(1,2) +
                        temp.split("\\s+")[10].substring(1,2);

        Log.e(TAG, "srtUsid=" + srtUsid);
        return srtUsid;
    }

    /*public static int getBoardType()
    {
        int type = KHADAS_UNKNOW;
	String str = readFile("/sys/class/board/type");
        if (str.equals("1"))
            type = KHADAS_EDGE;
        else if (str.equals("2"))
            type = KHADAS_EDGEV;
        else if (str.equals("3"))
            type = KHADAS_CAPTAIN;
        else
            type = KHADAS_UNKNOW;
        return type;
    }*/

    public static boolean isEthUp()
    {
        String status = Tools.readFile(Ethernet_status);
        if(status == null)
        {
            return false;
        }
        else if(status.equals("down") )
        {
            return false;
        }
        else if(status.equals("up") )
        {
			try {
				Process mProcess = Runtime.getRuntime().exec("cmdclient "+"ifconfig");
				BufferedReader mInputReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
				String line;
				while ((line = mInputReader.readLine()) != null) {
					if (line.contains("192.168")) {
						return true;
					}
				}
				mInputReader.close();
				mProcess.destroy();
			} catch (IOException e) {
				e.printStackTrace();
			}			
        }

        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isEthConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info.isConnected()) {
            return true;
        }

        return false;
    }
    
    public static long getmem_TOLAL() {
        long mTotal;
        // /proc/meminfo读出的内核信息进行解释
        String path = "/proc/meminfo";
        String MemTotal = "";
        String Cached = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("MemTotal"))
                	MemTotal = line;
                if(line.startsWith("Cached"))
                	Cached = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
      
        MemTotal = MemTotal.replace("MemTotal:", "").replace("kB", "").trim();
        Cached = Cached.replace("Cached:", "").replace("kB", "").trim();
        
//        mTotal = Integer.parseInt(MemTotal)+Integer.parseInt(Cached);
        mTotal = Integer.parseInt(MemTotal);
        return mTotal;
    }

    public static String getMemSize() {
        long mTotal;
        String path = "/proc/meminfo";
        String MemTotal = "";
        String Cached = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("MemTotal"))
                    MemTotal = line;
                if (line.startsWith("Cached"))
                    Cached = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        MemTotal = MemTotal.replace("MemTotal:", "").replace("kB", "").trim();
        Cached = Cached.replace("Cached:", "").replace("kB", "").trim();

        mTotal = Integer.parseInt(MemTotal);
        return (mTotal * 100 / 1024 / 1024 / 100.0) + " GB";
    }
    
    public static String getRomSize(Context cnt){
    	 File path = Environment.getDataDirectory();  
         StatFs stat = new StatFs(path.getPath());  
         long blockCount = stat.getBlockCount();  
         long blockSize = stat.getBlockSize();  
         long availableBlocks = stat.getAvailableBlocks();  

         String dataSize = Formatter.formatFileSize(cnt, blockCount*blockSize);
         Log.d("FactoryTest",dataSize);
		 return dataSize;
    }
    
    public static boolean isAndroid5_1_1(){
    	return Build.VERSION.RELEASE.equals("5.1.1");
    }
    public static boolean isGxbaby(){
    	return Gxbaby_platform_name.equals(SystemProperties.get("ro.board.platform"))
					|| Gxl_platform_name.equals(SystemProperties.get("ro.board.platform"))
					|| Gxm_platform_name.equals(SystemProperties.get("ro.board.platform"));
    }
	
	/**
	 * 判断是否android6.0.1
	 * @return
	 */
	public static boolean isAndroid6_0_1() {
		return Build.VERSION.RELEASE.equals("6.0.1");
	}
	
	  /**
     * 判断android6.0识别是否USB
     * return List<Boolean> 1:TF 2:usb1 3:usb2
     */
	private static StorageManager mStorageManager;
	public static Boolean[]  isUsbOrSd(Context context) {
		Boolean[] deviceArray = new Boolean[]{false,false,false};
		int usbNumber = 0;
		int tfNumber = 0;
		mStorageManager = (StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE);
		List<VolumeInfo> volumes = mStorageManager.getVolumes();
		for (VolumeInfo vol : volumes) {
			if (vol != null && vol.isMountedReadable()
					&& vol.getType() == VolumeInfo.TYPE_PUBLIC) {

				DiskInfo disk = vol.getDisk();
				if (disk.isUsb()) {
					usbNumber++;
//					return true;//usb
				} else {
					tfNumber++;
//					return false;//sd
				}
			}
		}
		
		if(tfNumber > 0){
			deviceArray[0] = true;
		}else{
			deviceArray[0] = false;
		}
		
		if(usbNumber>0){
			deviceArray[1] = true;
			if(usbNumber > 1){
				deviceArray[2] = true;
			}else{
				deviceArray[2] = false;
			}
		}else{
			deviceArray[1] = false;
			deviceArray[2] = false;
		}
		
		return deviceArray;
	}
    
}
