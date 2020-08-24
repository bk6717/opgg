package com.cos.opgg.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    
    private String summonerId;
    
    private String summonerName;
    private long leaguePoints;
    private long wins;
    private long losses;

    // 티어와 랭크 합친것
    private String tierRankId;
}