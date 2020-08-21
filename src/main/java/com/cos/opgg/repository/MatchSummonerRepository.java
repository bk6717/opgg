package com.cos.opgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.opgg.api.model.MatchSummonerModel;

public interface MatchSummonerRepository extends JpaRepository<MatchSummonerModel, Integer>{
	MatchSummonerModel save(MatchSummonerModel matchSummonerModel);
	
	List<MatchSummonerModel> findByGameId(long gameId);
	
}
