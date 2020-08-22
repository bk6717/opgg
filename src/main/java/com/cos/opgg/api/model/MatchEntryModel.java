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
public class MatchEntryModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    private String accountId;
    private String puuid;
    private String name;

	private String platformId;
	private long gameId;
	private long champion;
	private long queue;
	private long season;
	private long timestamp;
	private String role;
	private String lane;

}
