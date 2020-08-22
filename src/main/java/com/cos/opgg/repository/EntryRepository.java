package com.cos.opgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.opgg.api.model.EntryModel;

public interface EntryRepository extends JpaRepository<EntryModel, Integer>{
	
//	EntryModel save(EntryModel entryModel);
	
	@Query(value = "SELECT id, leagueId, leaguePoints, losses, queueType, rank, summonerId, summonerName, tier, tierRankId, wins   FROM entrymodel WHERE LOWER(replace(summonerName,' ', '')) LIKE ?1", nativeQuery = true)
	List<EntryModel> findAllBySummonerName(String summonerName);
	
	List<EntryModel> findAllBySummonerId(String summonerId);
}
