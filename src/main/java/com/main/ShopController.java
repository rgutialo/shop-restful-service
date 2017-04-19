package com.main;

import com.model.Shop;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class ShopController {

    private Map<String,Shop> shopsDemo;
    
    public ShopController()
    {
    	//Used ConcurrentHashMap in this example in order to allow concurrent transactions between users
    	shopsDemo  = new ConcurrentHashMap<String,Shop> ();
    }
      
    @RequestMapping(value="/shops", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public HttpEntity<List<Shop>> getShop(@RequestParam(value = "lat", required = false) Double lat, @RequestParam(value = "long", required = false) Double lon) 
    {
    	
    	List<Shop> tempShops = new ArrayList<Shop>(shopsDemo.values());
    	List<Shop> result = new ArrayList<Shop>();
    	
    	if (shopsDemo.isEmpty())
    	{
    		//return no shops found 
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	else
    	{
	    	//if we receive latitude and longitude params, we have to find the nearest shop
    		if (lat != null && lon != null)
	    	{
	    		for (Shop shopInstance : tempShops)
	    		{
	    			shopInstance.getShopAddress().getGeoloc().setDistanceKm(lat, lon);
	    		}
	    		
	    		Collections.sort(tempShops, (o1, o2) -> o1.compareTo(o2));
	    		
	    		result.add(tempShops.get(0));
	    		
	    		return new ResponseEntity<List<Shop>> (result, HttpStatus.OK);
	    		
	    	}
    		//else we will return all the shops in memory
	    	else
	    		result  = new ArrayList<Shop>(tempShops);
	    	
	    	return new ResponseEntity<List<Shop>> (result, HttpStatus.OK);
    	}
    }
    

    @RequestMapping(value="/shops", method = RequestMethod.POST)
    public HttpEntity<Shop> addShop(@RequestBody Shop shopParam) 
    {
    	//first we have to geolocate the shop we received. It's asynchronous in order to avoid deadlock
    	shopParam.geolocate();
    	
    	shopParam.add(linkTo(methodOn(ShopController.class, shopParam.getShopName()).findShop(shopParam.getShopName())).withSelfRel());
    	
    	if (!shopsDemo.containsKey(shopParam.getShopAddress()))
    		shopsDemo.put(shopParam.getShopName(),shopParam);
    	else
    		shopsDemo.replace(shopParam.getShopName(), shopParam);	
    	
    	//return the new shop created in memory
    	return new ResponseEntity<Shop> (shopParam, HttpStatus.OK);    		
    	
    }
    
    @GetMapping(path = "/{sName}")
    public HttpEntity<Shop> findShop(@PathVariable String sName) {
        
    	if (shopsDemo.containsKey(sName))
    		return new ResponseEntity<>(shopsDemo.get(sName), HttpStatus.OK);
    	else
    		//return no shops found 
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    
}
