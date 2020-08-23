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
	
    private int type;
	private int statusCode;
	private String message;

    private MatchCommonModel matchCommonModel;

    private MatchTeamModel winTeam;
    private MatchTeamModel loseTeam;

    private List<MatchSummonerModel> winSummonerModels;
    private List<MatchSummonerModel> loseSummonerModels;

}