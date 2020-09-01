package com.cos.opgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.opgg.api.model.RankingModel;

public interface RankingRepository extends JpaRepository<RankingModel, Integer>{

	List<RankingModel> findAll();
	
	// 오더바이 앞에 By를 꼭 붙일 것
	List<RankingModel> findAllByOrderByLeaguePoints();
	
	// 페이징 테스트
	@Query(value = "SELECT * FROM rankingmodel WHERE id >= ?1 ORDER BY id ASC LIMIT 10", nativeQuery = true)
	List<RankingModel> find10ByPage(long pageStart);
	
	// 페이징 테스트
	@Query(value = "SELECT * FROM rankingmodel WHERE id >= ?1 ORDER BY id ASC LIMIT 100", nativeQuery = true)
	List<RankingModel> find100ByPage(long pageStart);
	
	@Query(value = "SELECT * FROM rankingmodel WHERE LOWER(REPLACE(summonerName,' ', '')) LIKE ?1", nativeQuery = true)
	RankingModel findBySummonerName(String summonerName);
	
	RankingModel findBySummonerId(String summonerId);
	
	@Query(value = "SELECT count(id) FROM rankingmodel", nativeQuery = true)
	long countUser();
	
}
