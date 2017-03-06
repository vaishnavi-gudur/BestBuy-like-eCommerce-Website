import java.*;
import java.io.*;
import java.util.*;

public void StoreReview(String productModelName,String username,String category,String price,String retailerName,String retailerCity,String retailerState,String retailerZip,String brand,String manufacturerrebate,String age,String gender,String occupation,String rating,String reviewtext)

{
HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
try
{
reviews=MongoDBDataStoreUtilities.selectReview();}
catch(Exception e)
{ 
}
if(!reviews.containsKey(productModelName)){
ArrayList<Review> arr = new ArrayList<Review>();
reviews.put(productModelName, arr);
}
ArrayList<Review> listReview = reviews.get(productModelName);
Review review = new Review(productModelName,username,category,price,retailerName,retailerCity,retailerState,retailerZip,brand,manufacturerrebate,age,gender,occupation,rating,reviewtext)
listReview.add(review);
try
{
MongoDBDataStoreUtilities.insertReview(productModelName,username,category,price,retailerName,retailerCity,retailerState,retailerZip,brand,manufacturerrebate,age,gender,occupation,rating,reviewtext)
}
catch(Exception e)
{ 
}
}
