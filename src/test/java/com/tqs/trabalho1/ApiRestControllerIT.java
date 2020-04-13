package com.tqs.trabalho1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs.trabalho1.model.AirStatus;
import com.tqs.trabalho1.model.AirStatusCacheObject;
import com.tqs.trabalho1.model.AirStatusCachePK;
import com.tqs.trabalho1.repositories.AirStatusCacheRepository;




@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Trabalho1Application.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ApiRestControllerIT {
	
	@Autowired
    private MockMvc mvc;

    @Autowired
    private AirStatusCacheRepository repository;
    
    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
    
    @Test
    public void givenAirStatusNotInCache_whenGetValidLocalAirStats_thenReturnAirStatAndCacheEntryCreated() throws Exception {
    	mvc.perform(get("/api/getAirQuality").contentType(MediaType.APPLICATION_JSON)
    			.param("country", "Portugal")
    			.param("state", "Aveiro")
    			.param("city", "Aveiro"))
    	.andExpect(jsonPath("$.country", is("Portugal")))
    	.andExpect(jsonPath("$.city", is("Aveiro")))
    	.andExpect(jsonPath("$.state", is("Aveiro")));
    	
    	
    	List<AirStatusCacheObject> found = repository.findAll();
        assertThat(found).extracting(AirStatusCacheObject::getPk).containsOnly(new AirStatusCachePK("Portugal", "Aveiro", "Aveiro"));
    }
    
    @Test
    public void givenAirStatusInCache_whenGetValidLocalAirStats_thenReturnAirStat() throws Exception {
    	AirStatusCacheObject as = new AirStatus(LocalDateTime.now().toString(),10, 10, 10, 10, 10, 10, 10, "Portugal", "Aveiro", "Aveiro").convertToCacheObject();
    	repository.saveAndFlush(as);
    	
    	mvc.perform(get("/api/getAirQuality").contentType(MediaType.APPLICATION_JSON)
    			.param("country", "Portugal")
    			.param("state", "Aveiro")
    			.param("city", "Aveiro"))
    	.andExpect(jsonPath("$.country", is("Portugal")))
    	.andExpect(jsonPath("$.city", is("Aveiro")))
    	.andExpect(jsonPath("$.state", is("Aveiro")));
    }
    
    @Test
    public void whenGetInvalidLocalAirStats_thenStatus404() throws Exception {
    	mvc.perform(get("/api/getAirQuality").contentType(MediaType.APPLICATION_JSON)
    			.param("country", "a")
    			.param("state", "b")
    			.param("city", "c"))
    	.andExpect(status().isNotFound());
    }
    
    @Test
    public void givenCacheEntries_whenGetCacheEntries_thenReturnCacheEntries() throws Exception {
    	AirStatusCacheObject as1 = new AirStatus(LocalDateTime.now().toString(),10, 10, 10, 10, 10, 10, 10, "Portugal", "Aveiro", "Aveiro").convertToCacheObject();
    	AirStatusCacheObject as2 = new AirStatus(LocalDateTime.now().toString(),20, 20, 20, 20, 20, 20, 20, "Israel", "Southern District", "Eilat").convertToCacheObject();
    	AirStatusCacheObject as3 = new AirStatus(LocalDateTime.now().toString(),30, 30, 30, 30, 30, 30, 30, "Portugal", "Lisbon", "Lisbon").convertToCacheObject();
		
    	repository.save(as1);
    	repository.save(as2);
    	repository.save(as3);
    	repository.flush();
    	
    	mvc.perform(get("/api/getCacheEntries").contentType(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
    	.andExpect(jsonPath("$[0].pk.country", is("Portugal")))
    	.andExpect(jsonPath("$[0].pk.city", is("Aveiro")))
    	.andExpect(jsonPath("$[0].pk.state", is("Aveiro")))
    	.andExpect(jsonPath("$[1].pk.country", is("Israel")))
    	.andExpect(jsonPath("$[1].pk.state", is("Southern District")))
    	.andExpect(jsonPath("$[1].pk.city", is("Eilat")))
    	.andExpect(jsonPath("$[2].pk.country", is("Portugal")))
    	.andExpect(jsonPath("$[2].pk.city", is("Lisbon")))
    	.andExpect(jsonPath("$[2].pk.state", is("Lisbon")));
    }
    
    @Test
    public void givenNoCacheEntries_whenGetCacheEntries_thenStatusNoContent() throws Exception {
    	mvc.perform(get("/api/getCacheEntries").contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isNoContent());
    }
    
    @Test
    public void getCacheStats_thenStatus200() throws Exception {
    	mvc.perform(get("/api/getStats").contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk());
    }
    
    

}
