/**
 * 
 */
package org.bluebits.mocki.client.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.bluebits.mocki.R;
import org.bluebits.mocki.client.activities.OrderCollectionActivity;
import org.bluebits.mocki.client.adapters.StoreListAdapter;
import org.bluebits.mocki.client.model.MockiStore;
import org.bluebits.mocki.client.model.Store;
import org.bluebits.mocki.client.model.StoreJsonResponse;
import org.bluebits.mocki.client.net.MockiHttpClient;
import org.bluebits.mocki.client.net.NetworkConnectivityManager;
import org.bluebits.mocki.client.utils.OrderCollectionUtil;

/**
 * @author satyajit
 * 
 */
public class CustomerFragment extends Fragment {
	
	private static Bundle bundle = new Bundle();
	private static final String BASE_URL = "http://desertshipbd.com/mocki_server/";
	private AlertDialog dialog;
	private Activity activity;
	private List<Store> stores = null;
	Spinner spinner;
	String[] day;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = getActivity();
		this.activity.setContentView(R.layout.layout_oc_customer);

		addUiEventkListener();
	}

	private void addUiEventkListener() {
		TextView lblDatePicker = (TextView) activity
				.findViewById(R.id.lblDatePicker);
		lblDatePicker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePicker();
			}
		});

		TextView lblStore = (TextView) activity.findViewById(R.id.lblStore);
		lblStore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadStoreList();
			}
		});
	}

	private void showDatePicker() {
		final View fragmentView = this.getView();
		DatePicker date = new DatePicker();
		
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		date.setArguments(args);
		
		date.setCallBack(new OnDateSetListener() {

			@Override
			public void onDateSet(android.widget.DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				TextView target = (TextView)fragmentView.findViewById(R.id.lblDatePicker);
				String date = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear)
					       + "-" + String.valueOf(year);
				
				target.setText(date);
				target.setTag(date);
			}

		});

		date.show(getFragmentManager(), "Delivery Date");
	}
	
	@Override
	public void onPause() {
		super.onPause();

		//final EditText txtCustomerId = (EditText) this.getView().findViewById(R.id.txtCustomerId);
		final TextView lblStore = (TextView) this.getView().findViewById(R.id.lblStore);
		//final Spinner  spnSession = (Spinner) this.getView().findViewById(R.id.spnrSession);
		final TextView lblDeliveryDate = (TextView) this.getView().findViewById(R.id.lblDatePicker);
		//final EditText txtRemarks = (EditText) this.getView().findViewById(R.id.txtRemarks);
		
		Object storeTag = lblStore.getTag();
		Object dateTag = lblDeliveryDate.getTag();
		
		bundle.putString("storeId", storeTag == null ? "" : storeTag.toString());
		bundle.putString("storeName", lblStore.getText().toString());
		bundle.putString("date", dateTag == null ? "" : dateTag.toString());
	}

	@Override
	public void onResume() {
		super.onResume();
		//OrderCollectionUtil.saveCustomerFragment(this);

		OrderCollectionUtil.setCustomerView(this.getView());
		
		TextView lblStore = (TextView) this.getView().findViewById(R.id.lblStore);
		String storeName = bundle.getString("storeName");
		
		if(storeName == null || storeName.isEmpty()) {
			lblStore.setText(getResources().getString(R.string.oc_select_store));
		} else {
			lblStore.setText(storeName);
		}
		
		
		String date = bundle.getString("date");
		TextView lblDatePicker = (TextView) this.getView().findViewById(R.id.lblDatePicker);
		
		if(date == null || date.isEmpty()) {
			lblDatePicker.setText(getResources().getString(R.string.oc_delivery_date));
		} else {
			lblDatePicker.setText(date);
		}
	}

	private void loadStoreList() {
		if (NetworkConnectivityManager.isConnected(activity)) {
			RequestParams params = new RequestParams();
			params.put("route", "feed/web_api/stores");
			params.put("email", "satyajit02@gmail.com");

			MockiHttpClient.get(BASE_URL, params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(String response) {
							try {
								Gson gson = new Gson();
								StoreJsonResponse jsonResponse = gson.fromJson(
										response, StoreJsonResponse.class);

								if (jsonResponse.isSuccess()
										&& jsonResponse.getStores().size() > 0) {
									stores = new ArrayList<Store>();

									for (MockiStore ms : jsonResponse
											.getStores()) {
										Store store = new Store();
										store.setId(ms.getAddress_id());
										store.setName(ms.getCompany());
										stores.add(store);
									}
								}

								if (stores != null && stores.size() > 0) {
									showStoreList();
								}

							} catch (Exception e) {
								Toast.makeText(
										activity,
										"Error Loading store list. Please try again.",
										Toast.LENGTH_SHORT).show();
							}

						}
						
						@Override
						public void onFailure(Throwable error, String content) {
						    if ( error.getCause() instanceof ConnectTimeoutException ) {
						    	Toast.makeText(activity, "Connection timeout, Please try again.", 
						    			Toast.LENGTH_SHORT).show();
						    	return;
						    }
						    
						    Toast.makeText(activity, "Error Loading store list. Please try again.", 
						    		Toast.LENGTH_LONG).show();
						}

						/*@Override
						public void onFailure(Throwable t) {
							t.printStackTrace();
							Toast.makeText(
									activity,
									"Error Loading store list. Please try again.",
									Toast.LENGTH_LONG).show();
						}*/
					});

		} else {
			Toast.makeText(activity, "Network not available!",
					Toast.LENGTH_LONG).show();
		}
	}

	private void showStoreList() {
		StoreListAdapter adapter = new StoreListAdapter(activity,
				R.layout.layout_store_list, stores);

		ListView listViewItems = new ListView(activity);
		listViewItems.setAdapter(adapter);

		AlertDialog.Builder builder = new AlertDialog.Builder(activity)
				.setView(listViewItems).setTitle("Store List")
				.setIcon(R.drawable.ic_store);

		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						TextView lblStore = (TextView) getView().findViewById(R.id.lblStore);
						lblStore.setText(getResources().getString(R.string.oc_select_store));
						
						dialog.dismiss();
					}
				});

		dialog = builder.show();

		listViewItems.setOnItemClickListener(new ListItemClickListener(this
				.getView(), dialog));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_oc_customer, container, false);
	}

	private class ListItemClickListener implements OnItemClickListener {

		private View fragmentView;
		private AlertDialog dialog;

		public ListItemClickListener(View fragmentView, AlertDialog dialog) {
			this.fragmentView = fragmentView;
			this.dialog = dialog;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			TextView textViewItem = ((TextView) view.findViewById(R.id.store));
			String listItemText = textViewItem.getText().toString();
			Object listItemId = textViewItem.getTag();

			TextView target = (TextView) fragmentView.findViewById(R.id.lblStore);
			target.setText(listItemText);
			target.setTag(listItemId == null || listItemId.toString().isEmpty() ? "" : listItemId.toString());

			this.dialog.dismiss();
		}
	}
}
