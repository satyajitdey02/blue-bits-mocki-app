package org.bluebits.mocki.client.activities;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.bluebits.mocki.R;
import org.bluebits.mocki.client.events.listener.ActionBarTabListener;
import org.bluebits.mocki.client.fragments.CustomerFragment;
import org.bluebits.mocki.client.fragments.DatePickerFragment;
import org.bluebits.mocki.client.fragments.ProductListFragment;
import org.bluebits.mocki.client.fragments.SummaryFragment;
import org.bluebits.mocki.client.model.OrderJsonResponse;
import org.bluebits.mocki.client.net.MockiHttpClient;
import org.bluebits.mocki.client.net.NetworkConnectivityManager;
import org.bluebits.mocki.client.utils.ActivityLoaderUtil;
import org.bluebits.mocki.client.utils.OrderCollectionUtil;

public class OrderCollectionActivity extends Activity {

	public static String ACTIVE_TAB = "actionTab";
	
	private static final String BASE_URL = "http://desertshipbd.com/mocki_server/?route=feed/web_api/order";
	private static final String CONTENT_TYPE = "application/json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_oc);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		actionBar.addTab(actionBar
				.newTab()
				.setText("Customer")
				.setTabListener(
						new ActionBarTabListener<CustomerFragment>(this,
								"customer", CustomerFragment.class)));
		actionBar.addTab(actionBar
				.newTab()
				.setText("Products")
				.setTabListener(
						new ActionBarTabListener<ProductListFragment>(this,
								"products", ProductListFragment.class)));
		actionBar.addTab(actionBar
				.newTab()
				.setText("Summary")
				.setTabListener(
						new ActionBarTabListener<SummaryFragment>(this,
								"summary", SummaryFragment.class)));

		getOverflowMenu();

		if (savedInstanceState != null) {
			//actionBar.setSelectedNavigationItem(savedInstanceState.getInt(ACTIVE_TAB, 0));
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt(ACTIVE_TAB));
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ACTIVE_TAB, getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_oc, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.action_save_order:
			return true;
		case R.id.action_send_order:
			sendOrder();
			return true;
		case R.id.action_settings:

			return true;
		case R.id.action_sync:

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void sendOrder() {
		if(NetworkConnectivityManager.isConnected(this)) {
			HttpEntity entity = null;
			
			try {
				String order = OrderCollectionUtil.extractOrderAsJson();
				entity = new StringEntity(order);
			} catch (Exception e) {
				Toast.makeText(this, "Error sending Order. Please try again.", Toast.LENGTH_SHORT).show();
			}
			
			if(entity == null) {
				Toast.makeText(this, "Bad formatted Order. Please try again.", Toast.LENGTH_SHORT).show();
				return;
			}
			
			MockiHttpClient.post(this, BASE_URL, entity, CONTENT_TYPE, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(String response) {
					showOrderResponse(response);
				}
				
				@Override
				public void onFailure(Throwable error, String content) {
				    if (error.getCause() instanceof ConnectTimeoutException) {
				    	Toast.makeText(OrderCollectionActivity.this, "Connection timeout, Please try again.", Toast.LENGTH_SHORT).show();
				    } else if(error.getCause() instanceof SocketTimeoutException) {
				    	Toast.makeText(OrderCollectionActivity.this, "Socket timeout, Please try again.", Toast.LENGTH_SHORT).show();
				    } else if(error.getCause() instanceof ConnectionPoolTimeoutException) {
				    	Toast.makeText(OrderCollectionActivity.this, "Collection Pool timeout, Please try again.", Toast.LENGTH_SHORT).show();
				    }
				}

				/*@Override
				public void onFailure(Throwable t) {
					Toast.makeText(OrderCollectionActivity.this, "Couldn't save Order, Please try again.", Toast.LENGTH_SHORT).show();
				}*/
				
				@Override
				public void onFinish() {
					//OrderCollectionUtil.resetOrder();
				}
			});
		} else {
			Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void showOrderResponse(String response) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		if (response.isEmpty()) {
			builder.setTitle("Error")
				   .setIcon(R.drawable.ic_error)
				   .setMessage("Empty response found. Please contact with sysadmin.");
		} else {
			OrderJsonResponse jsonResponse = new Gson().fromJson(response,OrderJsonResponse.class);

			if (jsonResponse.isSuccess()
					&& !jsonResponse.getOrderId().isEmpty()) {
				builder.setTitle("Success")
					   .setIcon(R.drawable.ic_success)
					   .setMessage("Order sent successfully. Order ID:" + jsonResponse.getOrderId());
			} else {
				builder.setTitle("Error")
					   .setIcon(R.drawable.ic_error)
					   .setMessage("Error sending Order. Please try again.");
			}
		}

		builder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				OrderCollectionActivity.this.finish();
				ActivityLoaderUtil.load(OrderCollectionActivity.this, MockiMenuActivity.class);
			}
		});

		builder.show();
	}

	private void getOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}
}
