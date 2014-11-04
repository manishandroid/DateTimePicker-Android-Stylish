package com.datetimepicker.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class DateTimePickerActivity extends Activity implements OnClickListener
{
	static EditText etSelectDate, etSelectTime;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle arg0)
	{
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_datetime_picker);
		getActionBar().setTitle("Date Time Picker");
		initializeWidgets();
	}
	
	
	
	private void initializeWidgets()
	{
		etSelectDate = (EditText)findViewById(R.id.et_selectDate);
		etSelectTime = (EditText)findViewById(R.id.et_selectTime);
		
		etSelectTime.setOnClickListener(this);
		etSelectTime.setKeyListener(null);
		etSelectTime.setCursorVisible(false);
		etSelectTime.setPressed(false);
		etSelectTime.setFocusable(false);
		
		etSelectDate.setOnClickListener(this);
		etSelectDate.setKeyListener(null);
		etSelectDate.setCursorVisible(false);
		etSelectDate.setPressed(false);
		etSelectDate.setFocusable(false);
		
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.et_selectDate:
			FragmentManager fm2 = getFragmentManager();
	        DateDialog dateDialog = new DateDialog();
	        dateDialog.show(fm2, "fragment_date");
			break;
		case R.id.et_selectTime:
			FragmentManager fm = getFragmentManager();
	        TimeDialog timeDialog = new TimeDialog();
	        timeDialog.show(fm, "fragment_time");
			break;
		
		}
	}
	

}
