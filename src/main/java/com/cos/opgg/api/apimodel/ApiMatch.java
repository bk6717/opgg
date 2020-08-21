package com.cos.opgg.api.apimodel;

import java.util.List;

import com.cos.opgg.api.apimodel.attr.match.Participant;
import com.cos.opgg.api.apimodel.attr.match.ParticipantIdentity;
import com.cos.opgg.api.apimodel.attr.match.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// https://kr.api.riotgames.com/lol/match/v4/matches/4561055619?api_key=RGAPI-8f2ab161-b201-4d25-a846-17abf656e8e7


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiMatch {

    private long gameId;
    private String platformId;
    private long gameCreation;
    private long gameDuration;
    private long queueId;
    private long mapId;
    private long seasonId;
    private String gameVersion;
    private String gameMode;
    private String gameType;
    private List<Team> teams;
    private List<Participant> participants;
    private List<ParticipantIdentity> participantIdentities;

}