package com.datetimepicker.main;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.adapters.ArrayWheelAdapter;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;

@SuppressLint("NewApi")
public class TimeDialog extends DialogFragment implements OnClickListener
{
	private ImageView ivSet, ivCancel;
	public static String choosenTime = "";
	private AbstractWheel hours, mins, ampm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.layout_inflator_choose_time, container);
		initializeWidgets(view);
		return view;
	}

	private void initializeWidgets(View view)
	{
		ivSet = (ImageView) view.findViewById(R.id.imageView1);
		ivCancel = (ImageView) view.findViewById(R.id.imageView2);
		hours = (AbstractWheel) view.findViewById(R.id.hour);
		hours.setViewAdapter(new NumericWheelAdapter(getActivity(), 0, 12));
		hours.setCyclic(false);
		mins = (AbstractWheel) view.findViewById(R.id.mins);
		mins.setViewAdapter(new NumericWheelAdapter(getActivity(), 0, 59, "%02d"));
		mins.setCyclic(false);
		ampm = (AbstractWheel) view.findViewById(R.id.amorpm);
		String[] data = new String[] { "AM", "PM" };
		ampm.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), data));
		ampm.setCyclic(false);
		ivSet.setOnClickListener(this);
		ivCancel.setOnClickListener(this);
		initializeTimeWheel();
		
	}

	private void initializeTimeWheel()
	{
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR);
		int curMinutes = c.get(Calendar.MINUTE);
		hours.setCurrentItem(curHours);
		mins.setCurrentItem(curMinutes);
		ampm.setCurrentItem(c.get(Calendar.AM_PM));
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.imageView2:
			getDialog().dismiss();
			break;
		case R.id.imageView1:
			String am_or_pm = "";
			int hour = hours.getCurrentItem(); int min = mins.getCurrentItem();
			int AM_OR_PM = ampm.getCurrentItem();
			switch(AM_OR_PM)
			{
			case 0: am_or_pm = "AM"; break;
			case 1: am_or_pm = "PM"; break;
			}
			String hours, mins;
			if(hour < 10)
				hours = "0"+hour;
			else
				hours = String.valueOf(hour);
			if(min < 10)
				mins = "0"+min;
			else
				mins = String.valueOf(min);
			choosenTime = hours+":"+mins+" "+am_or_pm;
			getDialog().dismiss();
			break;
		}
	}

	
	
	@Override
	public void onDismiss(DialogInterface dialog) {
	    System.out.println("dismissed :: "+choosenTime);
	    	DateTimePickerActivity.etSelectTime.setText(choosenTime);
	    }

}