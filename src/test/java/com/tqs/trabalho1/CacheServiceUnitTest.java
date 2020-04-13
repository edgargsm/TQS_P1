package com.tqs.trabalho1;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import com.tqs.trabalho1.model.AirStatus;
import com.tqs.trabalho1.model.AirStatusCacheObject;
import com.tqs.trabalho1.model.AirStatusCachePK;
import com.tqs.trabalho1.repositories.AirStatusCacheRepository;
import com.tqs.trabalho1.services.CacheService;


@ExtendWith(MockitoExtension.class)
public class CacheServiceUnitTest {
	
	@Mock( lenient = true)
	private AirStatusCacheRepository air_status_repository;
	
	@InjectMocks
	private CacheService cache_service;
	
	
	@BeforeEach
	public void setUp() {
		AirStatusCacheObject as1 = new AirStatus(LocalDateTime.now().toString(),10, 10, 10, 10, 10, 10, 10, "Portugal", "Aveiro", "Aveiro").convertToCacheObject();
		AirStatusCacheObject as2 = new AirStatus(LocalDateTime.now().toString(),20, 20, 20, 20, 20, 20, 20, "Israel", "Southern District", "Eilat").convertToCacheObject();
		
		List<AirStatusCacheObject> l = Arrays.asList(as1, as2);

		Mockito.when(air_status_repository.findAll()).thenReturn(l);
		Mockito.when(air_status_repository.findById(new AirStatusCachePK("Portugal", "Aveiro", "Aveiro"))).thenReturn(Optional.of(as1));
		Mockito.when(air_status_repository.findById(new AirStatusCachePK("Israel", "Southern District", "Eilat"))).thenReturn(Optional.of(as2));
		Mockito.when(air_status_repository.findById(new AirStatusCachePK("a", "b", "c"))).thenReturn(Optional.empty());
	}
	
	@Test
	public void given2AirStatus_whenGetCacheEntries_thenReturn2Entries() {
		List<AirStatusCacheObject> l = cache_service.getCacheEntries();
		verifyFindAllIsCalledOnce();
		
		assertThat(l).hasSize(2).extracting(AirStatusCacheObject::getPk).contains(new AirStatusCachePK("Portugal", "Aveiro", "Aveiro"), new AirStatusCachePK("Israel", "Southern District", "Eilat"));
	}
	
	@Test
	public void whenValidLocation_thenReturnCacheEntrie() {
		String country = "Portugal";
		String state = "Aveiro";
		String city = "Aveiro";
		AirStatusCacheObject as = cache_service.checkCacheForRecord(country, state, city);
		
		assertThat(as.getPk().getCountry()).isEqualTo(country);
		assertThat(as.getPk().getState()).isEqualTo(state);
		assertThat(as.getPk().getCity()).isEqualTo(city);
		
		verifyFindByIdIsCalledOnce();
	}
	
	@Test
	public void whenInvalidLocation_thenReturnNull() {
		String country = "a";
		String state = "b";
		String city = "c";
		AirStatusCacheObject as = cache_service.checkCacheForRecord(country, state, city);
		
		assertThat(as).isNull();
		
		verifyFindByIdIsCalledOnce();
	}
	
	private void verifyFindAllIsCalledOnce() {
		Mockito.verify(air_status_repository, VerificationModeFactory.times(1)).findAll();
        //Mockito.reset(air_status_repository);
	}
	
	private void verifyFindByIdIsCalledOnce() {
		Mockito.verify(air_status_repository, VerificationModeFactory.times(1)).findById(Mockito.any());
		//Mockito.reset(air_status_repository);
	}

}
