package com.cos.opgg.api.model;

import javax.persistence.Column;
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
	@Column(name = "id")
	private int id;
	
	@Column(name = "leagueId")
    private String leagueId;
	
	@Column(name = "queueType")
    private String queueType;
	
	@Column(name = "tier")
    private String tier;
	
	@Column(name = "rank")
    private String rank;
    
	@Column(name = "summonerId")
    private String summonerId;
    
	@Column(name = "summonerName")
    private String summonerName;
	
	@Column(name = "leaguePoints")
    private long leaguePoints;
	
	@Column(name = "wins")
    private long wins;
	
	@Column(name = "losses")
    private long losses;

    // 티어와 랭크 합친것
	@Column(name = "tierRankId")
    private String tierRankId;
}