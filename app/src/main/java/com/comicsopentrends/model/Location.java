package com.comicsopentrends.model;

public class Location{
	private boolean isCountry;
	private String countryCode;
	private String name;
	private int id;

	public void setIsCountry(boolean isCountry){
		this.isCountry = isCountry;
	}

	public boolean isIsCountry(){
		return isCountry;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"Location{" + 
			"isCountry = '" + isCountry + '\'' + 
			",countryCode = '" + countryCode + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
