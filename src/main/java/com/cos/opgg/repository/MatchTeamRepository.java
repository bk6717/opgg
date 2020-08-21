package com.cos.opgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.opgg.api.model.MatchTeamModel;

public interface MatchTeamRepository extends JpaRepository<MatchTeamModel, Integer>{
	MatchTeamModel save(MatchTeamModel matchTeamModel);
	
	List<MatchTeamModel> findByGameId(long gameId);
}
