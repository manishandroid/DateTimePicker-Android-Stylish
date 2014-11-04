package com.datetimepicker.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

public class GridCellAdapter extends BaseAdapter 
{
	private final Context _context;
	public static String clickDate;
	private final List<String> list;
	private static final int DAY_OFFSET = 1;
	private final String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private int daysInMonth;
	private int currentDayOfMonth;
	private int currentWeekDay;
	Calendar calendar;
	private Button gridcell;

	// Days in Current Month
	public GridCellAdapter(Context context, int textViewResourceId, int month, int year)
	{
		super();
		this._context = context;
		this.list = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
		setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

		// Print Month
		printMonth(month, year);

		System.out.println(Arrays.toString(list.toArray()));
	}

	private String getMonthAsString(int i)
	{
		return months[i];
	}

	private int getNumberOfDaysOfMonth(int i)
	{
		return daysOfMonth[i];
	}

	public String getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	/**
	 * Prints Month
	 * 
	 * @param mm
	 * @param yy
	 */
	@SuppressLint("NewApi")
	private void printMonth(int mm, int yy)
	{
		int trailingSpaces = 0;
		int daysInPrevMonth = 0;
		int prevMonth = 0;
		int prevYear = 0;
		int nextMonth = 0;
		int nextYear = 0;

		int currentMonth = mm - 1;
		getMonthAsString(currentMonth);
		daysInMonth = getNumberOfDaysOfMonth(currentMonth);

		GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
		if (currentMonth == 11)
		{
			prevMonth = currentMonth - 1;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 0;
			prevYear = yy;
			nextYear = yy + 1;
		}
		else
			if (currentMonth == 0)
			{
				prevMonth = 11;
				prevYear = yy - 1;
				nextYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 1;
			}
			else
			{
				prevMonth = currentMonth - 1;
				nextMonth = currentMonth + 1;
				nextYear = yy;
				prevYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			}

		int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
		trailingSpaces = currentWeekDay;
		System.out.println("currentWeekDay :: " + currentWeekDay);

		if (cal.isLeapYear(cal.get(Calendar.YEAR)))
		{
			if (mm == 2)
			{
				++daysInMonth;
			}
			else
				if (mm == 3)
				{
					++daysInPrevMonth;
				}
		}

		for (int i = 0; i < trailingSpaces; i++)
		{
			TimeZone MyTimezone = TimeZone.getDefault();
			Calendar calendar = new GregorianCalendar(MyTimezone);
			calendar.set(yy, currentMonth, i);
			String day_name = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());// Locale.US);

			list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth)
					+ "-" + prevYear + "-no" + "-" + day_name);
		}

		// Current Month Days
		for (int i = 1; i <= daysInMonth; i++)
		{
			TimeZone MyTimezone = TimeZone.getDefault();
			Calendar calendar = new GregorianCalendar(MyTimezone);
			calendar.set(yy, currentMonth, i);
			String day_name = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());// Locale.US);
			if (i == getCurrentDayOfMonth())
			{
				list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy + "-yes" + "-" + day_name);
			}
			else
			{
				list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy + "-no" + "-" + day_name);
			}
		}

		// Leading Month days
		for (int i = 0; i < list.size() % 7; i++)
		{
			TimeZone MyTimezone = TimeZone.getDefault();
			Calendar calendar = new GregorianCalendar(MyTimezone);
			calendar.set(yy, currentMonth, i);
			String day_name = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());// Locale.US);
			list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear + "-no" + "-" + day_name);
		}
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		try
		{
			if (row == null)
			{
				LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.layout_inflator_screen_gridcell, parent, false);
			}
			// String current_month_check = monthTitleTextViewYear.getText().toString();
			gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);

			final String[] day_color = list.get(position).split("-");
			String theday = day_color[0];
			gridcell.setText(theday.trim());
			gridcell.setTag("");

			Calendar calendar = Calendar.getInstance();
			String date = day_color[0];
			String month = String.valueOf(convertMonthToInt(day_color[2].trim()) - 1);
			String year = day_color[3];
			System.out.println(date + "-" + month + "-" + year);
			calendar.set(Integer.parseInt(day_color[3].trim()) + 1900, convertMonthToInt(day_color[2].trim()) - 1,
					Integer.parseInt(day_color[0].trim()));

			gridcell.setGravity(Gravity.CENTER);
			gridcell.setTextSize(14);

			gridcell.setBackgroundResource(R.color.blue);

			if (day_color[1].equals("GREY"))
			{
				gridcell.setTag("next_month-" + Integer.parseInt(day_color[0].trim()));
				gridcell.setVisibility(View.INVISIBLE);
			}
			if (day_color[5].equals("Sat") || day_color[5].equals("Sun"))
			{
				gridcell.setTextColor(R.color.green);
			}

			gridcell.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(final View v)
				{
					// TODO Auto-generated method stub
					new Handler().postDelayed(new Runnable()
					{

						@Override
						public void run()
						{
							clickDate = day_color[0] + "-" + day_color[2] + "-" + day_color[3];
							Toast.makeText(_context, clickDate, Toast.LENGTH_SHORT).show();
						}
					}, 100);
				}
			});
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return row;
	}

	public int getCurrentDayOfMonth()
	{
		return currentDayOfMonth;
	}

	private void setCurrentDayOfMonth(int currentDayOfMonth)
	{
		this.currentDayOfMonth = currentDayOfMonth;
	}

	public void setCurrentWeekDay(int currentWeekDay)
	{
		this.currentWeekDay = currentWeekDay;
	}

	public int getCurrentWeekDay()
	{
		return currentWeekDay;
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

}
