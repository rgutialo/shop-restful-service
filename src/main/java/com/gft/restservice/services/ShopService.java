package com.gft.restservice.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gft.restservice.model.Shop;
import com.gft.restservice.repos.ShopRepository;

/**
 * Base class for ShopServices
 * 
 * @author Raul Gutierrez
 */
@Service
public class ShopService {
	
	private ShopRepository shopRepo;
	
	
	public ShopService() {
		shopRepo = new ShopRepository();
	}
	
	public ShopService (ShopRepository sRepo){
		this.shopRepo = sRepo;
	}
	
	public Collection<Shop> shopsList() {
		return this.shopRepo.values();
	}
	
	/**
	 * Get an ordered shop list  - from closest to farthest - based on coordinates received by params
	 * @param shopList list of shops
	 * @param lat  latitude
	 * @param lon  longitude
	 * @return an ordered shop list from closes to farthest 
	 */
	public List<Shop> listByProximity (List<Shop> shopList, Double lat, Double lon){		
		for (Shop shopInstance : shopList)
			shopInstance.getShopAddress().getGeoloc().setDistanceKm(lat, lon);
		
		Collections.sort(shopList, (o1, o2) -> o1.compareTo(o2));  				
		return shopList;
	}
	
	public Shop getShop(String sName){
		return this.shopRepo.getShop(sName);
	}
	
	public Shop addShop(Shop newShop) {
		//first we have to geolocate the shop we received. It's asynchronous in order to avoid deadlock
		newShop.geolocate();
		return this.shopRepo.addShop(newShop);
	}
	
	public boolean isEmpty() {
		return this.shopRepo.isEmpty();
	}
	
	public boolean containsShop(String sName){
		return shopRepo.containsShop(sName);		
	}
}
