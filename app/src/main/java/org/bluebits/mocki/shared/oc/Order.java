/**
 * 
 */
package org.bluebits.mocki.shared.oc;

import java.util.List;

import com.google.gson.Gson;

/**
 * @author satyajit
 * 
 */
public class Order {
	String customerId;
	String storeId;
	String sessionId;
	String deliveryDate;
	String remarks;
	List<OrderProduct> orderProducts;

	public Order() {

	}

	public Order(String customerId, String storeId, String sessionId,
			String deliveryDate, String remarks,
			List<OrderProduct> orderProducts) {
		this.customerId = customerId;
		this.storeId = storeId;
		this.sessionId = sessionId;
		this.deliveryDate = deliveryDate;
		this.remarks = remarks;
		this.orderProducts = orderProducts;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public String getJsonOrder() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
