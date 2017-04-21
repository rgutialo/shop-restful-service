package com.gft.restservice.repos;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.gft.restservice.model.Shop;

/**
 * Repository class of Shops
 * 
 * @author Raul Gutierrez
 */
@Repository
public class ShopRepository {
	
	private Map<String,Shop> shopsDemo; 
	
	public ShopRepository() {    	
    	//Used ConcurrentHashMap in this example in order to allow concurrent transactions between users
    	shopsDemo  = new ConcurrentHashMap<String,Shop> ();    	
    }
	
	public Collection<Shop> values() {
		return this.shopsDemo.values();
	}
	
	public boolean isEmpty() {
		return this.shopsDemo.isEmpty();
	}
	
	/**
	 * Add a shop to repository
	 * @param shop Shop to add
	 * @return return the shop that was added or last version of same shop if exists previously
	 */
	public Shop saveShop(Shop newShop) {
		Shop previousShop = shopsDemo.put(newShop.getShopName(), newShop);		
		return previousShop;
	}
	
	public boolean containsShop(String sName){
		return shopsDemo.containsKey(sName);		
	}
	
	public Shop getShop(String sName){
		return this.shopsDemo.get(sName);
	}

}
