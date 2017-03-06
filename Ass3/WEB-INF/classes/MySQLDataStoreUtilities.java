import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class MySQLDataStoreUtilities 
{
	public void insertUser(String username,String password,String usertype)
	{
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
			
			String insertIntoCustomerRegisterQuery = "INSERT INTO accounts(username,password,usertype)" + "VALUES(?,?,?);";
			PreparedStatement ps = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			ps.setString(1,username);
			ps.setString(2,password);
			ps.setString(3,usertype);
			ps.execute();		
			
		}
		catch(Exception e)
		{}
		
	}
	
	
	public static boolean selectUser(String username,String password) 
     {
      boolean st =false;
	  Connection conn = null;
      try{

         Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
         PreparedStatement ps = conn.prepareStatement
                             ("select username,password from accounts where username=? and password=?");
         ps.setString(1, username);
         ps.setString(2, password);
		 //ps.setString(3, usertype);
         ResultSet rs =ps.executeQuery();
         st = rs.next();
        
      }catch(Exception e)
      {
          e.printStackTrace();
      }
         return st;                 
  }


	public static void insertOrder(String username,String productName,String price,String creditCardNo,String address,int orderid)
		{ 
		Connection conn = null;
		try
		{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
		String insertIntoCustomerOrderQuery = "INSERT INTO Orders(username,productName,price,creditCardNo,address,orderid)" + "VALUES (?,?,?,?,?,?)";
		PreparedStatement pst =
		conn.prepareStatement(insertIntoCustomerOrderQuery);
		
		pst.setString(1,username);
		pst.setString(2,productName);
		pst.setString(3,price);
		pst.setString(4,creditCardNo);
		pst.setString(5,address);
		pst.setInt(6,orderid);
		
		pst.execute();
		}
		catch(Exception e){}
		}
		
	public static HashMap<String, ArrayList<OrderPayment>> selectOrder()
		{
		HashMap<String,ArrayList<OrderPayment>> orderPayments=new
		HashMap<String,ArrayList<OrderPayment>>();
		Connection conn = null;
		try{ 
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
		String selectOrderQuery = "select username,productName,price,creditCardNo,address,deliveryDate,orderid from Orders";
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();
		ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
		while(rs.next())
		{
			if(!orderPayments.containsKey(rs.getString("username")))
			{
			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
			orderPayments.put(rs.getString("username"), arr);
			}
			ArrayList<OrderPayment> listOrderPayment =orderPayments.get(rs.getString("username"));
			OrderPayment order= new OrderPayment(rs.getString("username"),rs.getString("productName"),rs.getString("price"),rs.getString("creditCardNo"),rs.getString("address"),rs.getString("deliveryDate"),rs.getInt("orderid"));
			listOrderPayment.add(order);
		}
		}
		catch(Exception e)
		{
		}
		return orderPayments;
		}



		public void insertProduct(String id,String productName,String brand,String cond,String price,String retailername,String retailercity,String retailerstate,String retailerzip,String category)
	{
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
			
			String insertIntoCustomerRegisterQuery = "INSERT INTO Products(id,productName,brand,cond,price,retailername,retailercity,retailerstate,retailerzip,category)" + "VALUES(?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement ps = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			ps.setString(1,id);
			ps.setString(2,productName);
			ps.setString(3,brand);
			ps.setString(4,cond);
			ps.setString(5,price);
			ps.setString(6,retailername);
			ps.setString(7,retailercity);
			ps.setString(8,retailerstate);
			ps.setString(9,retailerzip);
			ps.setString(10,category);
			ps.execute();		
			
		}
		catch(Exception e)
		{}
		
	}
		
		public static boolean selectProduct(String productName) 
     {
      boolean str =false;
	  Connection conn = null;
      try{

         Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
         PreparedStatement ps = conn.prepareStatement
                             ("select productName from Products where productName=?");
         ps.setString(1, productName);
		 //ps.setString(3, usertype);
         ResultSet rs =ps.executeQuery();
         str = rs.next();
        
      }catch(Exception e)
      {
          e.printStackTrace();
      }
         return str;                 
  }
		
	public static HashMap<String,Product> getData()
	{
		HashMap<String,Product> hm=new HashMap<String,Product>();
		Connection conn = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
			Statement stmt=conn.createStatement();
			String selectCustomerQuery="select * from Products";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while(rs.next())
			{
			Product p = new Product(rs.getString("id"), rs.getString("productName"), rs.getString("brand"),rs.getString("cond"),rs.getString("price"),rs.getString("retailername"),rs.getString("retailercity"),rs.getString("retailerstate"),rs.getString("retailerzip"),rs.getString("category"));
			hm.put(rs.getString("productName"), p);
			}
		}
		catch(Exception e)
		{}
		return hm;
	}	
	
	
		
		
}
	
