package com.cos.opgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.opgg.api.model.MatchEntryModel;

public interface MatchEntryRepository extends JpaRepository<MatchEntryModel, Integer>{
	
	MatchEntryModel findByAccountId(String accountId);
	
	MatchEntryModel findByGameId(long gameId);

}
