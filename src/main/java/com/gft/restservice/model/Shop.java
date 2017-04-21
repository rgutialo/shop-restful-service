package com.gft.restservice.model;

import java.util.Comparator;

import org.springframework.hateoas.ResourceSupport;


/**
 * Base class for Shop Entity
 * 
 * @author Raul Gutierrez
 */
public class Shop extends ResourceSupport implements Comparable<Shop> {
	
	private String shopName;	
	private Address shopAddress;
	

	public Shop() {
		this.shopName = "";
		this.shopAddress = new Address();
	}
	
	public Shop (String sName, Address sAddress){
		this.shopName = sName;
		this.shopAddress = sAddress;
	}
	
	public Shop (String sName, int sNumber, String zipCode, String countID, Double latitude, Double longitude, Double hereLat, Double hereLong){
		this.shopName = sName;
		this.shopAddress = new Address(sNumber, zipCode, countID, latitude, longitude, hereLat, hereLong);
	}		
	
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Address getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(Address shopAddress) {
		this.shopAddress = shopAddress;
	}		

	/**
	 * Overrides compareTo method from Comparable in order to use sort from Collection
	 * @param shop0 external shop which is compared
	 * @returns int integer value 
	 */
	@Override
	public int compareTo(Shop shop0) {
		if (this.getShopAddress().getGeoloc().getLatitude() == null ||shop0.getShopAddress().getGeoloc().getLongitude() == null) 
            return -1;
        else if (this.getShopAddress().getGeoloc().getLatitude()  == null || shop0.getShopAddress().getGeoloc().getLongitude() == null) 
            return 1;
         else             
            return Double.compare(this.getShopAddress().getGeoloc().getDistance(), shop0.getShopAddress().getGeoloc().getDistance());
	}
	
	public class ShopComparator implements Comparator<Shop> {
	    @Override
	    public int compare(Shop o1, Shop o2) {
	        return o1.compareTo(o2);
	    }
	}
}
