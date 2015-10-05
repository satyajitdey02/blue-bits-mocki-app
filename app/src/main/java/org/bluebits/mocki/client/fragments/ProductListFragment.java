/**
 * 
 */
package org.bluebits.mocki.client.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.bluebits.mocki.R;
import org.bluebits.mocki.R.color;
import org.bluebits.mocki.client.adapters.ProductListAdapter;
import org.bluebits.mocki.client.model.MockiProduct;
import org.bluebits.mocki.client.model.Product;
import org.bluebits.mocki.client.model.ProductJsonResponse;
import org.bluebits.mocki.client.net.MockiHttpClient;
import org.bluebits.mocki.client.net.NetworkConnectivityManager;
import org.bluebits.mocki.client.utils.OrderCollectionUtil;
import org.bluebits.mocki.shared.oc.OrderProduct;

public class ProductListFragment extends ListFragment {

	private static final String BASE_URL = "http://desertshipbd.com/mocki_server/";
	
	private List<Product> products;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getActivity().setContentView(R.layout.product_list_layout);

		if (NetworkConnectivityManager.isConnected(this.getActivity())) {
			RequestParams params = new RequestParams();
			params.put("route", "feed/web_api/products");
			params.put("category", "");
			params.put("key", "desertship");

			MockiHttpClient.get(BASE_URL, params, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(String response) {
					
					Gson gson = new Gson();
					ProductJsonResponse jsonResponse = gson.fromJson(response, ProductJsonResponse.class);

					if (jsonResponse.isSuccess() && jsonResponse.getProducts().size() > 0) {
						products = new ArrayList<Product>();

						for (MockiProduct mp : jsonResponse.getProducts()) {
							Product product = new Product();
							product.setProductId(mp.getId());
							product.setProductName(mp.getName());
							products.add(product);
						}
					}

					if (products != null && products.size() > 0) {
						populateProductList();
					}
				}
				
				@Override
				public void onFailure(Throwable error, String content) {
				    if ( error.getCause() instanceof ConnectTimeoutException ) {
				    	Toast.makeText( ProductListFragment.this.getActivity(), "Connection timeout, Please try again.", 
				    			Toast.LENGTH_SHORT).show();
				    	return;
				    }
				    
				    Toast.makeText(ProductListFragment.this.getActivity(), "Error Loading Product list. Please try again.", 
				    		Toast.LENGTH_LONG).show();
				}

				/*@Override
				public void onFailure(Throwable t) {
					t.printStackTrace();
				}*/
			});
			
		} else {
			Toast.makeText(this.getActivity(), "Network not available!", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		OrderCollectionUtil.saveProductListFragment(this);
	}
	
	private void populateProductList() {
		ArrayAdapter<Product> adapter = new ProductListAdapter(this.getActivity(), products);
		
		setListAdapter(adapter);
		getListView().setBackgroundColor(color.custom_tab_background_color);
		getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
	}
	
	public List<OrderProduct> getOrderProducts() {
		List<OrderProduct> orderProducts = null;

		if (products != null && products.size() > 0) {
			orderProducts = new ArrayList<OrderProduct>();
			for (Product product : products) {
				if (product.getProductQty() > 0) {
					OrderProduct orderProduct = new OrderProduct(
							product.getProductId(), product.getProductQty());
					orderProducts.add(orderProduct);
				}
			}
		}

		return orderProducts;
	}
}
