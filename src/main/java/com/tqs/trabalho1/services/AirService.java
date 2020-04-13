package com.tqs.trabalho1.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tqs.trabalho1.model.*;
import com.tqs.trabalho1.repositories.AirStatusCacheRepository;


@Service
public class AirService {
	
	@Autowired
	private CacheService cache_service;
	
	@Autowired
	private ExternalApiService api_service;
	
	private int external_api_access;
	private int n_times_cached;
	private int failed_api_access;
	
	public AirService() {
		this.external_api_access = 0;
		this.n_times_cached = 0;
		this.failed_api_access = 0;
	}
	
	public AirStatus getAirStatus(String country, String state, String city) throws IOException, UnirestException {
		
		AirStatusCacheObject cache_obj = cache_service.checkCacheForRecord(country, state, city);
		
		if (cache_obj!=null) {
			return cache_obj.convertToAirStatus();
		}
		
		AirStatus as = this.api_service.getAirStatus(country, state, city);
		this.external_api_access = this.external_api_access + 1;
		
		if (as == null) {
			this.failed_api_access = this.failed_api_access + 1;
			return null;
		}
		
		this.n_times_cached = this.n_times_cached + 1; 
	    cache_service.addCacheRecord(as.convertToCacheObject());
	    return as;
		
	}
	
	public CacheStatObject getStatistics() {
		return new CacheStatObject(this.external_api_access, this.n_times_cached, this.failed_api_access);
	}
	
	public void resetStatistics() {
		this.external_api_access = 0;
		this.failed_api_access = 0;
		this.n_times_cached = 0;
	}
	
}
