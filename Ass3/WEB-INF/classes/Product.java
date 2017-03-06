import java.io.*;
import java.util.*;


public class Product 
	{
	 private String id;
	 private String productName;
	 private String brand;
	 private String cond;
	 private String price;
	 private String retailername;
	 private String retailercity;
	 private String retailerstate;
	 private String retailerzip;
	 private String category;
	 	 
	public Product(String id,String productName,String brand,String cond,String price,String retailername,String retailercity,String retailerstate,String retailerzip,String category)
	{
			 this.id = id;
			 this.productName = productName;
			 this.brand = brand;
			 this.cond = cond;
			 this.price = price;
			 this.retailername = retailername;
			 this.retailercity = retailercity;
			 this.retailerstate = retailerstate;
			 this.retailerzip = retailerzip;
			 this.category = category;
			
	} 
	
	public Product()
	{}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
	this.id = id;
	}
	
	public String getproductName()
	{
		return productName;
	}
	
	public void setproductName(String productName)
	{
	this.productName = productName;
	}
	
	public String getBrand()
	{
		return brand;
	}
	
	public void setBrand(String brand)
	{
	this.brand = brand;
	}
	
	public String getCondition()
	{
		return cond;
	}
	
	public void setCondition(String cond)
	{
	this.cond = cond;
	}
	
	public String getPrice()
	{
		return price;
	}
	
	public void setPrice(String price)
	{
	this.price = price;
	}
	
	public String getRetailerName()
	{
		return retailername;
	}
	
	public void setRetailerName(String retailername)
	{
	this.retailername = retailername;
	}
	
	public String getRetailerCity()
	{
		return retailercity;
	}
	
	public void setRetailerCity(String retailercity)
	{
	this.retailercity = retailercity;
	}
	
	public String getRetailerState()
	{
		return retailerstate;
	}
	
	public void setRetailerState(String retailerstate)
	{
	this.retailerstate = retailerstate;
	}
	
	public String getRetailerZip()
	{
		return retailerzip;
	}
	
	public void setRetailerZip(String retailerzip)
	{
	this.retailerzip = retailerzip;
	}
	
	
	public String getCategory()
	{
		return category;
	}
	
	public void setCategory(String category)
	{
	this.category = category;
	}
}