package com.tqs.trabalho1.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tqs.trabalho1.model.AirStatus;

@Service
public class ExternalApiService {
	
	public AirStatus getAirStatus(String country, String state, String city) throws IOException, UnirestException {
		
		String key = "4a5675e5-65c9-4609-9a56-ebe6cfe96a94";
		String url = "https://api.airvisual.com/v2/city?key="+key+"&country="+URLEncoder.encode(country, StandardCharsets.UTF_8.toString())+"&state="+URLEncoder.encode(state, StandardCharsets.UTF_8.toString())+"&city="+URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
		
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest.get(url)
		  .asString();
		
		try {
			JSONObject myResponse = new JSONObject(response.getBody());
		    JSONObject data = myResponse.getJSONObject("data");
		    JSONObject current = data.getJSONObject("current");
		    JSONObject weather = current.getJSONObject("weather");
		    JSONObject pollution = current.getJSONObject("pollution");
		    
		    AirStatus as = new AirStatus(weather.getString("ts"), pollution.getInt("aqius"), pollution.getInt("aqicn"), weather.getInt("tp"), weather.getInt("pr"), weather.getInt("hu"), weather.getInt("ws"), weather.getInt("wd"), data.getString("country"), data.getString("state"), data.getString("city"));
		    //this.n_times_cached = this.n_times_cached + 1; 
		    //cache_service.addCacheRecord(as.convertToCacheObject());
		    return as;
		    
		}
		catch(Exception e) {
			//this.failed_api_access = this.failed_api_access + 1;
			return null;
		}
		
	}

}
