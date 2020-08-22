package com.cos.opgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.opgg.api.model.RankingModel;

public interface RankingRepository extends JpaRepository<RankingModel, Integer>{
	
//	RankingModel save(RankingModel rankingModel);
	
	List<RankingModel> findAll();
}
