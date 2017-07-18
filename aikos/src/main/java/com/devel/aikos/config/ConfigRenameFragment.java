package com.devel.aikos.config;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;

public class ConfigRenameFragment extends Fragment {
	
	TextView textviewConfRenameLabel1;
	TextView textviewConfRenameLabel2;
	TextView textviewConfRenameLabel3;
	
	TextView textviewConfRenameLabel11;
	TextView textviewConfRenameLabel12;
	TextView textviewConfRenameLabel13;
	TextView textviewConfRenameLabel14;
	
	EditText edittextConfRename1;
	EditText edittextConfRename2;
	EditText edittextConfRename3;
	
	EditText edittextConfRename11;
	EditText edittextConfRename12;
	EditText edittextConfRename13;
	EditText edittextConfRename14;
	
	
//	static final int CIRCUIT_COUNT = 3;
//	static final int PROGRAMME_COUNT = 4;
//	
//	ListView listviewConfigRename;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_config_rename, container, false);
		
		textviewConfRenameLabel1 = (TextView) view.findViewById(R.id.textviewConfRenameLabel1);
		textviewConfRenameLabel2 = (TextView) view.findViewById(R.id.textviewConfRenameLabel2);
		textviewConfRenameLabel3 = (TextView) view.findViewById(R.id.textviewConfRenameLabel3);
		textviewConfRenameLabel11 = (TextView) view.findViewById(R.id.textviewConfRenameLabel11);
		textviewConfRenameLabel12 = (TextView) view.findViewById(R.id.textviewConfRenameLabel12);
		textviewConfRenameLabel13 = (TextView) view.findViewById(R.id.textviewConfRenameLabel13);
		textviewConfRenameLabel14 = (TextView) view.findViewById(R.id.textviewConfRenameLabel14);
		
		edittextConfRename1 = (EditText) view.findViewById(R.id.edittextConfRename1);
		edittextConfRename2 = (EditText) view.findViewById(R.id.edittextConfRename2);
		edittextConfRename3 = (EditText) view.findViewById(R.id.edittextConfRename3);
		edittextConfRename11 = (EditText) view.findViewById(R.id.edittextConfRename11);
		edittextConfRename12 = (EditText) view.findViewById(R.id.edittextConfRename12);
		edittextConfRename13 = (EditText) view.findViewById(R.id.edittextConfRename13);
		edittextConfRename14 = (EditText) view.findViewById(R.id.edittextConfRename14);
		
		
		textviewConfRenameLabel1.setText(getActivity().getResources().getString(R.string.text_circute) + " " + 1);
		textviewConfRenameLabel2.setText(getActivity().getResources().getString(R.string.text_circute) + " " + 2);
		textviewConfRenameLabel3.setText(getActivity().getResources().getString(R.string.text_circute) + " " + 3);
		
		textviewConfRenameLabel11.setText(getActivity().getResources().getString(R.string.text_programme) + " " + 1);
		textviewConfRenameLabel12.setText(getActivity().getResources().getString(R.string.text_programme) + " " + 2);
		textviewConfRenameLabel13.setText(getActivity().getResources().getString(R.string.text_programme) + " " + 3);
		textviewConfRenameLabel14.setText(getActivity().getResources().getString(R.string.text_programme) + " " + 4);
		
		edittextConfRename1.setText(AikosGlobals.getCircuits().get(0).getName());
		edittextConfRename1.addTextChangedListener(new ListenerOnCircuitChange(getActivity(), 0));
		edittextConfRename2.setText(AikosGlobals.getCircuits().get(1).getName());
		edittextConfRename2.addTextChangedListener(new ListenerOnCircuitChange(getActivity(), 1));
		edittextConfRename3.setText(AikosGlobals.getCircuits().get(2).getName());
		edittextConfRename3.addTextChangedListener(new ListenerOnCircuitChange(getActivity(), 2));
		
		edittextConfRename11.setText(AikosGlobals.getProgram(0).getName());
		edittextConfRename11.addTextChangedListener(new ListenerOnProgramChange(getActivity(), 0));
		edittextConfRename12.setText(AikosGlobals.getProgram(1).getName());
		edittextConfRename12.addTextChangedListener(new ListenerOnProgramChange(getActivity(), 1));
		edittextConfRename13.setText(AikosGlobals.getProgram(2).getName());
		edittextConfRename13.addTextChangedListener(new ListenerOnProgramChange(getActivity(), 2));
		edittextConfRename14.setText(AikosGlobals.getProgram(3).getName());
		edittextConfRename14.addTextChangedListener(new ListenerOnProgramChange(getActivity(), 3));
		
		
		
//		listviewConfigRename = (ListView) view.findViewById(R.id.listviewConfigRename);
//		listviewConfigRename.setAdapter(new ConfigRenameListAdapter());
		
		return view;
	}
	
	public class ListenerOnCircuitChange implements TextWatcher {
		
		Context mContext;
		private int index = 0;
		
		public ListenerOnCircuitChange(Context context, int idx) {
			mContext = context;
			index = idx;
		}
		
		@Override
		public void afterTextChanged(Editable arg0) { }
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			if (AikosGlobals.getCircuits().size() > index) {
				AikosGlobals.getCircuits().get(index).setName(arg0.toString());
			}
		}

	}
	
	public class ListenerOnProgramChange implements TextWatcher {
		
		Context mContext;
		private int index = 0;
		
		public ListenerOnProgramChange(Context context, int idx) {
			mContext = context;
			index = idx;
		}
		
		@Override
		public void afterTextChanged(Editable arg0) { }
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			if (AikosGlobals.getProgramsArray().size() > index) {
				AikosGlobals.getProgram(index).setName(arg0.toString());
			}
		}

	}
	
	

}
