package com.cos.opgg.api.apimodel.attr.match;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    private long teamId;
    private String win;
    private boolean firstBlood;
    private boolean firstTower;
    private boolean firstInhibitor;
    private boolean firstBaron;
    private boolean firstDragon;
    private boolean firstRiftHerald;
    private long towerKills;
    private long inhibitorKills;
    private long baronKills;
    private long dragonKills;
    private long vilemawKills;
    private long riftHeraldKills;
    private long dominionVictoryScore;
    private List<Ban> bans;

}