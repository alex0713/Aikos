package com.devel.aikos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.devel.aikos.login.EnterShortCodeFragment;
import com.devel.aikos.login.JoinFragment;
import com.devel.aikos.login.LoadingFragment;
import com.devel.aikos.login.LoginFragment;

public class LoginActivity extends FragmentActivity {
	
	private static int FRAGMENT_LOGIN = 0;
	private static int FRAGMENT_LOADING = 1;
	private static int FRAGMENT_JOIN = 2;
	private static int FRAGMENT_INPUT_CODE = 3;
	
	private int currentFragment = FRAGMENT_LOGIN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if (currentFragment == FRAGMENT_LOADING) {
			getMenuInflater().inflate(R.menu.action_menu_loading, menu);
		}		
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	        	onBackPressed();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		showFirstPage();
	}
	
	private void doLogin() {
		
		new AsyncTask<Void, Boolean, Void>() {
			   @Override
			   protected void onPreExecute() {
				   super.onPreExecute();
			   }

			   @Override
			   protected Void doInBackground(Void... arg0) {
				   //TODO real login action
				   try {
					   Thread.sleep(1000);
				   } catch (InterruptedException e) {
					   e.printStackTrace();
				   }
				   return null;
			   }
			   
			   @Override
			   protected void onPostExecute(Void result) {
				   if (currentFragment == FRAGMENT_LOADING) {
					   setResult(AikosMainActivity.ACTIVITY_RESULT_LOGIN_OK);
					   loginOK();
				   }
			   }

		}.execute();
		
	}
	
	void loginOK() {

		getActionBar().show();
		
		if (getFragmentManager().getBackStackEntryCount() > 1) {
			getFragmentManager().popBackStack();
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.login_content_fragment, new EnterShortCodeFragment(), "Join");
		ft.addToBackStack(null);
		ft.commit();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		currentFragment = FRAGMENT_INPUT_CODE;
		invalidateOptionsMenu();
	}

	void showFirstPage() {
		
		getActionBar().hide();
		
		if (getFragmentManager().getBackStackEntryCount() > 1) {
			getFragmentManager().popBackStack();
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.login_content_fragment, new LoginFragment(), "Login");
		ft.addToBackStack(null);
		ft.commit();
		
		getActionBar().setDisplayHomeAsUpEnabled(false);
		
		currentFragment = FRAGMENT_LOGIN;
		
	}
	
	public void onLogin(View view) {
		
		doLogin();
		
		getActionBar().show();
		
		if (getFragmentManager().getBackStackEntryCount() > 1) {
			getFragmentManager().popBackStack();
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.login_content_fragment, new LoadingFragment(), "Loading");
		ft.addToBackStack(null);
		ft.commit();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		currentFragment = FRAGMENT_LOADING;
		invalidateOptionsMenu();
	}
	
	public void onGoJoin(View view) {
		
		getActionBar().show();
		
		if (getFragmentManager().getBackStackEntryCount() > 1) {
			getFragmentManager().popBackStack();
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.login_content_fragment, new JoinFragment(), "Join");
		ft.addToBackStack(null);
		ft.commit();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		currentFragment = FRAGMENT_JOIN;
		invalidateOptionsMenu();
		
	}

	public void onGoForgotPassword(View view) {
		
	}

	@Override
	public void onBackPressed() {
		if (currentFragment == FRAGMENT_LOGIN) {
			finish();
		} else {
			showFirstPage();
		}
		
	}
	
	
}
