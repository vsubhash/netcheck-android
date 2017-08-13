package com.vsubhash.droid.netcheck;

import com.vsubhash.droid.androidwithoutstupid.MvMessages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	Intent oNetCheckerServiceIntent, oAboutIntent;
	MvMessages oink;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        oink = new MvMessages(MainActivity.this);
        
        if (oNetCheckerServiceIntent == null) {
        	oNetCheckerServiceIntent = new Intent(MainActivity.this.getApplicationContext(), NetCheckerService.class);
        	startService(oNetCheckerServiceIntent);
        } 
        
    }
    
    public void handleOnClickView(View aoClickedView) {
    	switch (aoClickedView.getId()) {
    		case R.id.btnExit:
        	MainActivity.this.finish();
    			break;
    		case R.id.btnAbout:
    			oAboutIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vsubhash.com"));
        	startActivity(oAboutIntent);
    			break;
    		default:
    			stopService(oNetCheckerServiceIntent);
        	MainActivity.this.finish();
    			break;    		
    	}
    	
    	
    }
}
