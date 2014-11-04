package com.datetimepicker.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DateDialog extends DialogFragment implements OnClickListener
{
	private ImageView ivSet, ivCancel;
	public static String choosenTime = "";
	RelativeLayout rlCalendarTitle;
	GridView calendarView;
	GridCellAdapter adapter1;
	Button leftArrow, rightArrow;
	String next_month_day = "";
	TextView monthTitleTextViewYear;
	SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
	Calendar _calendar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.layout_inflator_choose_date, container);
		initializeWidgets(view);
		return view;
	}

	private void initializeWidgets(View view)
	{
		rlCalendarTitle = (RelativeLayout) view.findViewById(R.id.calendar_title_view2);
		calendarView = (GridView) view.findViewById(R.id.calendar);
		ivSet = (ImageView) view.findViewById(R.id.imageView1);
		ivCancel = (ImageView) view.findViewById(R.id.imageView2);

		monthTitleTextViewYear = (TextView) view.findViewById(R.id.calendar_month_year_textview2);
		_calendar = Calendar.getInstance(Locale.getDefault());
		initializeCalendar();
		leftArrow = (Button) view.findViewById(R.id.calendar_left_arrow2);
		rightArrow = (Button) view.findViewById(R.id.calendar_right_arrow2);
		leftArrow.setOnClickListener(this);
		rightArrow.setOnClickListener(this);
		ivSet.setOnClickListener(this);
		ivCancel.setOnClickListener(this);
	}

	private void initializeCalendar()
	{
		System.out.println(sdf.format(_calendar.getTime()));
		monthTitleTextViewYear.setText(sdf.format(_calendar.getTime()));
		String[] date_current = monthTitleTextViewYear.getText().toString().split(" ");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.YEAR, cal.getActualMinimum(Calendar.YEAR));

		String day__ = "";
		if ((convertMonthToInt(date_current[0])) < 9)
		{
			day__ = "0" + (convertMonthToInt(date_current[0]));
		}
		else
		{
			day__ = "" + (convertMonthToInt(date_current[0]));
		}
		System.out.println("day_ => " + day__);
		adapter1 = new GridCellAdapter(getActivity(), R.layout.layout_inflator_screen_gridcell, Integer.parseInt(day__),
				Integer.parseInt(date_current[1]));
		adapter1.notifyDataSetChanged();
		calendarView.setAdapter(adapter1);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.calendar_left_arrow2:
			monthLeftArrow(0);
			break;
		case R.id.calendar_right_arrow2:
			monthRightArrow(0);
			break;
		case R.id.imageView2:
			getDialog().dismiss();
			break;
		case R.id.imageView1:
			choosenTime = GridCellAdapter.clickDate;
			getDialog().dismiss();
			break;
		}
	}

	private void monthLeftArrow(int i)
	{
		// TODO Auto-generated method stub
		if (i == 0)
		{
			next_month_day = "";
		}

		String[] date = monthTitleTextViewYear.getText().toString().split(" ");
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Integer.parseInt(date[1]), (convertMonthToInt(date[0]) - 1), 1);
		cal.add(Calendar.MONTH, -1);

		monthTitleTextViewYear.setText(sdf.format(cal.getTime()));
		String[] date_current = monthTitleTextViewYear.getText().toString().split(" ");
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		String day__ = "";
		if ((convertMonthToInt(date_current[0])) < 9)
		{
			day__ = "0" + (convertMonthToInt(date_current[0]));
		}
		else
		{
			day__ = "" + (convertMonthToInt(date_current[0]));
		}
		adapter1 = new GridCellAdapter(getActivity(), R.layout.layout_inflator_screen_gridcell, Integer.parseInt(day__), 2014);
		adapter1.notifyDataSetChanged();
		calendarView.setAdapter(adapter1);
	}

	private void monthRightArrow(int i)
	{
		// TODO Auto-generated method stub
		if (i == 0)
		{
			next_month_day = "";
		}

		String[] date = monthTitleTextViewYear.getText().toString().split(" ");
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Integer.parseInt(date[1]), (convertMonthToInt(date[0]) - 1), 1);

		cal.add(Calendar.MONTH, 1);

		monthTitleTextViewYear.setText(sdf.format(cal.getTime()));

		String[] date_current = monthTitleTextViewYear.getText().toString().split(" ");
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		String day__ = "";
		if ((convertMonthToInt(date_current[0])) < 9)
		{
			day__ = "0" + (convertMonthToInt(date_current[0]));
		}
		else
		{
			day__ = "" + (convertMonthToInt(date_current[0]));
		}
		adapter1 = new GridCellAdapter(getActivity(), R.layout.layout_inflator_screen_gridcell, Integer.parseInt(day__), 2014);
		adapter1.notifyDataSetChanged();
		calendarView.setAdapter(adapter1);
	}

	private int convertMonthToInt(String str)
	{
		// TODO Auto-generated method stub
		if (str.equals("Jan"))
		{
			return 1;
		}
		else
			if (str.equals("Feb"))
			{
				return 2;
			}
			else
				if (str.equals("Mar"))
				{
					return 3;
				}
				else
					if (str.equals("Apr"))
					{
						return 4;
					}
					else
						if (str.equals("May"))
						{
							return 5;
						}
						else
							if (str.equals("Jun"))
							{
								return 6;
							}
							else
								if (str.equals("Jul"))
								{
									return 7;
								}
								else
									if (str.equals("Aug"))
									{
										return 8;
									}
									else
										if (str.equals("Sep"))
										{
											return 9;
										}
										else
											if (str.equals("Oct"))
											{
												return 10;
											}
											else
												if (str.equals("Nov"))
												{
													return 11;
												}
												else
													if (str.equals("Dec"))
													{
														return 12;
													}

		return 0;
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
	    System.out.println("dismissed :: "+choosenTime);
	    DateTimePickerActivity.etSelectDate.setText(choosenTime);
	}

}