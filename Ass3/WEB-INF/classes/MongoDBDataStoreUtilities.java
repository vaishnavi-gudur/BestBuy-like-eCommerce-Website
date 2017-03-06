import java.*;
import java.io.*;
import java.util.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;

public class MongoDBDataStoreUtilities
{
static DBCollection myReviews;
public static void getConnection()
{
MongoClient mongo;
mongo = new MongoClient("localhost", 27017);
DB db = mongo.getDB("CustomerReviews");
myReviews= db.getCollection("myReviews");
}

public static HashMap<String, ArrayList<Review>> selectReview()
{
getConnection();
HashMap<String, ArrayList<Review>> reviewHashmap=new HashMap<String, ArrayList<Review>>();
DBCursor cursor = myReviews.find();
while (cursor.hasNext())
{
BasicDBObject obj = (BasicDBObject) cursor.next();
if(! reviewHashmap.containsKey(obj.getString("pid")))
{
ArrayList<Review> arr = new ArrayList<Review>();
reviewHashmap.put(obj.getString("pid"), arr);
}
ArrayList<Review> listReview = reviewHashmap.get(obj.getString("pid"));
Review review =new Review(obj.getString("pid"),obj.getString("productModelName"),obj.getString("username"),obj.getString("category"),obj.getInt("price"),obj.getString("retailerName"),obj.getString("retailerCity"),obj.getString("retailerState"),obj.getString("retailerZip"),obj.getString("brand"),obj.getString("manufacturerrebate"),obj.getString("age"),obj.getString("gender"),obj.getString("occupation"),obj.getInt("rating"),obj.getString("reviewtext"));
listReview.add(review);
}
return reviewHashmap;
}

public static void insertReview(String pid,String productModelName,String username,String category,int price,String retailerName,String retailerCity,String retailerState,String retailerZip,String brand,String manufacturerrebate,String age,String gender,String occupation,int rating,String reviewtext)
{
getConnection();
BasicDBObject doc = new BasicDBObject("title", "myReviews");
doc.append("pid",pid);
doc.append("username", username);
doc.append("productModelName", productModelName);
doc.append("category", category);
doc.append("price", price);
doc.append("retailerName", retailerName);
doc.append("retailerCity", retailerCity);
doc.append("retailerState", retailerState);
doc.append("retailerZip", retailerZip);
doc.append("manufacturerrebate", manufacturerrebate);
doc.append("manufacturername", brand);
doc.append("age", age);
doc.append("gender", gender);
doc.append("occupation", occupation);
doc.append("rating", rating);
doc.append("reviewtext", reviewtext);
myReviews.insert(doc);
}
}
