package com.cos.opgg.api.apimodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/IGw6-Lj3urVwyovvU0I1HjVLQBHgBwvuw2c1tsLLPZ5DqA?api_key=RGAPI-8f2ab161-b201-4d25-a846-17abf656e8e7


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiEntry {

    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private String summonerId;
    private String summonerName;
    private long leaguePoints;
    private long wins;
    private long losses;
    private boolean veteran;
    private boolean inactive;
    private boolean freshBlood;
    private boolean hotStreak;

}