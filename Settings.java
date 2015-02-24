package com.vipsoftwares.frndlocfinder;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.gc.materialdesign.widgets.SnackBar;
import com.startapp.android.publish.StartAppAd;
import com.vipsoftwares.frndlocfinder.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Settings extends SherlockActivity{
	
	  SharedPreferences sh;
	   ScrollView scroll;
	   
	
		
	    String DB_NAME = "userdata.db"; //Initialize database Name
	    int DB_VERSION = 1; //Initialize Database Version
	    ClipboardManager myClipboard;
		DBUserData d=new DBUserData(Settings.this, DB_NAME, DB_VERSION);
		
	static String ID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setting);
		
		StartAppAd.showSlider(this);
		
		 SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	     String  ID2 = sharedPreferences.getString("ID", "") ; //Get Copied ID from SharedPreferences
		
	     /**
	      * 
	      * Set SherLock Actionbar properties
	      */
	     
	     getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action2));
		 getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Friend Location Finder</font>"));
		 
		 getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#ffffff'>Add Friend Here</font>"));
	     
		 getSupportActionBar().setHomeButtonEnabled(true);
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 
		final EditText et=(EditText)findViewById(R.id.editText1); //EditText for Name
		
		final EditText et2=(EditText)findViewById(R.id.editText2); //EditText for ID
		
		
		et2.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        if (event.getAction()!=KeyEvent.ACTION_DOWN)
		        	
		            return false;
		        if(keyCode == KeyEvent.KEYCODE_ENTER ){
		         
		        	
		        	if(et2.getText().toString().trim().equals(""))
		        	{
		        		et2.setError("Please Enter Field");
		        	}else
		        	{
		        	
		        	et.setFocusableInTouchMode(true);
		        	et.requestFocus();
		        	}
		        
		        	
		            return true;
		        }
		        return false;
		    }
		});
	
		
		
		et.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        if (event.getAction()!=KeyEvent.ACTION_DOWN)
		        	
		            return false;
		        if(keyCode == KeyEvent.KEYCODE_ENTER ){
		         
		        	
					DBUserData d=new DBUserData(Settings.this, DB_NAME, DB_VERSION);
					
					String name=et.getText().toString();
					String Id=et2.getText().toString();
					
					if(name.trim().equals("") || Id.trim().equals(""))
					{
					/*
					 * Check wheater both fields are entered or not
					 */
						
						AlertDialog.Builder abuild = new AlertDialog.Builder(Settings.this);
						abuild.setMessage("Please Enter Both Fields !!");
						
						et2.setError("Please Enter Field");
						et.setError("Please Enter Field");

						
						abuild.setNeutralButton("OK", new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
						
						
						abuild.setTitle("Error !!");
						abuild.setIcon(android.R.drawable.ic_dialog_alert);
						
						AlertDialog dlg = abuild.create();
						dlg.show();
						
					}else
					{
						d.userid=et.getText().toString(); //get USERID from EditText
						d.phno=et2.getText().toString();  //Get Phone Number from EditText
						
						
						try
						{
							
							DatabaseHandler dd=new DatabaseHandler(Settings.this, DB_NAME,DB_VERSION);
							
							//Insert Contact in database
							String msg=	dd.addContact(et.getText().toString(),et2.getText().toString());
							
							if(msg=="Friend Added")
							{
								Toast.makeText(getApplicationContext(), "Friend Added", Toast.LENGTH_LONG).show();
							}
							else
							{
								AlertDialog.Builder abuild = new AlertDialog.Builder(Settings.this);
								abuild.setMessage("Friend Already Exist !!");
								
								abuild.setNeutralButton("OK", new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
									
									}
								});
								
								
								abuild.setTitle("Error !!");
								abuild.setIcon(android.R.drawable.ic_dialog_alert);
								
								AlertDialog dlg = abuild.create();
								dlg.show();
							}
							
							et.setText(null);
							et2.setText(null);
						
						}catch(Exception e)
						{
							Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_LONG).show();
						}
						
					}
					
		        	
		            return true;
		        }
		        return false;
		    }
		});
	
	
		et.setText(ID2);		
		final Button btn=(Button)findViewById(R.id.ad);
		
		btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
			
				String name=et.getText().toString();
				String Id=et2.getText().toString();
				
				//Check both fields are entered or not and give alert if not entered
				if(name.trim().equals("") || Id.trim().equals(""))
				{
				
					AlertDialog.Builder abuild = new AlertDialog.Builder(Settings.this);
					abuild.setMessage("Please Enter Both Fields !!");
					abuild.setNeutralButton("OK", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					
					
					abuild.setTitle("Error !!");
					abuild.setIcon(android.R.drawable.ic_dialog_alert);
					
					AlertDialog dlg = abuild.create();
					dlg.show();
					
				}else
				{
					d.userid=et.getText().toString();
					d.phno=et2.getText().toString();
					
					try
					{
						
					DatabaseHandler dd=new DatabaseHandler(Settings.this, DB_NAME,DB_VERSION);
					String msg=	dd.addContact(et.getText().toString(),et2.getText().toString());
						
						if(msg=="Friend Added")
						{
							SnackBar snackbar = new SnackBar(Settings.this, "Friend Added", "OK", new OnClickListener() {
								
								public void onClick(View v) {
									// TODO Auto-generated method stub
									
								}
							});
							snackbar.show();
							Toast.makeText(getApplicationContext(), "Friend Added", Toast.LENGTH_LONG).show();
						}
						else
						{
							
							//Give Alert that friend already exist
							
							AlertDialog.Builder abuild = new AlertDialog.Builder(Settings.this);
							abuild.setMessage("Friend Already Exist !!");
							abuild.setNeutralButton("OK", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
								
								}
							});
							
							
							abuild.setTitle("Error !!");
							abuild.setIcon(android.R.drawable.ic_dialog_alert);
							
							AlertDialog dlg = abuild.create();
							dlg.show();
						}
						
						et.setText(null);
						et2.setText(null);
					
					}catch(Exception e)
					{
						Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
					
						
				}
				
			
				
			}
		});
		
	
	}
	
	public String getID()
	{
		
		ID=sh.getString("ID","");
		
		return ID;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
	getSupportMenuInflater().inflate(R.menu.scanmenu,menu); //Inflate SCAN Menu
	
		return true;
	}
	
	  @Override
		public boolean onOptionsItemSelected(
				com.actionbarsherlock.view.MenuItem item) {
			// TODO Auto-generated method stub
			  
			  if(item.getItemId()==android.R.id.home)
			  {
				  finish();
			  }else if(item.getItemId()==R.id.clickscan)
			  {
				  Intent i=new Intent(Settings.this,Scan.class);
				  startActivity(i);
			  }
			  
			return super.onOptionsItemSelected(item);
		}

}
