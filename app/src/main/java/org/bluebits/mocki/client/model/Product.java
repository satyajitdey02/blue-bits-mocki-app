/**
 * 
 */
package org.bluebits.mocki.client.model;

/**
 * @author satyajit
 * 
 */
public class Product {

	private String productId;
	private String productName;
	private int productQty;

	public Product() { }

	public Product(String productId, String productName, int productQty) {
		this.productId = productId;
		this.productName = productName;
		this.productQty = productQty;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public int getProductQty() {
		return productQty;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductQty(int productQty) {
		this.productQty = productQty;
	}
}
