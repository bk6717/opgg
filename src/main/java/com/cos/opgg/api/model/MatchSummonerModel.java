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
public class MatchSummonerModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "gameId")
    private long gameId;
	
	@Column(name = "queueId")
    private long queueId;

	
	@Column(name = "gameCreation")
    private long gameCreation;
	
	@Column(name = "gameDuration")
    private long gameDuration;

	@Column(name = "summonerName")
    private String summonerName;
	
	@Column(name = "accountId")
    private String accountId;
	
	@Column(name = "participantId")
    private long participantId;
	
	@Column(name = "teamId")
    private long teamId;
	
	@Column(name = "championId")
    private long championId;
	
	@Column(name = "spell1Id")
    private long spell1Id;
	
	@Column(name = "spell2Id")
    private long spell2Id;
	
	@Column(name = "win")
    private boolean win;
	
	@Column(name = "item0")
    private long item0;
	
	@Column(name = "item1")
    private long item1;
	
	@Column(name = "item2")
    private long item2;
	
	@Column(name = "item3")
    private long item3;
	
	@Column(name = "item4")
    private long item4;
	
	@Column(name = "item5")
    private long item5;
	
	@Column(name = "item6")
    private long item6;
	
	@Column(name = "kills")
    private long kills;
	
	@Column(name = "deaths")
    private long deaths;
	
	@Column(name = "assists")
    private long assists;
	
	@Column(name = "totalDamageDealtToChampions")
    private long totalDamageDealtToChampions;
	
	@Column(name = "goldEarned")
    private long goldEarned;
	
	@Column(name = "totalMinionsKilled")
    private long totalMinionsKilled;
	
	@Column(name = "champLevel")
    private long champLevel;
	
	@Column(name = "sightWardsBoughtInGame")
    private long sightWardsBoughtInGame;
	
	@Column(name = "wardsPlaced")
    private long wardsPlaced;
	
	@Column(name = "wardsKilled")
    private long wardsKilled;
	
	@Column(name = "perkPrimaryStyle")
    private long perkPrimaryStyle;
	
	@Column(name = "perkSubStyle")
    private long perkSubStyle;

}