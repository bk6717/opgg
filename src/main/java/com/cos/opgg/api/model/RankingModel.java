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
public class RankingModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "summonerId")
	private String summonerId;
	
	@Column(name = "summonerName")
    private String summonerName;
	
	@Column(name = "tier")
    private String tier;
	
	@Column(name = "rank")
    private String rank;
    
	@Column(name = "leaguePoints")
    private long leaguePoints;
	
	@Column(name = "win")
    private long win;
	
	@Column(name = "lose")
    private long lose;

}