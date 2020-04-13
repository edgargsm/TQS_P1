package com.tqs.trabalho1.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "AirStatusCacheObject")
public class AirStatusCacheObject {
	
	@EmbeddedId
	private AirStatusCachePK pk;
	
	private LocalDateTime ts;
	private int Us_AQI;
	private int Ch_AQI;
	private int temperature;
	private int pressure;
	private int humidity;
	private int wind_speed;
	private int wind_direction; // graus -> 0 = north, 90 = east
	private LocalDateTime cache_time; // time this information was cached
	
	public AirStatusCacheObject() {
	}

	public AirStatusCacheObject(AirStatusCachePK pk, LocalDateTime ts, int us_AQI, int ch_AQI, int temperature,
			int pressure, int humidity, int wind_speed, int wind_direction) {
		this.pk = pk;
		this.ts = ts;
		Us_AQI = us_AQI;
		Ch_AQI = ch_AQI;
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		this.wind_speed = wind_speed;
		this.wind_direction = wind_direction;
		this.cache_time = LocalDateTime.now();
	}

	public AirStatusCachePK getPk() {
		return pk;
	}

	public void setPk(AirStatusCachePK pk) {
		this.pk = pk;
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

	public LocalDateTime getCache_time() {
		return cache_time;
	}
	
	public void setCache_time(LocalDateTime cache_time) {
		this.cache_time = cache_time;
	}

	public AirStatus convertToAirStatus() {
		return new AirStatus(this.ts.toString(), this.Us_AQI, this.Ch_AQI, this.temperature, this.pressure,
				this.humidity, this.wind_speed, this.wind_direction, this.pk.getCountry(), this.pk.getState(), this.pk.getCity());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Ch_AQI;
		result = prime * result + Us_AQI;
		result = prime * result + ((cache_time == null) ? 0 : cache_time.hashCode());
		result = prime * result + humidity;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		result = prime * result + pressure;
		result = prime * result + temperature;
		result = prime * result + ((ts == null) ? 0 : ts.hashCode());
		result = prime * result + wind_direction;
		result = prime * result + wind_speed;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirStatusCacheObject other = (AirStatusCacheObject) obj;
		if (Ch_AQI != other.Ch_AQI)
			return false;
		if (Us_AQI != other.Us_AQI)
			return false;
		if (cache_time == null) {
			if (other.cache_time != null)
				return false;
		} else if (!cache_time.equals(other.cache_time))
			return false;
		if (humidity != other.humidity)
			return false;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		if (pressure != other.pressure)
			return false;
		if (temperature != other.temperature)
			return false;
		if (ts == null) {
			if (other.ts != null)
				return false;
		} else if (!ts.equals(other.ts))
			return false;
		if (wind_direction != other.wind_direction)
			return false;
		if (wind_speed != other.wind_speed)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AirStatusCacheObject [pk=" + pk + ", ts=" + ts + ", Us_AQI=" + Us_AQI + ", Ch_AQI=" + Ch_AQI
				+ ", temperature=" + temperature + ", pressure=" + pressure + ", humidity=" + humidity + ", wind_speed="
				+ wind_speed + ", wind_direction=" + wind_direction + ", cache_time=" + cache_time + "]";
	}

	
	
}
