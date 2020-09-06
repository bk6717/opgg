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
public class MatchTeamModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "gameId")
    private long gameId;
	
	@Column(name = "teamId")
    private long teamId;
	
	@Column(name = "win")
    private String win;
	
	@Column(name = "towerKills")
    private long towerKills;
	
	@Column(name = "baronKills")
    private long baronKills;
	
	@Column(name = "dragonkills")
    private long dragonKills;
}