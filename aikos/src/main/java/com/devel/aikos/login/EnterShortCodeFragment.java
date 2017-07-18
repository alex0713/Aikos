package com.devel.aikos.login;

import com.devel.aikos.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EnterShortCodeFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_login_enter_short_code, container, false);
		
		((Button)view.findViewById(R.id.buttonnterShortCodeOK)).setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				checkShortCode();
			}
		});
		
		return view;
		
	}
	
	void checkShortCode() {
		getActivity().finish();
	}
	
}
