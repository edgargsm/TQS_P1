package com.tqs.trabalho1.model;

import java.time.LocalDateTime;

public class AirStatus {
	
	private LocalDateTime ts;
	private int Us_AQI;
	private int Ch_AQI;
	private int temperature;
	private int pressure;
	private int humidity;
	private int wind_speed;
	private int wind_direction; // graus -> 0 = north, 90 = east
	private String country;
	private String city;
	private String state;	
	
	public AirStatus() {
		
	}

	public AirStatus(String ts, int us_AQI, int ch_AQI, int temperature, int pressure,
			int humidity, int wind_speed, int wind_direction, String country, String state, String city) {
		try {
			this.ts = LocalDateTime.parse(ts);
		}
		catch (Exception e) {
			this.ts = LocalDateTime.parse(ts.substring(0, ts.length()-1)); //Taking out the last letter because of the format the API gives the date
		}
		Us_AQI = us_AQI;
		Ch_AQI = ch_AQI;
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		this.wind_speed = wind_speed;
		this.wind_direction = wind_direction;
		this.country = country;
		this.city = city;
		this.state = state;
	}

	public LocalDateTime getTs() {
		return ts;
	}

	public void setTs(LocalDateTime ts) {
		this.ts = ts;
	}

	public int getUs_AQI() {
		return Us_AQI;
	}

	public void setUs_AQI(int us_AQI) {
		Us_AQI = us_AQI;
	}

	public int getCh_AQI() {
		return Ch_AQI;
	}

	public void setCh_AQI(int ch_AQI) {
		Ch_AQI = ch_AQI;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(int wind_speed) {
		this.wind_speed = wind_speed;
	}

	public int getWind_direction() {
		return wind_direction;
	}

	public void setWind_direction(int wind_direction) {
		this.wind_direction = wind_direction;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "AirStatus [ts=" + ts + ", Us_AQI=" + Us_AQI + ", Ch_AQI=" + Ch_AQI + ", temperature=" + temperature
				+ ", pressure=" + pressure + ", humidity=" + humidity + ", wind_speed=" + wind_speed
				+ ", wind_direction=" + wind_direction + ", country=" + country + ", city=" + city + ", state=" + state
				+ ", getTs()=" + getTs() + ", getUs_AQI()=" + getUs_AQI() + ", getCh_AQI()=" + getCh_AQI()
				+ ", getTemperature()=" + getTemperature() + ", getPressure()=" + getPressure() + ", getHumidity()="
				+ getHumidity() + ", getWind_speed()=" + getWind_speed() + ", getWind_direction()="
				+ getWind_direction() + ", getCountry()=" + getCountry() + ", getCity()=" + getCity() + ", getState()="
				+ getState() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	public AirStatusCacheObject convertToCacheObject() {
		return new AirStatusCacheObject(new AirStatusCachePK(this.country, this.state, this.city), this.ts, this.Us_AQI, this.Ch_AQI, this.temperature,
				this.pressure, this.humidity, this.wind_speed, this.wind_direction);
	}
	
	
}
