package com.cos.opgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.opgg.api.model.SummonerModel;

public interface SummonerRepository extends JpaRepository<SummonerModel, Integer>{
	
	@Query(value = "SELECT * FROM summonermodel WHERE LOWER(REPLACE(name,' ', '')) LIKE ?1", nativeQuery = true)
	SummonerModel findByName(String name);
	
	SummonerModel findByAccountId(String accountId);
	
	SummonerModel findBySummonerId(String summonerId);
}
