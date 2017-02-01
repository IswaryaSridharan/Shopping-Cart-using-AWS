package com.org.cart.model.cart;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import java.util.*;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class ItemMarshaller implements DynamoDBMarshaller<List<Item>>
 
   {

    @Override
    public String marshall(List<Item> items) {
        return new Gson().toJson(items);
    }

    @Override
    public List<Item> unmarshall(Class<List<Item>> clazz, String s) {
        Type listType = new TypeToken<ArrayList<Item>>(){}.getType();
        return new Gson().fromJson(s, listType);
    }    
}
