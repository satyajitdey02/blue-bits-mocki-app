/**
 * 
 */
package org.bluebits.mocki.client.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.bluebits.mocki.R;
import org.bluebits.mocki.client.model.Product;

/**
 * @author satyajit
 * 
 */
public class ProductListAdapter extends ArrayAdapter<Product> {

	private final List<Product> list;
	private final Activity context;

	public ProductListAdapter(Activity context, List<Product> list) {
		super(context, R.layout.layout_product_list, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView lblProduct;
		protected EditText txtQty;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.layout_product_list, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.lblProduct = (TextView) view.findViewById(R.id.product);
			viewHolder.txtQty = (EditText) view.findViewById(R.id.qty);

			viewHolder.txtQty.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {
					Product element = (Product) viewHolder.txtQty
							.getTag();
					final EditText text = (EditText) v;
					String qty = text.getText().toString();

					if (!qty.isEmpty()) {
						element.setProductQty(Integer.parseInt(qty));
					}
				}
			});

			view.setTag(viewHolder);
			viewHolder.txtQty.setTag(list.get(position));
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).txtQty.setTag(list.get(position));
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.lblProduct.setText(list.get(position).getProductName());
		
		if (list.get(position).getProductQty() > 0) {
			holder.txtQty.setText(String.valueOf(list.get(position).getProductQty()));
		}

		return view;
	}
}