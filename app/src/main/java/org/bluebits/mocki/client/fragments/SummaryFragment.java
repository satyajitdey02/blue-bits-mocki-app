/**
 * 
 */
package org.bluebits.mocki.client.fragments;

import org.bluebits.mocki.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author satyajit
 * 
 */
public class SummaryFragment extends Fragment {

	private Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = getActivity();
		this.activity.setContentView(R.layout.layout_oc_summary);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	

}
