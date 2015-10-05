/**
 * 
 */
package org.bluebits.mocki.shared.oc;

/**
 * @author satyajit
 * 
 */
public class OrderProduct {

	String productId;
	int qty;

	public OrderProduct() {

	}

	public OrderProduct(String productId, int qty) {
		this.productId = productId;
		this.qty = qty;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
}
