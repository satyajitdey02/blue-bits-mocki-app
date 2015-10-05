/**
 * 
 */
package org.bluebits.mocki.client.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.bluebits.mocki.R;
import org.bluebits.mocki.client.fragments.CustomerFragment;
import org.bluebits.mocki.client.fragments.ProductListFragment;
import org.bluebits.mocki.client.fragments.SummaryFragment;
import org.bluebits.mocki.shared.oc.Order;

/**
 * @author satyajit
 * 
 */

public class OrderCollectionUtil {
	
	private static Order order;
	private static View mCustomerView;
	private static CustomerFragment mCustomerFragment;
	private static ProductListFragment mProductListFragment;
	//private static SummaryFragment mSummaryFragment;
	
	
	public static void resetOrder() {
		order = null;
		mCustomerFragment = null;
		mProductListFragment = null;
		//mSummaryFragment = null;
	}
	
	public static void setCustomerView(View view) {
		mCustomerView = view;
		System.out.println("debug");
	}
	
	public static void saveCustomerFragment(CustomerFragment customerFragment) {
		mCustomerFragment = customerFragment;
	}
	
	public static void saveProductListFragment(ProductListFragment productListFragment) {
		mProductListFragment = productListFragment;
	}
	
	public static void saveSummaryFragment(SummaryFragment summaryFragment) {
		//mSummaryFragment = summaryFragment;
	}
	
	public static Order extractOrder() {
		if(mCustomerView == null || mProductListFragment == null) {
			return null;
		}
		
		order = new Order();
		
		final EditText txtCustomerId = (EditText) mCustomerView.findViewById(R.id.txtCustomerId);
		final TextView lblStore = (TextView) mCustomerView.findViewById(R.id.lblStore);
		final Spinner  spnSession = (Spinner) mCustomerView.findViewById(R.id.spnrSession);
		final TextView lblDeliveryDate = (TextView) mCustomerView.findViewById(R.id.lblDatePicker);
		final EditText txtRemarks = (EditText) mCustomerView.findViewById(R.id.txtRemarks);
		
		order.setCustomerId(txtCustomerId.getText().toString());
		order.setStoreId(lblStore.getTag().toString());
		order.setSessionId(String.valueOf(spnSession.getSelectedItemPosition()));
		order.setDeliveryDate(lblDeliveryDate.getTag().toString());
		order.setRemarks(txtRemarks.getText().toString());
		
		order.setOrderProducts(mProductListFragment.getOrderProducts());
		
		return order;
	}
	
	public static String extractOrderAsJson() {
		return extractOrder().getJsonOrder();
	}
	
	public static Order getOrder() {
		return order;
	}
}
