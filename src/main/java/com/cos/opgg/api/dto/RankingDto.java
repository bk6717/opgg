package com.cos.opgg.api.dto;

import com.cos.opgg.api.model.RankingModel;
import com.cos.opgg.api.model.SummonerModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingDto {

    private int type;

    private long allUser;

    private SummonerModel summonerModel;

    private RankingModel rankingModel;

}