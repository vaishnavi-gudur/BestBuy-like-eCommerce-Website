import java.io.*;
import java.util.*;


public class ShoppingCart {
   
    ArrayList<Item> itemlist = new ArrayList<Item>();  // list of Items in the cart
    java.text.DecimalFormat currency = new java.text.DecimalFormat("$ #,###,##0.00");
   
    public ShoppingCart()
    {
       
    }
     public void empty()
     {
       itemlist.clear();
     }
    //
     // add an item to the cart
     // if its there already, just update the upc
     //  
    public void add(Item anitem)
    {
   
     for(int i = 0; i < itemlist.size(); i++)
     {
     Item item = itemlist.get(i);
      if(anitem.id == item.id) // already in the cart?
      {
       item.quantity += anitem.quantity; // yes, just update the quantity
       return;
      }
     }
     itemlist.add(anitem); // no, add it as a new item
    }
    //
    // remove an item with a given id from the shopping cart
    //
    public void remove(int id)
    {
   
     for(int i = 0; i < itemlist.size(); i++)
     {
      Item item = itemlist.get(i);
      if(id == item.id) // item in the cart?
      {
       itemlist.remove(i); // remove it
       break;
      }
     }
     
    }
   //
   // Display the current contents of the cart as an html table
   //
   public void display(JspWriter out) throws IOException
     {
      //
      // start the table and output the header row
      //
      out.println("<h3>Cart contents</h3>");
      out.println("<table border=1>");
      out.println("<tr><th>ID</th><th>Name</th><th>Price</th><th>Quantity</th><th>Total</th></tr>");
   
      double total = 0;
      //
      // output one item at a time from the cart, one item to a row table
      //
      for(int i = 0; i < itemlist.size(); i++)
      {
       Item item = (Item)itemlist.get(i);
       out.println("<tr><td>"+item.id+"</td>"+
                  "<td>"+item.name+"</td>"+
                  "<td align=right>"+ currency.format(item.price)+"</td>"+
                  "<td align=right>"+ item.quantity+"</td>"+
                  "<td align=right>"+ currency.format(item.price*item.quantity)+"</td>"+
                  "<td align=center><A href='removeItemFromCart.jsp?id="+item.id+"'>remove</A></TD></tr>");
       total += item.price*item.quantity;
      }
      //
      // add summary information (total, tax, grand total)
      //
       out.println("<tr><td colspan = 4>Total purchase</td>");
       out.println("<td align=right>"+currency.format(total)+"</td></tr>");
       out.println("<tr><td colspan = 4>Sales tax @6%</td>"+
                  "<td align=right>"+ currency.format(total*.06)+"</td></tr>");
       out.println("<tr><td colspan = 4>Amount due</td>"+
                  "<td align=right>"+ currency.format(total*1.06)+"</td></tr>");
       out.println("</table>");
       out.println("<a href='checkOut.jsp'><input type='Button' value='Register'/></A><br/>");

     }
     
   public int checkOut(String url, String studentid) 
    {
        int result = 0; // tally the classes added
        try{
        // open a connection
          Connection con = null;
          Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  // load the driver
          con = DriverManager.getConnection(url);
//        
// add entry to enroll table for each item
//
        PreparedStatement prep = con.prepareStatement("Insert into enroll (StudentID, CRN) values (?,?)");
      
        for(int i = 0; i < itemlist.size(); i++)
        {
           Item item = itemlist.get(i);
           prep.setString(1, studentid);
           prep.setInt(2, item.id);
           result += prep.executeUpdate();
        }
        itemlist.clear(); //empty the cart
        prep.close();
        con.close();
        }
        catch(Exception ex)
        {
          // unable to close the prepared statement  
        }
        return result;
    }
   
    
}
