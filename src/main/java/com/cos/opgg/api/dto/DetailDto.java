package com.cos.opgg.api.dto;


import java.util.List;

import com.cos.opgg.api.model.MatchCommonModel;
import com.cos.opgg.api.model.MatchSummonerModel;
import com.cos.opgg.api.model.MatchTeamModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailDto {

    private MatchCommonModel matchCommonModel;

    private List<MatchTeamModel> matchTeamModels;

    private List<MatchSummonerModel> winSummonerModels;

    private List<MatchSummonerModel> loseSummonerModels;

}