package com.devel.aikos.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;

public class MainHotwaterFragment extends Fragment {

	ListView listviewMainHeating = null;
	
	TextView textviewHeatingBottom1 = null;
	TextView textviewHeatingBottom2 = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_main_hotwater, container, false);
		
		textviewHeatingBottom1 = (TextView) view.findViewById(R.id.textviewHeatingBottom1);
		textviewHeatingBottom2 = (TextView) view.findViewById(R.id.textviewHeatingBottom2);
		
		textviewHeatingBottom1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		textviewHeatingBottom2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goProgramme();
			}
		});
		
		
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		refreshView();
		
		super.onResume();
			
	}
	
	public void refreshView() {
		if (textviewHeatingBottom1 != null) {
			String first = getActivity().getResources().getString(R.string.string_curr_active_prog___) + "  ";
			Spannable sp = new SpannableString(first + AikosGlobals.getActiveProgramName());
			StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
			sp.setSpan(boldSpan, first.length(), sp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			textviewHeatingBottom1.setText(sp);
		}
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}

	void goProgramme() {
        Intent i = new Intent(getActivity(), IntraDayActivity.class);
        i.putExtra(IntraDayActivity.EXTRA_KET_TEMPOBJ_ZONE_INDEX, IntraDayActivity.EXTRA_KET_TEMPOBJ_ZONE_HOTWATER);
        getActivity().startActivity(i);
	}

	
}
