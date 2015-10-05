package org.bluebits.mocki.mock.client.factory;

import java.util.ArrayList;
import java.util.List;

import org.bluebits.mocki.client.model.MockiMenuItem;
import org.bluebits.mocki.client.model.Product;

public class MockiFactory {

	private static final int PRODUCT_LIST_SIZE = 50;

	public static List<Product> getProducts() {
		List<Product> products = new ArrayList<Product>();

		for (int i = 0; i < PRODUCT_LIST_SIZE; i++) {
			Product product = new Product();
			product.setProductName("Mocki Product " + (i + 1));
			products.add(product);
		}

		return products;
	}

	public static class MockiMenu {
		private static List<MockiMenuItem> items;

		public static List<MockiMenuItem> getMenuItems() {
			items = new ArrayList<MockiMenuItem>();
			items.add(new MockiMenuItem(1, "ic_mod_icon.png", "Order Collection"));
			items.add(new MockiMenuItem(2, "ic_mod_icon.png", "Doctors Call"));
			items.add(new MockiMenuItem(3, "ic_mod_icon.png", "Field Survey"));
			items.add(new MockiMenuItem(4, "ic_mod_icon.png", "Review and Management"));
			items.add(new MockiMenuItem(5, "ic_mod_icon.png", "Synchronization"));
			items.add(new MockiMenuItem(6, "ic_mod_icon.png", "Application Settings"));
			items.add(new MockiMenuItem(7, "ic_mod_icon.png", "About BlueBits"));

			return items;
		}

		public static MockiMenuItem getItemById(int id) {

			for (MockiMenuItem item : items) {
				if (item.Id == id) {
					return item;
				}
			}

			return null;
		}
	}
}
