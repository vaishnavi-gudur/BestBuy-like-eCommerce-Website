import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class MySQLDataStoreUtilities 
{
	public void insertUser(String username,String password,String usertype)
	{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase",username,password);
			
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
	
	
	public static boolean selectUser()
		{
			boolean st = false;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase",username,password);
			
			String selectCustomerRegisterQuery = "SELECT * FROM accounts where username=? and password=?";
			PreparedStatement ps = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			ps.setString(1,username);
			ps.setString(2,password);
			ResultSet rs = ps.executeQuery();
			st = rs.next();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return st;
	}
	
	
}