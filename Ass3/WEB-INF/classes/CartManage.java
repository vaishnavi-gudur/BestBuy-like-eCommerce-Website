import java.util.*;
import java.io.*;

public class CartManage {
	HashMap<String,String> cartProducts ;	
	public CartManage()
	{
		cartProducts = new HashMap<String,String>();
		
	}
	
	public HashMap getItems(){
		return cartProducts;
	}
	
	public void addtoCart (String productName , String price)
	{
		cartProducts.put(productName,price);
	}
}