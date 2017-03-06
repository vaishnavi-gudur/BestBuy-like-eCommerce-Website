import java.io.*;
import java.io.ObjectInputStream;
import org.w3c.dom.*;
import javax.servlet.*;
import javax.xml.parsers.*;
import javax.servlet.http.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.*;



import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DealMatch
{
static String tableoutput="";

public static String get()throws ServletException,IOException{
	
	HashMap<String,Product> selectedProducts=new HashMap<String,Product>();
	
		tableoutput+="<div= id='content'>";
		tableoutput+="<div class='post'>";
		tableoutput+="<h2 class='title'>";
		tableoutput+="<a href=''></h2>";
		tableoutput+="<div class='entry'>";
		tableoutput+="<br><br>";
		tableoutput+="<h2>The Deal Match</h2>";
		String line=null;
		HashMap<String,Product> productMap=MySQLDataStoreUtilities.getData();
	
	for(Map.Entry<String,Product> entry:productMap.entrySet())
	{
		if(selectedProducts.size()<2&&!selectedProducts.containsKey(entry.getKey())){
			BufferedReader reader= new BufferedReader(new FileReader(new File("C:\\apache-tomcat-7.0.34\\webapps\\Ass3\\DealMatches.txt")));
			line=reader.readLine();
			if(line==null)
			{
				tableoutput+="<h2 align='center'>No Offers Found</h2>";
				break;
			}
			else{
				do{
					
					if(line.contains(entry.getKey()))
					{
						if(entry.getKey()!="")
						{
						tableoutput+="<h2>"+line+"</h2>";
						tableoutput+="<br>";
						selectedProducts.put(entry.getKey(),entry.getValue());
						break;
						}
		
					}
					else{
						//tableoutput+="<h2>"+"maddy"+entry.getKey()+"</h2>";
					}
					
				}while((line=reader.readLine())!=null);
			}
			reader.close();
			
		}
	}
	
	
	Iterator it = selectedProducts.entrySet().iterator();
	 tableoutput+="<table><tr>";
	 
	 while (it.hasNext())
	{
		
		Map.Entry pi = (Map.Entry)it.next();
		Product p=(Product)pi.getValue();
		
		//if (p.getProductName().toLowerCase().startsWith(searchId))
		{
			tableoutput+="<td><table>";
			tableoutput+="<tr><td>" + p.getproductName()+"</td></tr>";
			tableoutput+="<tr><td>"+"$"+p.getPrice()+"</td></tr>";
			tableoutput+="<tr><td>" + "<img src='images/"+p.getBrand()+".jpeg'></td></tr>";
		
		
		
		tableoutput+="<tr><td><form action='addtocart'>"+
		"<input type='hidden' name='id' value='" + p.getId()+"'>"+
		"<input type='hidden' name='productName' value='" + p.getproductName()+"'>"+
		"<input type='hidden' name='price' value='" + p.getPrice()+"'>"+
		"<input type='hidden' name='retailerzip' value='" + p.getRetailerZip()+"'>"+
		"<input type='hidden' name='retailerstate' value='" + p.getRetailerState()+"'>"+
		"<input type='hidden' name='retailercity' value='" + p.getRetailerCity()+"'>"+
		"<input type='hidden' name='category' value='" + p.getCategory()+"'>"+
		"<input type='hidden' name='brand' value='" + p.getBrand()+"'>"+
		"<input type='submit' value='Buy Now'></form ></td></tr>";
		//"<td><input type='submit' value='Review'></td>";
		tableoutput+="<tr><td><form action='writereview'>"+
		"<input type='hidden' name='id' value='" + p.getId()+"'>"+
		"<input type='hidden' name='productName' value='" + p.getproductName()+"'>"+
		"<input type='hidden' name='price' value='" + p.getPrice()+"'>"+
		"<input type='hidden' name='retailerzip' value='" + p.getRetailerZip()+"'>"+
		"<input type='hidden' name='retailerstate' value='" + p.getRetailerState()+"'>"+
		"<input type='hidden' name='retailercity' value='" + p.getRetailerCity()+"'>"+
		"<input type='hidden' name='category' value='" + p.getCategory()+"'>"+
		"<input type='hidden' name='brand' value='" + p.getBrand()+"'>"+
		"<input type='submit' value='Write Review'></form ></td></tr>";
		tableoutput+="<tr><td><form action='mongofind'>"+
		"<input type='hidden' name='id' value='" + p.getId()+"'>"+
		"<input type='hidden' name='productName' value='" + p.getproductName()+"'>"+
		"<input type='hidden' name='price' value='" + p.getPrice()+"'>"+
		"<input type='hidden' name='retailerzip' value='" + p.getRetailerZip()+"'>"+
		"<input type='hidden' name='retailerstate' value='" + p.getRetailerState()+"'>"+
		"<input type='hidden' name='retailercity' value='" + p.getRetailerCity()+"'>"+
		"<input type='hidden' name='category' value='" + p.getCategory()+"'>"+
		"<input type='hidden' name='brand' value='" + p.getBrand()+"'>"+
		"<input type='submit' value='View Review'></form ></td></tr>";
		tableoutput+="</table></td>";
		
		
		
		}
		
		
	}
	
	tableoutput+="</tr></table>";
	return tableoutput;
}

    
}