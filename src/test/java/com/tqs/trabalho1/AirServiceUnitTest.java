package com.tqs.trabalho1;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.tqs.trabalho1.model.AirStatus;
import com.tqs.trabalho1.model.AirStatusCacheObject;
import com.tqs.trabalho1.model.CacheStatObject;
import com.tqs.trabalho1.repositories.AirStatusCacheRepository;
import com.tqs.trabalho1.services.AirService;
import com.tqs.trabalho1.services.CacheService;
import com.tqs.trabalho1.services.ExternalApiService;

@ExtendWith(MockitoExtension.class)
public class AirServiceUnitTest {

	
	//private AirStatusCacheRepository cache_repository;
	
	@Mock(lenient = true)
	private CacheService cache_service;
	
	@Mock(lenient = true)
	private ExternalApiService api_service;
	
	@InjectMocks
	private AirService air_service;
	
	@BeforeEach
	public void setUp() throws IOException, UnirestException {
		AirStatus as1 = new AirStatus(LocalDateTime.now().toString(),10, 10, 10, 10, 10, 10, 10, "Portugal", "Aveiro", "Aveiro");
		//AirStatus as2 = new AirStatus(LocalDateTime.now().toString(),20, 20, 20, 20, 20, 20, 20, "Israel", "Southern District", "Eilat");
		
		air_service.resetStatistics();
		
		Mockito.when(api_service.getAirStatus("Portugal", "Aveiro", "Aveiro")).thenReturn(as1);
		//Mockito.when(api_service.getAirStatus("Israel", "Southern District", "Eilat")).thenReturn(as2);
		Mockito.when(api_service.getAirStatus("a", "b", "c")).thenReturn(null);
	}
	
	@Test
	public void whenValidLocal_thenReturnAirStatus() throws IOException, UnirestException {
		String country = "Portugal";
		String state = "Aveiro";
		String city = "Aveiro";
		AirStatus as = air_service.getAirStatus(country, state, city);
		
		assertThat(as.getCountry()).isEqualTo(country);
		assertThat(as.getState()).isEqualTo(state);
		assertThat(as.getCity()).isEqualTo(city);
		
		CacheStatObject c = this.air_service.getStatistics();
		
		assertThat(c.getExternal_api_access()).isEqualTo(1);
		assertThat(c.getFailed_api_access()).isEqualTo(0);
		assertThat(c.getN_times_cached()).isEqualTo(1);
		
		verifyCheckCacheForRecordIsCalledOnce(country, state, city);
		verifyAddCacheRecordIsCalledOnce();
		verifyGetAirStatusIsCalledOnce(country, state, city);
		
	}
	
	@Test
	public void whenInvalidLocal_thenReturnNull() throws IOException, UnirestException {
		String country = "a";
		String state = "b";
		String city = "c";
		AirStatus as = air_service.getAirStatus(country, state, city);
		
		assertThat(as).isNull();
		
		CacheStatObject c = this.air_service.getStatistics();
		
		assertThat(c.getExternal_api_access()).isEqualTo(1);
		assertThat(c.getFailed_api_access()).isEqualTo(1);
		assertThat(c.getN_times_cached()).isEqualTo(0);
		
		verifyCheckCacheForRecordIsCalledOnce(country, state, city);
		verifyGetAirStatusIsCalledOnce(country, state, city);
		
	}
	
	@Test
	public void whenValidLocalInCache_thenReturnAirStatus() throws IOException, UnirestException {
		
		String country = "Portugal";
		String state = "Aveiro";
		String city = "Aveiro";
		
		Mockito.when(cache_service.checkCacheForRecord(country, state, city)).thenReturn(new AirStatus(LocalDateTime.now().toString(),10, 10, 10, 10, 10, 10, 10, "Portugal", "Aveiro", "Aveiro").convertToCacheObject());
		
		AirStatus as = air_service.getAirStatus(country, state, city);
		
		assertThat(as.getCountry()).isEqualTo(country);
		assertThat(as.getState()).isEqualTo(state);
		assertThat(as.getCity()).isEqualTo(city);
		
		CacheStatObject c = this.air_service.getStatistics();
		
		assertThat(c.getExternal_api_access()).isEqualTo(0);
		assertThat(c.getFailed_api_access()).isEqualTo(0);
		assertThat(c.getN_times_cached()).isEqualTo(0);
		
		verifyCheckCacheForRecordIsCalledOnce(country, state, city);
	}
	
	private void verifyCheckCacheForRecordIsCalledOnce(String country, String state, String city) {
		Mockito.verify(this.cache_service, VerificationModeFactory.times(1)).checkCacheForRecord(country, state, city);
        //Mockito.reset(this.cache_service);
	}
	
	private void verifyAddCacheRecordIsCalledOnce() {
		Mockito.verify(this.cache_service, VerificationModeFactory.times(1)).addCacheRecord(Mockito.any());
        //Mockito.reset(this.cache_service);
	}
	
	private void verifyGetAirStatusIsCalledOnce(String country, String state, String city) throws IOException, UnirestException {
		Mockito.verify(this.api_service, VerificationModeFactory.times(1)).getAirStatus(country, state, city);
        //Mockito.reset(this.api_service);
	}
	
}
