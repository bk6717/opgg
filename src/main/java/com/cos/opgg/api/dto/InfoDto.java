package com.cos.opgg.api.dto;

import java.util.List;

import com.cos.opgg.api.model.EntryModel;
import com.cos.opgg.api.model.MatchSummonerModel;
import com.cos.opgg.api.model.SummonerModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoDto {

    private int type;
    
    private long radder;

    private SummonerModel summonerModel;

    private List<EntryModel> entryModels;

    private MatchSummonerModel matchSummonerModel;
    
}