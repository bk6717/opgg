package com.cos.opgg.repository;

import com.cos.opgg.api.model.EntryModel;
import com.cos.opgg.api.model.MatchCommonModel;
import com.cos.opgg.api.model.MatchSummonerModel;
import com.cos.opgg.api.model.MatchTeamModel;
import com.cos.opgg.api.model.RankingModel;
import com.cos.opgg.api.model.SummonerModel;

public interface ApiRepository {
	public int saveEntryModel(EntryModel entryModel);
	
	public int saveMatchCommonModel(MatchCommonModel matchCommonModel);
	
	public int saveMatchSummonerModel(MatchSummonerModel matchSummonerModel);
	public int saveMatchTeamModel(MatchTeamModel matchTeamModel);
	public int saveRankingModel(RankingModel rankingModel);
	public int saveSummonerModel(SummonerModel summonerModel);
	
}
