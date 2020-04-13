package com.tqs.trabalho1;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import com.tqs.trabalho1.model.AirStatus;
import com.tqs.trabalho1.model.AirStatusCacheObject;
import com.tqs.trabalho1.model.AirStatusCachePK;
import com.tqs.trabalho1.repositories.AirStatusCacheRepository;

@DataJpaTest
public class AirStatusCacheRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private AirStatusCacheRepository cache_repository;
	
	@Test
	public void givenSetOfAirStatusCacheObjects_whenFindAll_returnAllCacheEntries() {
		AirStatusCacheObject as1 = new AirStatus(LocalDateTime.now().toString(),10, 10, 10, 10, 10, 10, 10, "Portugal", "Aveiro", "Aveiro").convertToCacheObject();
		AirStatusCacheObject as2 = new AirStatus(LocalDateTime.now().toString(),20, 20, 20, 20, 20, 20, 20, "Israel", "Southern District", "Eilat").convertToCacheObject();
		AirStatusCacheObject as3 = new AirStatus(LocalDateTime.now().toString(),30, 30, 30, 30, 30, 30, 30, "Portugal", "Lisbon", "Lisbon").convertToCacheObject();

		entityManager.persist(as1);
        entityManager.persist(as2);
        entityManager.persist(as3);
        entityManager.flush();
        
        List<AirStatusCacheObject> l = cache_repository.findAll();
        
        assertThat(l).hasSize(3).extracting(AirStatusCacheObject::getPk).containsOnly(as1.getPk(), as2.getPk(), as3.getPk());
	}
	
	@Test
	public void whenValidId_thenReturnCacheEntrie() {
		AirStatusCacheObject as = new AirStatus(LocalDateTime.now().toString(),10, 10, 10, 10, 10, 10, 10, "Portugal", "Aveiro", "Aveiro").convertToCacheObject();
		entityManager.persistAndFlush(as);
		
		AirStatusCacheObject get = cache_repository.findById(as.getPk()).orElse(null);
		assertThat(get.getPk()).isEqualTo(as.getPk());
	}
	
	@Test
	public void whenInvalidId_thenNull() {
		AirStatusCacheObject get = cache_repository.findById(new AirStatusCachePK("a", "b", "c")).orElse(null);
		assertThat(get).isNull();
	}

}
