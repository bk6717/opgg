package com.cos.opgg.api.apimodel;

import java.util.List;

import com.cos.opgg.api.apimodel.attr.rank.Entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiRanking {

    public String tier;
    public String leagueId;
    public String queue;
    public String name;
    public List<Entry> entries = null;

}