/**
 * 
 */
package org.bluebits.mocki.client.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.bluebits.mocki.R;
import org.bluebits.mocki.client.adapters.MockiMenuAdapter;
import org.bluebits.mocki.client.utils.ActivityLoaderUtil;
import org.bluebits.mocki.mock.client.factory.MockiFactory;

/**
 * @author satyajit
 * 
 */
public class MockiMenuActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mocki_menu);

		listView = (ListView) findViewById(R.id.listView);

		MockiMenuAdapter adapter = new MockiMenuAdapter(this,
				R.layout.layout_mocki_menu_item,
				MockiFactory.MockiMenu.getMenuItems());

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ListItemClickListener(this));
	}

	private class ListItemClickListener implements OnItemClickListener {

		private Context ctx;

		public ListItemClickListener(Context ctx) {
			this.ctx = ctx;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				ActivityLoaderUtil.load(this.ctx, OrderCollectionActivity.class);
				break;

			default:
				System.out.println("No Action!");
				break;
			}
		}
	}
}
