/**
 * 
 */
package org.bluebits.mocki.client.fragments;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import org.bluebits.mocki.R;

/**
 * @author satyajit
 * 
 */
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	
	private Activity activity;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		this.activity = getActivity();
		
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(this.activity, this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		TextView btnDatePicker = (TextView) this.activity.findViewById(R.id.lblDatePicker);
		btnDatePicker.setText(day + "-" + month + "-" + year, BufferType.NORMAL);
	}
}
