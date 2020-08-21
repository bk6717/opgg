package com.cos.opgg.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.api.apimodel.ApiEntry;
import com.cos.opgg.api.apimodel.ApiMatch;
import com.cos.opgg.api.apimodel.ApiMatchEntry;
import com.cos.opgg.api.apimodel.ApiRanking;
import com.cos.opgg.api.apimodel.ApiSummoner;
import com.cos.opgg.api.apimodel.attr.match.Participant;
import com.cos.opgg.api.apimodel.attr.match.ParticipantIdentity;
import com.cos.opgg.api.apimodel.attr.match.Player;
import com.cos.opgg.api.apimodel.attr.match.Stats;
import com.cos.opgg.api.apimodel.attr.match.Team;
import com.cos.opgg.api.apimodel.attr.matchentry.Match;
import com.cos.opgg.api.apimodel.attr.rank.Entry;
import com.cos.opgg.api.model.EntryModel;
import com.cos.opgg.api.model.MatchCommonModel;
import com.cos.opgg.api.model.MatchSummonerModel;
import com.cos.opgg.api.model.MatchTeamModel;
import com.cos.opgg.api.model.RankingModel;
import com.cos.opgg.api.model.SummonerModel;
import com.cos.opgg.repository.EntryRepository;
import com.cos.opgg.repository.MatchCommonRepository;
import com.cos.opgg.repository.MatchSummonerRepository;
import com.cos.opgg.repository.MatchTeamRepository;
import com.cos.opgg.repository.RankingRepository;
import com.cos.opgg.repository.SummonerRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class TestController {

	@Autowired
	SummonerRepository summonerRepository;

	@Autowired
	EntryRepository entryRepository;

	@Autowired
	MatchCommonRepository matchCommonRepository;

	@Autowired
	MatchTeamRepository matchTeamRepository;

	@Autowired
	MatchSummonerRepository matchSummonerRepository;

	@Autowired
	RankingRepository rankingRepository;

//	@GetMapping("/inputsummoner/{name}")
//	public String inputsummoner(@PathVariable String name) {
//		
//		try {
//			URL url = new URL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+name+"?api_key=RGAPI-d9294016-a43c-4ed1-be3d-c8edf5715d88");
//			
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			
//			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
//			
//			StringBuilder sb = new StringBuilder();
//			
//			String input = "";
//			while ((input = br.readLine()) != null) {
//				sb.append(input);
//			}
//			
//			System.out.println(sb.toString());
//			
//			br.close(); // 버퍼 닫기
//			con.disconnect(); // 스트림 닫기
//			
//			Gson gson = new Gson();
//			ApiSummoner apiSummoner = gson.fromJson(sb.toString(), ApiSummoner.class);
//			
//			SummonerModel summonerModel = SummonerModel.builder()
//					.summonerId(apiSummoner.getId())
//					.accountId(apiSummoner.getAccountId())
//					.name(apiSummoner.getName())
//					.profileIconId(apiSummoner.getProfileIconId())
//					.puuid(apiSummoner.getPuuid())
//					.summonerLevel(apiSummoner.getSummonerLevel())
//					.revisionDate(apiSummoner.getRevisionDate())
//					.build();
//			
//			SummonerModel summonerEntity = summonerRepository.save(summonerModel);
//			
//			System.out.println(summonerEntity);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return "test";
//	}
	
	@GetMapping("test/summoner/{name}")
	public SummonerModel getsummoner(@PathVariable String name) {
		
		String tempName = name.replace(" ", "").toLowerCase();
		
		return summonerRepository.findByName(tempName);
	}
	
	@GetMapping("test/entry/{name}")
	public List<EntryModel> getEntry(@PathVariable String name) {
		
		String tempName = name.replace(" ", "").toLowerCase();
		
		return entryRepository.findBySummonerName(tempName);
	}
	
	@GetMapping("test/rank")
	public List<RankingModel> getRank() {
		
		return rankingRepository.findAll();
	}
	
	@GetMapping("test/matchcommon/{gameId}")
	public MatchCommonModel getMatchcommon(@PathVariable long gameId) {
		
		return matchCommonRepository.findByGameId(gameId);
	}
	
	@GetMapping("test/matchteam/{gameId}")
	public List<MatchTeamModel> getMatchTeam(@PathVariable long gameId) {
		
		return matchTeamRepository.findByGameId(gameId);
	}
	
	@GetMapping("test/matchsummoner/{gameId}")
	public List<MatchSummonerModel> getMatchSummoner(@PathVariable long gameId) {
		
		return matchSummonerRepository.findByGameId(gameId);
	}
	
	

	@GetMapping("/input/{name}")
	public String inputentry(@PathVariable String name) {
		
		String apiKey = "RGAPI-d9294016-a43c-4ed1-be3d-c8edf5715d88";

		try {
			
			Thread.sleep(300);
			// 소환사 정보

			String summonerid;
			String accountId;
			long matchId;

			URL url = new URL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name
					+ "?api_key="+apiKey);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			System.out.println(sb.toString());

			Gson gson = new Gson();
			ApiSummoner apiSummoner = gson.fromJson(sb.toString(), ApiSummoner.class);

			SummonerModel summonerModel = SummonerModel.builder().summonerId(apiSummoner.getId())
					.accountId(apiSummoner.getAccountId()).name(apiSummoner.getName())
					.profileIconId(apiSummoner.getProfileIconId()).puuid(apiSummoner.getPuuid())
					.summonerLevel(apiSummoner.getSummonerLevel()).revisionDate(apiSummoner.getRevisionDate()).build();

			SummonerModel summonerEntity = summonerRepository.save(summonerModel);

			Thread.sleep(300);
			// 엔트리

			summonerid = apiSummoner.getId();

			System.out.println(summonerid);

			URL url1 = new URL("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerid
					+ "?api_key="+apiKey);

			con = (HttpURLConnection) url1.openConnection();

			br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			sb = new StringBuilder();

			input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

//			System.out.println(sb.toString());

			Type listType = new TypeToken<ArrayList<ApiEntry>>() {
			}.getType();

			List<ApiEntry> apiEntrys = gson.fromJson(sb.toString(), listType);

			System.out.println(apiEntrys);

			for (ApiEntry apiEntry : apiEntrys) {
				String division = "1";
				if (apiEntry.getRank().equals("I")) {
					division = "1";
				} else if (apiEntry.getRank().equals("II")) {
					division = "2";
				} else if (apiEntry.getRank().equals("III")) {
					division = "3";
				} else if (apiEntry.getRank().equals("IV")) {
					division = "4";
				}

				EntryModel entryModel = EntryModel.builder().leagueId(apiEntry.getLeagueId())
						.leaguePoints(apiEntry.getLeaguePoints()).queueType(apiEntry.getQueueType())
						.summonerId(apiEntry.getSummonerId()).summonerName(apiEntry.getSummonerName())
						.rank(apiEntry.getRank()).tier(apiEntry.getTier()).wins(apiEntry.getWins())
						.losses(apiEntry.getLosses()).tierRankId(apiEntry.getTier().toLowerCase() + "_" + division)
						.build();

				entryRepository.save(entryModel);
			}

			Thread.sleep(300);
			// 경기리스트

			accountId = apiSummoner.getAccountId();

			System.out.println(accountId);

			URL url2 = new URL("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/"+accountId+"?api_key="+apiKey);

			con = (HttpURLConnection) url2.openConnection();

			br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			sb = new StringBuilder();

			input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

//			System.out.println(sb.toString());

			ApiMatchEntry apiMatchEntry = gson.fromJson(sb.toString(), ApiMatchEntry.class);

			for (Match match : apiMatchEntry.getMatches()) {
				
				// 매치 가져오기
				matchId = match.getGameId();

				System.out.println(matchId);

				URL url3 = new URL("https://kr.api.riotgames.com/lol/match/v4/matches/"+matchId+"?api_key="+apiKey);

				con = (HttpURLConnection) url3.openConnection();

				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

				sb = new StringBuilder();

				input = "";
				while ((input = br.readLine()) != null) {
					sb.append(input);
				}

//				System.out.println(sb.toString());

				ApiMatch apiMatch = gson.fromJson(sb.toString(), ApiMatch.class);
				
				MatchCommonModel matchCommonModel = MatchCommonModel.builder()
						.gameCreation(apiMatch.getGameCreation())
						.gameDuration(apiMatch.getGameDuration())
						.gameId(apiMatch.getGameId())
						.gameMode(apiMatch.getGameMode())
						.mapId(apiMatch.getMapId())
						.platformId(apiMatch.getPlatformId())
						.seasonId(apiMatch.getSeasonId())
						.build();
				
				matchCommonRepository.save(matchCommonModel);
				
				for (Team team : apiMatch.getTeams()) {
					
					MatchTeamModel matchTeamModel = MatchTeamModel.builder()
							.baronKills(team.getBaronKills())
							.dragonkils(team.getDragonKills())
							.gameId(apiMatch.getGameId())
							.teamId(team.getTeamId())
							.towerKills(team.getTowerKills())
							.win(team.getWin())
							.build();
					
					matchTeamRepository.save(matchTeamModel);
				}
				
				for (int i = 0; i < 10; i++) {
					
					Participant participant = apiMatch.getParticipants().get(i);
					ParticipantIdentity participantIdentity = apiMatch.getParticipantIdentities().get(i);
					Player player = participantIdentity.getPlayer();
					Stats stats = participant.getStats();
					
					MatchSummonerModel matchSummonerModel = MatchSummonerModel.builder()
							.assists(stats.getAssists())
							.championId(participant.getChampionId())
							.champLevel(stats.getChampLevel())
							.deaths(stats.getDeaths())
							.kills(stats.getKills())
							.gameCreation(apiMatch.getGameCreation())
							.gameDuration(apiMatch.getGameDuration())
							.gameId(apiMatch.getGameId())
							.goldEarned(stats.getGoldEarned())
							.item0(stats.getItem0())
							.item1(stats.getItem1())
							.item2(stats.getItem2())
							.item3(stats.getItem3())
							.item4(stats.getItem4())
							.item5(stats.getItem5())
							.item6(stats.getItem6())
							.participantId(i)
							.perkPrimaryStyle(stats.getPerkPrimaryStyle())
							.perkSubStyle(stats.getPerkSubStyle())
							.queueId(apiMatch.getQueueId())
							.sightWardsBoughtInGame(stats.getSightWardsBoughtInGame())
							.wardsKilled(stats.getWardsKilled())
							.wardsPlaced(stats.getWardsPlaced())
							.spell1Id(participant.getSpell1Id())
							.spell2Id(participant.getSpell2Id())
							.summonerName(player.getSummonerName())
							.teamId(participant.getTeamId())
							.totalDamageDealtToChampions(stats.getTotalDamageDealtToChampions())
							.totalMinionsKilled(stats.getTotalMinionsKilled())
							.win(stats.isWin())
							.build();
					
					matchSummonerRepository.save(matchSummonerModel);
					
				}
				

				Thread.sleep(300);
				
			}
			
			br.close(); // 버퍼 닫기
			con.disconnect(); // 스트림 닫기
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "test";
	}

	@GetMapping("/inputrank/ch")
	public String test() {

		try {
			URL url = new URL("https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key=RGAPI-d9294016-a43c-4ed1-be3d-c8edf5715d88");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			System.out.println(sb.toString());

			br.close(); // 버퍼 닫기
			con.disconnect(); // 스트림 닫기

			Gson gson = new Gson();
			ApiRanking apiRanking = gson.fromJson(sb.toString(), ApiRanking.class);

			for (Entry entry : apiRanking.getEntries()) {
				RankingModel rankingModel = RankingModel.builder()
						.lose(entry.getLosses())
						.win(entry.wins)
						.leaguePoints(entry.getLeaguePoints())
						.tier("CHALLENGER")
						.Rank(entry.getRank())
						.summonerName(entry.getSummonerName())
						.summonerId(entry.getSummonerId())
						.build();
				
				rankingRepository.save(rankingModel);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "test";
	}
	

}
