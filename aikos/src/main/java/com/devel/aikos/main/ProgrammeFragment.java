package com.devel.aikos.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aikos.aikoslibrary.data.AikosProgramme;
import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class ProgrammeFragment extends Fragment {
	
	private int pIndex = -1;
	
	Context context = null;
	
	LineChart mChart;
	
	TextView textviewProgramModify;
	TextView textviewProgramActivate;
    TextView textviewIsActive = null;
	
	AikosProgramme program = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_programme, container, false);
		
		context = getActivity();
		
		textviewProgramModify = (TextView) view.findViewById(R.id.textviewProgramModify);
		textviewProgramActivate = (TextView) view.findViewById(R.id.textviewProgramActivate);
        textviewIsActive = (TextView) view.findViewById(R.id.textviewIsActive);
		
		mChart = (LineChart) view.findViewById(R.id.chartProgram);
		
		program = AikosGlobals.getProgram(pIndex);
		if (program == null) {
			
		}
		
		initChart();
        
        return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		refreshView();
	}
	
	public void refreshView() {
        if (program == null) {
            return;
        }

		if (program.isAuto() || program.isContinue()) {
			textviewProgramModify.setVisibility(View.INVISIBLE);
		} else {
			textviewProgramModify.setVisibility(View.VISIBLE);
			textviewProgramModify.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View arg0) {
					onModfyProgram();
				}
			});
		}
		
		if (AikosGlobals.currentActiveProgramme == pIndex) {
			textviewProgramActivate.setTextColor(getResources().getColor(R.color.color_inactive_gray));
            textviewIsActive.setVisibility(View.VISIBLE);
		} else {
			textviewProgramActivate.setTextColor(getResources().getColor(R.color.color_purple));
            textviewIsActive.setVisibility(View.GONE);
		}
		
		textviewProgramActivate.setOnClickListener(new View.OnClickListener() {				
			@Override
			public void onClick(View arg0) {
				onActiveProgram();
			}
		});
	}



	void onModfyProgram() {
		Intent i = new Intent(getActivity(), ProgramEditActivity.class);
		i.putExtra(ProgramEditActivity.EXTRA_KEY_PROGRAM_INDEX, pIndex);
		getActivity().startActivity(i);
	}
	
	void onActiveProgram() {
		AikosGlobals.currentActiveProgramme = pIndex;
		refreshView();
	}
	
	
	void initChart() {

        // if enabled, the chart will always start at zero on the y-axis
        mChart.setStartAtZero(true);

        // disable the drawing of values into the chart
        mChart.setDrawYValues(false);
        mChart.setDrawYLabels(false);
        mChart.setDrawXLabels(false);

        mChart.setDrawBorder(false);
        
        mChart.setDrawLegend(false);

        // no description text
        mChart.setDescription("");
        mChart.setUnit(" $");

        // enable value highlighting
        mChart.setHighlightEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(false);

        // enable scaling and dragging
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setDrawVerticalGrid(false);
        
//        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//        mChart.setValueTypeface(tf);
        
//        XLabels x = mChart.getXLabels();
//        x.setTypeface(tf);
        
//        YLabels y = mChart.getYLabels();
//        y.setTypeface(tf);
//        y.setLabelCount(5);

        // add data
        setData(5, 100);
        
//        mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        mChart.invalidate();
	}
	
	private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> vals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
                                                           // ((mult *
                                                           // 0.1) / 10);
            vals1.add(new Entry(val, i));
        }
        
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(vals1, "DataSet 1");
        set1.setDrawCubic(false);
//        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(true);
        set1.setDrawCircles(true); 
        set1.setLineWidth(2f);
        set1.setCircleSize(5f);
        set1.setHighLightColor(context.getResources().getColor(R.color._Purple_Heart));
        set1.setColor(context.getResources().getColor(R.color._Purple_Heart));
        set1.setFillColor(context.getResources().getColor(R.color._Cold_Purple));
        set1.setCircleColor(context.getResources().getColor(R.color._Purple_Heart));

        set1.setShader(true);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1);

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
    }

	public int getpIndex() {
		return pIndex;
	}

	public void setpIndex(int pIndex) {
		this.pIndex = pIndex;
	}
	
}
