package com.cos.opgg.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.cos.opgg.api.dto.InfoDto;
import com.cos.opgg.api.model.EntryModel;
import com.cos.opgg.api.model.MatchCommonModel;
import com.cos.opgg.api.model.MatchEntryModel;
import com.cos.opgg.api.model.MatchSummonerModel;
import com.cos.opgg.api.model.MatchTeamModel;
import com.cos.opgg.api.model.RankingModel;
import com.cos.opgg.api.model.SummonerModel;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.repository.EntryRepository;
import com.cos.opgg.repository.MatchCommonRepository;
import com.cos.opgg.repository.MatchEntryRepository;
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

	@Autowired
	MatchEntryRepository matchEntryRepository;


	@GetMapping("test/info/{name}")
	public RespDto<?> getInfo(@PathVariable String name) {
		
		List<InfoDto> infoDtos = new ArrayList<>();

		// 공백과 대소문자 구분 제거
		String tempName = name.replace(" ", "").toLowerCase();

		// 소환사 정보 db에서 가져오기
		SummonerModel summonerEntity = summonerRepository.findByName(tempName);
		
		ApiSummoner apiSummoner = null;
		
		// db에 소환사 아이디가 없으면 api로 가져오기
		if(summonerEntity == null) {
			
			// api서버에 소환사 아이디가 있는지 확인
			apiSummoner = getApiSummoner(tempName, getApikey());
			
			if (apiSummoner == null) {
				return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "소환사 아이디가 없습니다.", null);				
			}
			summonerEntity = summonerRepository.findByName(tempName);
		}
		

		// 소환사 엔트리 가져오기
		List<EntryModel> entryEntities = entryRepository.findAllBySummonerName(tempName);
		
		// db에 소환사 아이디가 없으면 api로 가져오기
		if(entryEntities == null) {
			
			// api서버에 소환사 아이디가 있는지 확인
			List<ApiEntry> apiEntries = getApiEntries(apiSummoner, getApikey());
			
			if (apiEntries == null) {
				
				InfoDto infoDtoHeader = InfoDto.builder()
						.type(0)
						.summonerModel(summonerEntity)
						.build();
				
				infoDtos.add(infoDtoHeader);
				
				return new RespDto<List<InfoDto>>(HttpStatus.OK.value(), "엔트리 정보가 없습니다.", infoDtos);				
			}
			entryEntities = entryRepository.findAllBySummonerName(tempName);
		}
		
		// 헤더
		InfoDto infoDtoHeader = InfoDto.builder().type(0).summonerModel(summonerEntity).entryModels(entryEntities)
				.build();
		
		infoDtos.add(infoDtoHeader);
				
		// 소환사의 경기 가져오기
		List<MatchSummonerModel> matchSummonerModels = matchSummonerRepository
				.findAllBySummonerNameByOrderByGameCreationDesc(tempName);

		// 경기내용이 없을 경우
		if (matchSummonerModels == null) {
			
			return new RespDto<List<InfoDto>>(HttpStatus.OK.value(), "경기 내용이 없습니다", infoDtos);
		}

		// 경기 유저 내용
		for (MatchSummonerModel matchSummonerEntity : matchSummonerModels) {

			InfoDto infoDto = InfoDto.builder()
					.type(1)
					.matchSummonerModel(matchSummonerEntity).build();

			infoDtos.add(infoDto);

		}

		return new RespDto<List<InfoDto>>(HttpStatus.OK.value(), "정상", infoDtos);
	}

	@GetMapping("test/summoner/{name}")
	public SummonerModel getsummoner(@PathVariable String name) {

		String tempName = name.replace(" ", "").toLowerCase();

		return summonerRepository.findByName(tempName);
	}

	@GetMapping("test/entry/{name}")
	public List<EntryModel> getEntry(@PathVariable String name) {

		String tempName = name.replace(" ", "").toLowerCase();

		return entryRepository.findAllBySummonerName(tempName);
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

		return matchTeamRepository.findAllByGameId(gameId);
	}

	@GetMapping("test/matchsummoner/{gameId}")
	public List<MatchSummonerModel> getMatchSummoner(@PathVariable long gameId) {

		return matchSummonerRepository.findAllByGameIdOrderByParticipantIdAsc(gameId);
	}
	
	

	@GetMapping("/input/{name}")
	public String testInput(@PathVariable String name) {

		boolean b = getApiData(name);
		
		if (b) {
			return "입력성공";
		} else {
			return "입력실패";
		}
	}

	@GetMapping("/inputrank/ch")
	public String test() {

		try {
			URL url = new URL(
					"https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key="+getApikey());

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
				RankingModel rankingModel = RankingModel.builder().lose(entry.getLosses()).win(entry.wins)
						.leaguePoints(entry.getLeaguePoints()).tier("CHALLENGER").Rank(entry.getRank())
						.summonerName(entry.getSummonerName()).summonerId(entry.getSummonerId()).build();

				rankingRepository.save(rankingModel);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "test";
	}
	
	private String getApikey() {
		// api키 가져오기
		try {
			String apiKey;

			URL keyUrl = new URL("http://59.20.79.42/server/riotapikey.html");

			HttpURLConnection con = (HttpURLConnection) keyUrl.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			br.close(); // 버퍼 닫기
			con.disconnect(); // 스트림 닫기

			System.out.println(sb.toString());

			return sb.toString();

		} catch (Exception e) {
			System.out.println("api키를 가져오지 못했습니다.");
		}
		return null;
	}

	@GetMapping("testdb/{name}/{apikey}")
	private ApiSummoner getApiSummoner(@PathVariable(name = "name") String name, @PathVariable(name = "apikey") String apiKey) {
		
		try {
			// 소환사 정보

			URL url = new URL(
					"https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=" + apiKey);

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
			
			
			SummonerModel summonerEntity = summonerRepository.findByName(name);
			
			if(summonerEntity == null) {
				summonerRepository.save(summonerModel);
			}

			return apiSummoner;
			
		} catch (Exception e) {
			
			System.out.println("소환사정보를 가져오지 못했습니다.");
		}
		
		return null;
	}
	
	private List<ApiEntry> getApiEntries(ApiSummoner apiSummoner, String apiKey) {
		
		// 엔트리가져오기
		try {

			URL url1 = new URL("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + apiSummoner.getId()
					+ "?api_key=" + apiKey);

			HttpURLConnection con = (HttpURLConnection) url1.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}


			Type listType = new TypeToken<ArrayList<ApiEntry>>() {
			}.getType();

			Gson gson = new Gson();
			
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
				
				List<EntryModel> entryEntities = entryRepository.findAllBySummonerId(apiSummoner.getId());
				
				if(entryEntities == null) {
					entryRepository.save(entryModel);
				}

			}
			
			return apiEntrys;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
		
	}
	
	private ApiMatchEntry getApiMatchEntry(ApiSummoner apiSummoner, String apiKey) {
		// 매치엔트리 가져오기
		try {
			URL url2 = new URL("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/" + apiSummoner.getAccountId()
					+ "?api_key=" + apiKey);

			HttpURLConnection con = (HttpURLConnection) url2.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			Gson gson = new Gson();
			
			ApiMatchEntry apiMatchEntry = gson.fromJson(sb.toString(), ApiMatchEntry.class);
			
			for (Match match : apiMatchEntry.getMatches()) {

				MatchEntryModel matchEntryModel = MatchEntryModel.builder().accountId(apiSummoner.getAccountId())
						.champion(match.getChampion()).gameId(match.getGameId()).lane(match.getLane())
						.name(apiSummoner.getName()).platformId(match.getPlatformId()).puuid(apiSummoner.getPuuid())
						.queue(match.getQueue()).role(match.getRole()).season(match.getSeason())
						.timestamp(match.getTimestamp()).build();
				
				
//				List<EntryModel> entryEntities = entryRepository.findAllBySummonerId(summonerId);
//				
//				if(entryEntities == null) {
//					entryRepository.save(entryModel);
//				}
				
				MatchEntryModel matchEntryEntity = matchEntryRepository.findByGameId(match.getGameId());
				
				if (matchEntryEntity == null) {
					matchEntryRepository.save(matchEntryModel);		
				}
			}
			
			return apiMatchEntry;
			
		} catch (Exception e) {
			System.out.println("매치엔트리를 가져오지 못했습니다.");
		}
		
		return null;
	}
	
	private ApiMatch getApiMatch(long matchId, String apiKey) {
		
		try {
			// 매치 가져오기

			URL url3 = new URL(
					"https://kr.api.riotgames.com/lol/match/v4/matches/" + matchId + "?api_key=" + apiKey);

			HttpURLConnection con = (HttpURLConnection) url3.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			
			Gson gson = new Gson();
			
			// 매치 가져오기
			
			ApiMatch apiMatch = gson.fromJson(sb.toString(), ApiMatch.class);

			MatchCommonModel matchCommonModel = MatchCommonModel.builder().gameCreation(apiMatch.getGameCreation())
					.gameDuration(apiMatch.getGameDuration()).gameId(apiMatch.getGameId())
					.gameMode(apiMatch.getGameMode()).mapId(apiMatch.getMapId())
					.platformId(apiMatch.getPlatformId()).seasonId(apiMatch.getSeasonId()).build();
			
			MatchCommonModel matchCommonEntity = matchCommonRepository.findByGameId(apiMatch.getGameId());
			
			// 서비스로 만들어서 트랜잭션화 해야됨
			if(matchCommonEntity == null) {
				matchCommonRepository.save(matchCommonModel);				
			} else {
				return apiMatch;
			}
			

			for (Team team : apiMatch.getTeams()) {

				MatchTeamModel matchTeamModel = MatchTeamModel.builder().baronKills(team.getBaronKills())
						.dragonkils(team.getDragonKills()).gameId(apiMatch.getGameId()).teamId(team.getTeamId())
						.towerKills(team.getTowerKills()).win(team.getWin()).build();

				matchTeamRepository.save(matchTeamModel);
			}

			for (int i = 0; i < 10; i++) {
				
				Participant participant = apiMatch.getParticipants().get(i);
				ParticipantIdentity participantIdentity = apiMatch.getParticipantIdentities().get(i);
				Player player = participantIdentity.getPlayer();
				Stats stats = participant.getStats();

				MatchSummonerModel matchSummonerModel = MatchSummonerModel.builder().assists(stats.getAssists())
						.championId(participant.getChampionId()).champLevel(stats.getChampLevel())
						.deaths(stats.getDeaths()).kills(stats.getKills()).gameCreation(apiMatch.getGameCreation())
						.gameDuration(apiMatch.getGameDuration()).gameId(apiMatch.getGameId())
						.goldEarned(stats.getGoldEarned()).item0(stats.getItem0()).item1(stats.getItem1())
						.item2(stats.getItem2()).item3(stats.getItem3()).item4(stats.getItem4())
						.item5(stats.getItem5()).item6(stats.getItem6()).participantId(participant.getParticipantId())
						.perkPrimaryStyle(stats.getPerkPrimaryStyle()).perkSubStyle(stats.getPerkSubStyle())
						.queueId(apiMatch.getQueueId()).sightWardsBoughtInGame(stats.getSightWardsBoughtInGame())
						.wardsKilled(stats.getWardsKilled()).wardsPlaced(stats.getWardsPlaced())
						.spell1Id(participant.getSpell1Id()).spell2Id(participant.getSpell2Id())
						.summonerName(player.getSummonerName()).teamId(participant.getTeamId())
						.totalDamageDealtToChampions(stats.getTotalDamageDealtToChampions())
						.totalMinionsKilled(stats.getTotalMinionsKilled()).win(stats.isWin()).build();

				matchSummonerRepository.save(matchSummonerModel);

			}
			
			return apiMatch;
			
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("경기를 가져오지 못했습니다.");
		}
		
		return null;
		
	}
	
	// api데이터가져오기
	private boolean getApiData(String name) {
		
		try {

			// apiKey 가져오기
			String apiKey = getApikey();

			// 소환사 정보

			ApiSummoner apiSummoner = getApiSummoner(name, apiKey);
			Thread.sleep(300);
			
			// 엔트리
			List<ApiEntry> apiEntrys = getApiEntries(apiSummoner, apiKey);
			Thread.sleep(300);

			// 경기리스트
			ApiMatchEntry apiMatchEntry = getApiMatchEntry(apiSummoner, apiKey);
			Thread.sleep(300);

			int countMatch = 0;

			for (Match match : apiMatchEntry.getMatches()) {

				if (countMatch > 19) {
					break;
				}

				countMatch++;
				
				ApiMatch apiMatch = getApiMatch(match.getGameId(), apiKey);
				
				Thread.sleep(300);

			}
			
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
}
