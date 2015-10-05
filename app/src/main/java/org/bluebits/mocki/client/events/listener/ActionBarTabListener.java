/**
 * 
 */
package org.bluebits.mocki.client.events.listener;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.Toast;

/**
 * @author satyajit
 * 
 */
public class ActionBarTabListener<T extends Fragment> implements
		ActionBar.TabListener {

	private Fragment fragment;
	private Activity activity;
	private String tag;
	private Class<T> clazz;

	public ActionBarTabListener(Activity activity, String tag, Class<T> clazz) {
		this.activity = activity;
		this.tag = tag;
		this.clazz = clazz;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Toast.makeText(activity, "Reselected!", Toast.LENGTH_SHORT).show();
	}
	

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// Check if the fragment is already initialized
		if (this.fragment == null) {
			// If not, instantiate and add it to the activity
			this.fragment = Fragment.instantiate(this.activity,
					this.clazz.getName());
			ft.replace(android.R.id.content, this.fragment, this.tag);
		} else {
			// If it exists, simply attach it in order to show it
			ft.attach(this.fragment);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (this.fragment != null) {
			// Detach the fragment, because another one is being attached
			ft.detach(this.fragment);
		}
	}

}
