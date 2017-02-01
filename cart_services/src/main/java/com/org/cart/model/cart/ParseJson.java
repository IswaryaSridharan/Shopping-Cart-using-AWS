package com.org.cart.model.cart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import com.google.gson.*;
import java.util.*;

 
public class ParseJson {
 
	public Cart parse(JsonObject obj) {
		Cart cart = new Cart();
		List<Item> items = new ArrayList<>();
		
		//HashMap<Integer,Double> map = new HashMap<Integer,Double>();
	//	jsonString = callURL("http://list-view-microservice.us-west-2.elasticbeanstalk.com/products");
	//	System.out.println("\n\njsonString: " + jsonString);

        cart.setLoginId(obj.get("loginId").getAsString());
        cart.setSku(obj.get("sku").getAsString());
		try {  
			JsonArray jsonArray = obj.getAsJsonArray("items");
			//System.out.println("\n\njsonArray: " + jsonArray);
			int count = jsonArray.size(); // get totalCount of all jsonObjects
			for(int i=0 ; i< count; i++){   // iterate through jsonArray 
				JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();  // get jsonObject @ i position 
				//System.out.println(jsonObject);
				String id = jsonObject.get("id").getAsString();
				double price = Double.parseDouble(jsonObject.get("price").getAsString());
				System.out.println("id : "+id + ", price : " +price);
				Item item = new Item(id, price);
				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cart.setItems(items);
		return cart;
	}
 
	
	public static String callURL(String myURL) {
		//System.out.println("Requested URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
		in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:"+ myURL, e);
		} 
 
		String urlString = sb.toString();
	//	var data = jsonString;

	//	var parsedData = JSON.parse(data);
	//	alert(parsedData.id);
	//	alert(parsedData.price);
		//alert(parsedData.jobtitel);

		return urlString;
	}
 
}
