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
public class MatchEntryModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "accountId")
    private String accountId;
	
	@Column(name = "puuid")
    private String puuid;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "platformId")
	private String platformId;
	
	@Column(name = "gameId")
	private long gameId;
	
	@Column(name = "champion")
	private long champion;
	
	@Column(name = "queue")
	private long queue;
	
	@Column(name = "season")
	private long season;
	
	@Column(name = "timestamp")
	private long timestamp;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "lane")
	private String lane;

}
