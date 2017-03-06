import java.*;
import java.io.*;
import java.util.HashMap;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *
 * @author nbuser
 */
public class ProductData {

    private HashMap products = new HashMap();

    public HashMap getProducts() {
        return products;
    }

    public ProductData() {
		
	Connection conn = null;
	try{	
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
	Statement stmt=conn.createStatement();
	String selectCustomerQuery="select * from products";
	ResultSet rs = stmt.executeQuery(selectCustomerQuery);
	while(rs.next())
	{
	Product p = new Product(rs.getString("id"), rs.getString("productName"));
	products.put(rs.getString("id"), p);
	}
	}
	catch(Exception e)
	{}
    }
}