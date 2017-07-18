package com.devel.aikos.config;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;

public class ConfigProfileFragment extends Fragment {

    EditText edittextJoinFirstName = null;
    EditText edittextJoinName = null;
    EditText edittextJoinLogin = null;
    EditText edittextJoinPasswd1 = null;
    EditText edittextJoinPasswd2 = null;
    EditText edittextJoinLongId = null;
    EditText edittextJoinShortId = null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_config_profile, container, false);

        edittextJoinFirstName = (EditText) view.findViewById(R.id.edittextJoinFirstName);
        edittextJoinName = (EditText) view.findViewById(R.id.edittextJoinName);
        edittextJoinLogin = (EditText) view.findViewById(R.id.edittextJoinLogin);
        edittextJoinPasswd1 = (EditText) view.findViewById(R.id.edittextJoinPasswd1);
        edittextJoinPasswd2 = (EditText) view.findViewById(R.id.edittextJoinPasswd2);
        edittextJoinLongId = (EditText) view.findViewById(R.id.edittextJoinLongId);
        edittextJoinShortId = (EditText) view.findViewById(R.id.edittextJoinShortId);

        edittextJoinFirstName.setText(AikosGlobals.userProfile.firstName);
        edittextJoinName.setText(AikosGlobals.userProfile.lastName);
        edittextJoinLogin.setText(AikosGlobals.userProfile.email);
        edittextJoinPasswd1.setText(AikosGlobals.userProfile.password);
        edittextJoinPasswd2.setText(AikosGlobals.userProfile.password);
        edittextJoinLongId.setText(AikosGlobals.userProfile.longId);
        edittextJoinShortId.setText(AikosGlobals.userProfile.shortId);
		
		return view;
	}
}
