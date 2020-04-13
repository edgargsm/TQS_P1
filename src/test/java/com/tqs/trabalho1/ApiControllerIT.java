package com.tqs.trabalho1;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.tqs.trabalho1.controllers.ApiController;
import com.tqs.trabalho1.model.AirStatus;
import com.tqs.trabalho1.model.AirStatusCacheObject;
import com.tqs.trabalho1.services.AirService;
import com.tqs.trabalho1.services.CacheService;



@WebMvcTest(ApiController.class)
public class ApiControllerIT {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirService air_service;
    
    @MockBean
    private CacheService cache_service;
    
    
    @Test
    public void whenGetAirQuality_thenReturnAirStatus() throws Exception {
    	AirStatus as = new AirStatus(LocalDateTime.now().toString(),10, 10, 10, 10, 10, 10, 10, "Portugal", "Aveiro", "Aveiro");
    	given(air_service.getAirStatus(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).willReturn(as);
    	
    	mvc.perform(get("/api/getAirQuality").contentType(MediaType.APPLICATION_JSON).param("country", "Portugal").param("state", "Aveiro").param("city", "Aveiro")).andExpect(jsonPath("$.country", is("Portugal"))).andExpect(jsonPath("$.city", is("Aveiro"))).andExpect(jsonPath("$.state", is("Aveiro")));
        verify(air_service, VerificationModeFactory.times(1)).getAirStatus(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        reset(air_service);
    }
    
    @Test
    public void givenCacheEntries_whenGetCacheEntrie_thenReturnAllCacheEntries() throws Exception {
    	AirStatusCacheObject as1 = new AirStatus(LocalDateTime.now().toString(),10, 10, 10, 10, 10, 10, 10, "Portugal", "Aveiro", "Aveiro").convertToCacheObject();
		AirStatusCacheObject as2 = new AirStatus(LocalDateTime.now().toString(),20, 20, 20, 20, 20, 20, 20, "Israel", "Southern District", "Eilat").convertToCacheObject();
		given(cache_service.getCacheEntries()).willReturn(Arrays.asList(as1, as2));
		
		mvc.perform(get("/api/getCacheEntries").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].pk.country", is("Portugal"))).andExpect(jsonPath("$[0].pk.city", is("Aveiro"))).andExpect(jsonPath("$[0].pk.state", is("Aveiro"))).andExpect(jsonPath("$[1].pk.country", is("Israel"))).andExpect(jsonPath("$[1].pk.state", is("Southern District"))).andExpect(jsonPath("$[1].pk.city", is("Eilat")));
        verify(cache_service, VerificationModeFactory.times(1)).getCacheEntries();
        reset(cache_service);
    }
	
}
