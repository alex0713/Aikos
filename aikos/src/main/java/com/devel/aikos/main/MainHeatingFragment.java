package com.devel.aikos.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;

public class MainHeatingFragment extends Fragment {
	
//	ListView listviewMainHeating = null;

	TextView textviewHeatingChangeProg = null;
    TextView textviewHeatingBottomProg = null;

    TextView[] textViewZoneName = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_main_heating, container, false);
		
        textviewHeatingChangeProg = (TextView) view.findViewById(R.id.textviewHeatingChangeProg);
        textviewHeatingBottomProg = (TextView) view.findViewById(R.id.textviewHeatingBottomProg);

        textviewHeatingChangeProg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goProgramme();
			}
		});


        textViewZoneName = new TextView[] {
                (TextView) view.findViewById(R.id.textviewZoneName1),
                (TextView) view.findViewById(R.id.textviewZoneName2),
                (TextView) view.findViewById(R.id.textviewZoneName3)
        };
        for (int i = 0; i < AikosGlobals.getZones(0).size(); i++) {
            if (textViewZoneName.length <= i) break;
            textViewZoneName[i].setText(AikosGlobals.getZones(0).get(i).getName());
        }
		
//		listviewMainHeating = (ListView) view.findViewById(R.id.listviewMainHeating);
//		listviewMainHeating.setAdapter(new HeatingListAdapter());
		
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		refreshView();
		
		super.onResume();
			
	}
	
	public void refreshView() {
        textviewHeatingBottomProg.setText(AikosGlobals.getActiveProgramName());
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}

	void goProgramme() {
		Intent i = new Intent(getActivity(), ProgrammeActivity.class);
		getActivity().startActivity(i);
	}

}
