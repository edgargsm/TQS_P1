package com.tqs.trabalho1.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.tqs.trabalho1.model.AirStatus;
import com.tqs.trabalho1.model.AirStatusCacheObject;
import com.tqs.trabalho1.model.CacheStatObject;
import com.tqs.trabalho1.services.AirService;
import com.tqs.trabalho1.services.CacheService;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private AirService air_service;
	
	@Autowired
	private CacheService cache_service;
	
	@GetMapping(path="/getAirQuality")
	public ResponseEntity<AirStatus> getAirQuality(@RequestParam(name="country", required=true)String country, @RequestParam(name="state", required=true)String state, @RequestParam(name="city", required=true)String city) throws IOException, UnirestException {
		AirStatus as = air_service.getAirStatus(country, state, city);
		if (as==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AirStatus>(as, HttpStatus.OK);
	}
	
	@GetMapping(path="/getCacheEntries")
	public ResponseEntity<List<AirStatusCacheObject>> getCacheEntries(){
		List<AirStatusCacheObject> l = cache_service.getCacheEntries();
		
		if(l.size()==0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<AirStatusCacheObject>>(l, HttpStatus.OK);
	}
	
	@GetMapping(path="/getStats")
	public ResponseEntity<CacheStatObject> getCacheStats() {
		return new ResponseEntity<CacheStatObject>(this.air_service.getStatistics(),HttpStatus.OK);
	}
	
}
