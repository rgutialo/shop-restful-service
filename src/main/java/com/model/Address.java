package com.model;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base class for Address Entity
 * 
 * @author Raul Gutierrez
 */
public class Address implements Serializable {
	
	private int number;
	private String zipCode;
	private String countryID;
	@Autowired
	private Geolocation geoloc;
	
	private static final long serialVersionUID = 6210366512436661558L;
	
	public Address()
	{
		this.number = 0;
		this.zipCode = "";
		this.countryID = "";
		this.geoloc = new Geolocation();
	}
	
	public Address (int number, String zipCode, String countID, Double latitude, Double longitude, Double hereLat, Double hereLon)
	{
		this.number = number;
		this.zipCode = zipCode;
		this.countryID = countID;	
		this.geoloc = new Geolocation(latitude, longitude, hereLat, hereLon);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Geolocation getGeoloc() {
		return geoloc;
	}

	public void setGeoloc(Geolocation geoloc) {
		this.geoloc = geoloc;
	}

	public String getCountryID() {
		return countryID;
	}

	public void setCountryID(String countryID) {
		this.countryID = countryID;
	}
	
	
	
	

	

}
