import java.io.*;
import java.util.*;


public class Item {

    int id = 0;
    String name = "";
    int quantity = 0;
    double price = 0;
   
    public Item()
    {
       
    }
  //
  // mutator methods
  //
    public void setId(int n)
    {
      id = n;
    }
     public void setName(String s)
    {
     name = s;
    }
     public void setQuantity(int n)
    {
      quantity = n;
    }
     
    public void setPrice(double n)
    {
      price = n;
    }
   //
   // Accessor methods
   //
    public int getId()
    {
      return id;
    }
    public String getName()
    {
      return name;
    }
    
   public int getQuantity()
   {
    return quantity;
   }
   
   public double getPrice()
   {
     return price;
   }
   
   
   @Override
   public String toString()
   {
     return id+" "+name+" "+quantity +" @ "+price;
   }
}