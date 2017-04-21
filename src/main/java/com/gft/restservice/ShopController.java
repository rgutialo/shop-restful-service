package com.gft.restservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gft.restservice.model.Shop;
import com.gft.restservice.services.ShopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class ShopController {

	@Autowired
	ShopService shopserv;

	@RequestMapping(value = "/shops", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
	public HttpEntity<List<Shop>> getShop(@RequestParam(value = "lat", required = false) Double lat,
			@RequestParam(value = "long", required = false) Double lon) {

		List<Shop> tempShops = new ArrayList<Shop>(shopserv.shopsList());
		List<Shop> result = new ArrayList<Shop>();

		if (shopserv.isEmpty())
			// return no shops found
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		else {
			// if we receive latitude and longitude params, we have to find the
			// nearest shop
			if (lat != null && lon != null) {
				// we use shop service to get distances
				shopserv.listByProximity(tempShops, lat, lon);
				result.add(tempShops.get(0));
				return new ResponseEntity<List<Shop>>(result, HttpStatus.OK);
			}
			// else we will return all the shops in memory
			else
				result = new ArrayList<Shop>(tempShops);

			return new ResponseEntity<List<Shop>>(result, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/shops", method = RequestMethod.POST)
	public HttpEntity<Shop> addShop(@RequestBody Shop shopParam) {
		// add the shop to repository and receive previous version (if exists)
		Shop previousShop = shopserv.addShop(shopParam);
		shopParam.add(linkTo(methodOn(ShopController.class, shopParam.getShopName()).findShop(shopParam.getShopName()))
				.withSelfRel());

		if (previousShop == null)
			// no previous shop in memory. Return the new shop created in memory
			return new ResponseEntity<Shop>(shopParam, HttpStatus.CREATED);
		else
			// return previous shop in memory
			return new ResponseEntity<Shop>(previousShop, HttpStatus.OK);
	}

	@GetMapping(path = "/{sName}")
	public HttpEntity<Shop> findShop(@PathVariable String sName) {
		if (shopserv.containsShop(sName))
			return new ResponseEntity<>(shopserv.getShop(sName), HttpStatus.OK);
		else
			// return no shops found
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
