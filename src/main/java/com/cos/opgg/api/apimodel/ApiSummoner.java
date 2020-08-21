package com.cos.opgg.api.apimodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/eren%ED%97%8C%ED%84%B0?api_key=RGAPI-8f2ab161-b201-4d25-a846-17abf656e8e7


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiSummoner {

    private String id;
    private String accountId;
    private String puuid;
    private String name;
    private long profileIconId;
    private long revisionDate;
    private long summonerLevel;

}