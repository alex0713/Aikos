package com.devel.aikos.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.github.mikephil.charting.utils.YLabels.YLabelPosition;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainConsumeFragment extends Fragment {

    static final int PERIOD_instantaneous = 0;
    static final int PERIOD_hour = 1;
    static final int PERIOD_day = 2;
    static final int PERIOD_month = 3;

    static final int CONSUME_CASE_usage = 0;
    static final int CONSUME_CASE_counter = 1;

    static String[] months = {"J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"};
	
	private int period = 0;
	private int consumeCase = 0;
	
	private Boolean[] ischecked;
	
	Context context = null;
	
	BarChart mChart;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_consume, container, false);
		
		context = getActivity();
		
		mChart = (BarChart) view.findViewById(R.id.chartConsume);
		
		setConsumeCase(0);
		
		initChart();
        
        return view;
	}
	
	
	
	
	void initChart() {
		//initialize the Chart
		// enable the drawing of values
        mChart.setDrawYValues(true);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);
        
        ChartValueFormatter customFormatter = new ChartValueFormatter();
        
        // set a custom formatter for the values inside the chart
        mChart.setValueFormatter(customFormatter);
        
        // if false values are only drawn for the stack sum, else each value is drawn
        mChart.setDrawValuesForWholeStack(false);

        // disable 3D
        mChart.set3DEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDragEnabled(true);

        mChart.setDrawBarShadow(false);
        
        //------
//        mChart.setTouchEnabled(false);

        // change the position of the y-labels
        YLabels yLabels = mChart.getYLabels();
        yLabels.setPosition(YLabelPosition.RIGHT);
        yLabels.setLabelCount(5);
        yLabels.setFormatter(customFormatter);

        XLabels xLabels = mChart.getXLabels();
        xLabels.setPosition(XLabelPosition.BOTTOM);
        xLabels.setCenterXLabelText(true);
        
        refreshChart();

        mChart.setDrawLegend(false);
		Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_RIGHT);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
	}

	
	public void refreshChart() {

        int xCount = period == PERIOD_hour ? 24 :
                period == PERIOD_day ? 7 :
                period == PERIOD_month ? 12 : 15;
		
		if (context == null) {
			return;
		}
		
		int ccount = 0;
		for (int i = 0; i < ischecked.length; i++) {
			if (ischecked[i]) ccount++;
		}
		
		if (ccount == 0) {
			return;
		}
        String[] dayNames = getResources().getStringArray(R.array.days);
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < xCount; i++) {
            if (period == PERIOD_month) {
                xVals.add(months[i]);
            } else if (period == PERIOD_day) {
                xVals.add(dayNames[i]);
            } else {
                xVals.add("" + (i + 1));
            }
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < xCount; i++) {
        	
        	float[] yvals = new float[ccount];
        	float mult = (50 + 1);
        	for (int j = 0; j < yvals.length; j++) {
        		yvals[j] = (float) (Math.random() * mult) + mult / 3;
			}

            yVals1.add(new BarEntry(yvals, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "  KWh");
        
        int[] ccolors = new int[ccount];
        int idx = 0;
		for (int i = 0; i < ischecked.length; i++) {
			if (ischecked[i]) {
				ccolors[idx++] = AikosGlobals.graphColors[i];
				if (idx >= ccount) break;
			}
		}
        set1.setColors(ccolors);
        
        String[] titles = new String[ccount];
        idx = 0;
		for (int i = 0; i < ischecked.length; i++) {
			if (ischecked[i]) {
				titles[idx++] = AikosGlobals.getConsumeItems(consumeCase)[i];
				if (idx >= ccount) break;
			}
		}
		
        set1.setStackLabels(titles);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);

        mChart.setData(data);
        mChart.invalidate();
    }
	
	public int getPeriod() {
		return period;
	}
	
	public void setPeriod(int period) {
		this.period = period;
		refreshChart();
	}

	public int getConsumeCase() {
		return consumeCase;
	}
	
	public void setConsumeCase(int consumeCase) {
		this.consumeCase = Math.min(Math.max(consumeCase, 0), 1);
		
		ischecked = new Boolean[AikosGlobals.getConsumeItems(consumeCase).length];
		for (int i = 0; i < ischecked.length; i++) {
			ischecked[i] = true;
		}
		
		refreshChart();
	}
	
	public Boolean isChecked(int index) {
		if (index < 0 || index >= ischecked.length) {
			return false;
		}
		return ischecked[index];
	}
	
	public void setChecked(int index, Boolean val) {
		if (index >= 0 && index < ischecked.length) {
			if (ischecked[index] == val) {
				return;
			}
			ischecked[index] = val;
			refreshChart();
		}
	}

	class ChartValueFormatter implements ValueFormatter {

	    DecimalFormat mFormatter = new DecimalFormat("###,###,###");

	    @Override
	    public String getFormattedValue(float value) {
	        // do here whatever you want, avoid excessive calculations and memory
	        // allocations
	        return mFormatter.format(value);
	    }
	}
}
