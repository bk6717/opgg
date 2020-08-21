package com.cos.opgg.api.apimodel.attr.rank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entry {

    public String summonerId;
    public String summonerName;
    public long leaguePoints;
    public String rank;
    public long wins;
    public long losses;
    public boolean veteran;
    public boolean inactive;
    public boolean freshBlood;
    public boolean hotStreak;

}