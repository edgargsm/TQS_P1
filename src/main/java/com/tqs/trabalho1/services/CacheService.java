package com.tqs.trabalho1.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tqs.trabalho1.model.AirStatusCacheObject;
import com.tqs.trabalho1.model.AirStatusCachePK;
import com.tqs.trabalho1.repositories.AirStatusCacheRepository;

@Service
public class CacheService {
	
	@Autowired
	private AirStatusCacheRepository air_status_repository;
	
	public List<AirStatusCacheObject> getCacheEntries() {
		List<AirStatusCacheObject> l = air_status_repository.findAll();
		return l;
	}
	
	public AirStatusCacheObject checkCacheForRecord(String country, String state, String city) {
		Optional<AirStatusCacheObject> cache_obj = air_status_repository.findById(new AirStatusCachePK(country, state, city));
		if (!cache_obj.isEmpty()) {
			return cache_obj.get();
		}
		return null;
	}
	
	public void addCacheRecord(AirStatusCacheObject as) {
		air_status_repository.saveAndFlush(as);
	}
	
	@Scheduled(initialDelay = 10 * 1000, fixedDelay = 5 * 60 * 1000)
    public void checkOutdatedCaches() {
        List<AirStatusCacheObject> caches = air_status_repository.findAll();
        
        caches.forEach(this::checkDate);
    }
	
	private void checkDate(AirStatusCacheObject obj) {
		LocalDateTime cache_time = obj.getCache_time();
		if (cache_time.plusMinutes(5).isAfter(LocalDateTime.now())) {
			air_status_repository.delete(obj);
		}
	}

}
