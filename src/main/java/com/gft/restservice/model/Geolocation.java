package com.gft.restservice.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gft.restservice.services.GeoService;

/**
 * Base class for Geolocation Entity
 * 
 * @author Raul Gutierrez
 */
public class Geolocation implements Serializable {

	private Double latitude;
	private Double longitude;

	// calculated data from latitude and longitude in runtime
	@JsonIgnore
	private transient Double distance;

	private static final long serialVersionUID = 6210366512436661558L;

	public Geolocation() {
		this.latitude = null;
		this.longitude = null;
		this.distance = null;
	}

	public Geolocation(Double lat, Double lon, Double hereLat, Double hereLon) {
		this.latitude = lat;
		this.longitude = lon;
		if (hereLat == null || hereLon == null)
			this.distance = null;
		else
			setDistanceKm(hereLat, hereLon);
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Sets the distance in kilometers from a place to the point placed in the
	 * instance object
	 * 
	 * @param lat
	 *            latitude's point
	 * @param lon
	 *            longitude's point
	 * 
	 */
	public void setDistanceKm(Double lat, Double lon) {
		this.distance = GeoService.getDistance(this.getLatitude(), this.getLongitude(), lat, lon);
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
