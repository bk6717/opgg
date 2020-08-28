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
public class MatchCommonModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "gameId",unique = true)
    private long gameId;
	
	@Column(name = "queueId")
	private long queueId;
	
	@Column(name = "platformId")
    private String platformId;
	
	@Column(name = "gameCreation")
    private long gameCreation;
	
	@Column(name = "gameDuration")
    private long gameDuration;
	
	@Column(name = "mapId")
    private long mapId;
	
	@Column(name = "seasonId")
    private long seasonId;
	
	@Column(name = "gameMode")
    private String gameMode;

}