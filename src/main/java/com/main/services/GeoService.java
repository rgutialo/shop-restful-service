package com.main.services;


import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.model.Geolocation;

/**
 * Base class for Geolocate places
 * 
 * @author Raul Gutierrez
 */
public class GeoService {
	
	private static final double R = 6372.8; // In kilometers

	/**
	 * Gets the distance between two points on Earth
	 * @param lat1  latitude of point 1
	 * @param lon1  longitude of point 1
	 * @param lat2  latitude of point 2
	 * @param lon2  longitude of point 2
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
	 * @param zipCode 
	 * @param countryID
	 * @return Geolocation object inicialized with latitude and longitude
	 */
    public static Geolocation geolocate(String zipCode, String countryID) 
    {
    	
    	Geolocation geoloc = new Geolocation();
    	
    	GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBkNfsZ867XNB90YE0aY-uAQmXZHCeAocA"); 
    	 GeocodingApiRequest req = GeocodingApi.newRequest(context)
                 .components(ComponentFilter.country(countryID), ComponentFilter.postalCode(zipCode));

         req.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
             @Override
             public void onResult(GeocodingResult[] result) {
                 if (result != null && result.length > 0) {
                     Geometry geometry = result[0].geometry;
                     geoloc.setLatitude(geometry.location.lat);
                     geoloc.setLongitude(geometry.location.lng);
                 }
             }

             @Override
             public void onFailure(Throwable e) {
            	
             }
         });
         
         return geoloc;
    }

	
	

}
