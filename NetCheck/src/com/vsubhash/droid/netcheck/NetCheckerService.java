package com.vsubhash.droid.netcheck;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import com.vsubhash.droid.androidwithoutstupid.MvGeneral;
import com.vsubhash.droid.androidwithoutstupid.MvMessages;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NetCheckerService extends Service {
	
	static Timer moTimer;
	static int moINTERVAL = 15;
	static String msURL = "https://www.google.com/";
	static boolean mbIsConnected = false;
	
	MvMessages oink;
	MvGeneral util;
	
	@Override
	public void onCreate() {
		
		oink = new MvMessages(NetCheckerService.this.getApplicationContext(), MainActivity.class);
		util = new MvGeneral(NetCheckerService.this.getApplicationContext());
		
   	moTimer = new Timer();  	
   	moTimer.scheduleAtFixedRate(new TimerTask() {			
			@Override
			public void run() {
				if (checkUrl(msURL)) {
				  if (!mbIsConnected) {
						util.playSound(R.raw.connected);
					}
					MvMessages.logMessage("Connected");
					mbIsConnected = true;
				} else {
					if (mbIsConnected) {
						util.playSound(R.raw.disconnected);
					}
					MvMessages.logMessage("Disconnected");
					mbIsConnected = false;
				}
			}
		}, 1000, moINTERVAL*1000);	
		super.onCreate();
	}
	
	
	static boolean checkUrl(String asUrl) {
		boolean bReturn = false;
    URL oUrl;
    HttpURLConnection oUrlConnection;
     
		try {
			oUrl = new URL(asUrl);
			oUrlConnection = (HttpURLConnection) oUrl.openConnection();			
			oUrlConnection.setConnectTimeout(3*1000);
			oUrlConnection.setReadTimeout(3*1000);
			oUrlConnection.setRequestMethod("HEAD");
			
			MvMessages.logMessage("Checking...");
			oUrlConnection.connect();
			String sContentType = oUrlConnection.getContentType();
			
			if (sContentType != null) {
				MvMessages.logMessage("Found " + sContentType);
				bReturn = true;
			} else {
				MvMessages.logMessage("Found " + null);
			}
			oUrlConnection.disconnect();			
		} catch (Exception e) {
			MvMessages.logMessage("Connection error " + e.getMessage());
			//e.printStackTrace();
		}
    
		return(bReturn);
	}	
	
	
	/**
	 * @see android.app.Service#onBind(Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Put your code here
		return null;
	}
	
}
