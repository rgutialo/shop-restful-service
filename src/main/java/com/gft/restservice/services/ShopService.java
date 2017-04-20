package com.gft.restservice.services;

import java.util.Collections;
import java.util.List;

import com.gft.restservice.model.Shop;

/**
 * Base class for ShopServices
 * 
 * @author Raul Gutierrez
 */
public class ShopService {
	
	/**
	 * Get an ordered shop list  - from closest to farthest - based on coordinates received by params
	 * @param shopList list of shops
	 * @param lat  latitude
	 * @param lon  longitude
	 * @return an ordered shop list from closes to farthest 
	 */
	public static List<Shop> listByProximity (List<Shop> shopList, Double lat, Double lon){		
		for (Shop shopInstance : shopList)
			shopInstance.getShopAddress().getGeoloc().setDistanceKm(lat, lon);
		
		Collections.sort(shopList, (o1, o2) -> o1.compareTo(o2));  				
		return shopList;
	}
}
