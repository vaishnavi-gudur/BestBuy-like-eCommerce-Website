import java.sql.*;
import java.io.*;
import java.util.*;

public class Authenticate
 {
     public static boolean checkUser(String username,String password) 
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
         ResultSet rs =ps.executeQuery();
         st = rs.next();
        
      }catch(Exception e)
      {
          e.printStackTrace();
      }
         return st;                 
  }   
}