import java.io.*;
import java.util.*;


public class OrderPayment 
	{

	private String username;
	 private String productName;
	 private String creditCardNo;
	 private String price;
	 private String address;
	 private String deliveryDate;
	 private int orderid;
	 	 
	public OrderPayment(String username,String productName,String price,String creditCardNo,String address,String deliveryDate,int orderid)
	{
			this.username=username;
			this.productName=productName;
			this.price=price;
			this.creditCardNo=creditCardNo;
			this.address=address;
			this.deliveryDate = deliveryDate;
			this.orderid=orderid;			
	}
	
	
	public OrderPayment()
	{		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getproductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	
	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getdeliveryDate() {
		return deliveryDate;
	}
	
	public void setdeliveryDate(String deliveryDate){
		this.deliveryDate = deliveryDate;
	}
	
	public int getOrderId() {
		return orderid;
	}

	public void setOrderId(int orderid) {
		this.orderid = orderid;
	}
}