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
public class SummonerModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private int id;
	
	@Column(name = "summonerId", unique = true)
    private String summonerId;
	
	@Column(name = "accountId",unique = true)
    private String accountId;
	
	@Column(name = "puuid",unique = true)
    private String puuid;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "profileIconId")
    private long profileIconId;
	
	@Column(name = "revisionDate")
    private long revisionDate;
	
	@Column(name = "summonerLevel")
    private long summonerLevel;

}



