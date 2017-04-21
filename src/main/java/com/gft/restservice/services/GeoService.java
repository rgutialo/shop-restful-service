package com.gft.restservice.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.gft.restservice.model.Geolocation;
import com.gft.restservice.model.Shop;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;

/**
 * Base class for Geolocate places
 * 
 * @author Raul Gutierrez
 */
@Service
public class GeoService {

	private static final double R = 6372.8; // In kilometers

	/**
	 * Gets the distance between two points on Earth
	 * 
	 * @param lat1
	 *            latitude of point 1
	 * @param lon1
	 *            longitude of point 1
	 * @param lat2
	 *            latitude of point 2
	 * @param lon2
	 *            longitude of point 2
	 * @return the distance in kilometers
	 */
	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}

	/**
	 * Get the coordinates when receiving a zip Code and a Country ID.
	 * 
	 * @param shop
	 *            Shop to geolocate
	 * @return Geolocation object with latitude and longitude
	 */
	public CompletableFuture<Geolocation> geolocate(Shop pShop) {

		CompletableFuture<Geolocation> geoloc = new CompletableFuture<Geolocation>();

		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBkNfsZ867XNB90YE0aY-uAQmXZHCeAocA");
		GeocodingApiRequest req = GeocodingApi.newRequest(context).components(
				ComponentFilter.country(pShop.getShopAddress().getCountryID()),
				ComponentFilter.postalCode(pShop.getShopAddress().getZipCode()));

		req.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
			@Override
			public void onResult(GeocodingResult[] result) {
				if (result != null && result.length > 0) {
					Geometry geometry = result[0].geometry;
					geoloc.complete(new Geolocation(geometry.location.lat, geometry.location.lng, null, null));
				}
			}

			@Override
			public void onFailure(Throwable e) {

			}

		});
		return geoloc;
	}
}
