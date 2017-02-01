package com.org.cart.model.cart;

public class Item
{
	private String id;
	private Double price;
	
	public Item(String id, double price)
	{
		this.id = id;
		this.price = price;
	}
	
	public String getId()
	{
		return id;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public void setPrice(Double price)
	{
		this.price = price;
	}
}