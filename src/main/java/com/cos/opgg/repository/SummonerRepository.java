package com.cos.opgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.opgg.api.model.SummonerModel;

public interface SummonerRepository extends JpaRepository<SummonerModel, Integer>{
	SummonerModel save(SummonerModel summonerModel);
	
	@Query(value = "SELECT id, accountId, name, profileIconId, puuid, revisionDate, summonerId, summonerLevel FROM summonermodel WHERE LOWER(replace(name,' ', '')) LIKE ?1", nativeQuery = true)
	SummonerModel findByName(String name);
}
