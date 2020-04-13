package com.tqs.trabalho1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.trabalho1.model.*;

@Repository
public interface AirStatusCacheRepository extends JpaRepository<AirStatusCacheObject, AirStatusCachePK>{

	
}
