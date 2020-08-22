package com.cos.opgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.opgg.api.model.MatchCommonModel;

public interface MatchCommonRepository extends JpaRepository<MatchCommonModel, Integer>{
	
//	MatchCommonModel save(MatchCommonModel matchCommonModel);
	
	MatchCommonModel findByGameId(long gameId);
	
}
