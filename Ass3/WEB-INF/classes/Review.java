import java.*;
import java.io.*;
import java.util.*;

public class Review
{
     private String username;
	 private String productModelName;
	 private String category;
	 private int price;
	 private String brand;
	 private String retailerName;
	 private String retailerCity;
	 private String retailerState;
	 private String retailerZip;
	 private String manufacturerrebate;
	 private String age;
	 private String gender;
	 private String occupation;
	 private int rating;
	 private String reviewtext;
	 private String pid;

	 public Review(String pid,String productModelName,String username,String category,int price,String retailerName,String retailerCity,String retailerState,String retailerZip,String manufacturername,String manufacturerrebate,String age,String gender,String occupation,int rating,String reviewtext)
	 {
		 this.pid = pid;
		 this.productModelName = productModelName;
		 this.username = username; 
		 this.price = price;
		 this.category = category;
		 this.retailerName = retailerName;
		 this.retailerCity = retailerCity;
		 this.retailerState = retailerState;
		 this.retailerZip = retailerZip;
		 this.brand = brand;
		 this.manufacturerrebate = manufacturerrebate;
		 this.age = age;
		 this.gender = gender;
		 this.occupation = occupation;
		 this.rating = rating;
		 this.reviewtext = reviewtext;
		 
		 
		 
	 }
	
	public String getPid()
	{
		return pid;
	}
	
	public void setPid(String pid)
	{
		this.pid = pid;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProductModelName() {
		return productModelName;
	}

	public void setProductModelName(String productModelName) {
		this.productModelName = productModelName;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}
	
	public String getRetailerCity() {
		return retailerCity;
	}

	public void setRetailerCity(String retailerCity) {
		this.retailerCity = retailerCity;
	}
	public String getRetailerState() {
		return retailerState;
	}

	public void setRetailerState(String retailerState) {
		this.retailerState = retailerState;
	}
	
	public String getRetailerZip() {
		return retailerZip;
	}

	public void setRetailerZip(String retailerZip) {
		this.retailerZip = retailerZip;
	}
	
	public String getManufacturerName() {
		return brand;
	}

	public void ManufacturerName(String brand) {
		this.brand = brand;
	}
	
	public String getManufacturerRebate() {
		return manufacturerrebate;
	}

	public void setManufacturerRebate(String manufacturerrebate) {
		this.manufacturerrebate = manufacturerrebate;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getReviewText() {
		return reviewtext;
	}

	public void setReviewText(String reviewtext) {
		this.reviewtext = reviewtext;
	}
	
	
}


