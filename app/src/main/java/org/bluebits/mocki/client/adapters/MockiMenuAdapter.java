/**
 * 
 */
package org.bluebits.mocki.client.adapters;

/**
 * @author satyajit
 *
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.bluebits.mocki.R;
import org.bluebits.mocki.client.model.MockiMenuItem;
import org.bluebits.mocki.mock.client.factory.MockiFactory;

public class MockiMenuAdapter extends ArrayAdapter<MockiMenuItem> {

    private final Context context;
    private final List<MockiMenuItem> menuItems;
    private final int rowResourceId;

    public MockiMenuAdapter(Context context, int textViewResourceId, List<MockiMenuItem> menuItems) {

        super(context, textViewResourceId, menuItems);

        this.context = context;
        this.menuItems = menuItems;
        this.rowResourceId = textViewResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(rowResourceId, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.mockiMenuIcon);
        TextView textView = (TextView) rowView.findViewById(R.id.mockiMenuName);

        int id = menuItems.get(position).getId();
        
        String imageFile = MockiFactory.MockiMenu.getItemById(id).IconFile;

        textView.setText(MockiFactory.MockiMenu.getItemById(id).Name);
        // get input stream
        InputStream ims = null;
        try {
            ims = context.getAssets().open(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        // set image to ImageView
        imageView.setImageDrawable(d);
        return rowView;

    }
}
