package com.cos.opgg.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
import com.cos.opgg.api.dto.DetailDto;
import com.cos.opgg.api.dto.InfoDto;
import com.cos.opgg.api.dto.RankingDto;
import com.cos.opgg.api.model.EntryModel;
import com.cos.opgg.api.model.MatchCommonModel;
import com.cos.opgg.api.model.MatchEntryModel;
import com.cos.opgg.api.model.MatchSummonerModel;
import com.cos.opgg.api.model.MatchTeamModel;
import com.cos.opgg.api.model.RankingModel;
import com.cos.opgg.api.model.SummonerModel;
import com.cos.opgg.dto.GetApiMatchEntryDto;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.repository.EntryRepository;
import com.cos.opgg.repository.MatchCommonRepository;
import com.cos.opgg.repository.MatchEntryRepository;
import com.cos.opgg.repository.MatchSummonerRepository;
import com.cos.opgg.repository.MatchTeamRepository;
import com.cos.opgg.repository.RankingRepository;
import com.cos.opgg.repository.SummonerRepository;
import com.cos.opgg.util.DescRank;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class ApiService {

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

	// 싱글톤
	private static ApiService apiUtil = new ApiService();

	private ApiService() {
	};

	public static ApiService getInstance() {
		return apiUtil;
	}

	public synchronized RespDto<?> getRank(String name) {

		List<RankingDto> rankingDtos = new ArrayList<>();

		long allUser = rankingRepository.countUser();

		String tempName0 = name.replace(" ", "").toLowerCase();

		RankingModel rankingEntity0 = rankingRepository.findBySummonerName(tempName0);

		if (rankingEntity0 == null) {
			return new RespDto<RankingDto>(HttpStatus.BAD_REQUEST.value(), "해당 유저 정보가 없습니다.", null);
		}

		long pageStart = rankingEntity0.getId();

		RankingDto rankingDtoHeader = RankingDto.builder().type(0).allUser(allUser).build();

		rankingDtos.add(rankingDtoHeader);

		List<RankingModel> rankingEntities = rankingRepository.find10ByPage(pageStart);

		if (rankingEntities == null || rankingEntities.size() == 0) {
			return new RespDto<RankingDto>(HttpStatus.BAD_REQUEST.value(), "해당 페이지의 데이터가 없습니다.", null);
		}

		for (RankingModel rankingEntity : rankingEntities) {
			
			RankingDto rankingDto = null;

			String tempName = (rankingEntity.getSummonerName()).replace(" ", "").toLowerCase();
			SummonerModel summonerEntity = summonerRepository.findByName(tempName);

			if (summonerEntity == null) {
//				tempName = (rankingEntity.getSummonerName()).replace(" ", "").toLowerCase();
//
//				ApiSummoner apiSummoner = getApiSummoner(tempName, getApikey());
//				try {
//					Thread.sleep(1210);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//				if (apiSummoner == null) {
//					rankingDto = RankingDto.builder().type(1).rankingModel(rankingEntity).build();
//				} else {
//					summonerEntity = summonerRepository.findByName(tempName);
//					rankingDto = RankingDto.builder().type(1).summonerModel(summonerEntity)
//							.rankingModel(rankingEntity).build();
//				}
				
				rankingDto = RankingDto.builder().type(1).rankingModel(rankingEntity).build();
				
				rankingDtos.add(rankingDto);


			} else {
				summonerEntity = summonerRepository.findByName(tempName);
				rankingDto = RankingDto.builder().type(1).summonerModel(summonerEntity)
						.rankingModel(rankingEntity).build();
				rankingDtos.add(rankingDto);
			}
		}

		return new RespDto<List<RankingDto>>(HttpStatus.RESET_CONTENT.value(), "정상", rankingDtos);
	}

	public synchronized RespDto<?> getRank(long page) {

		List<RankingDto> rankingDtos = new ArrayList<>();

		long pageStart;

		if (page < 1) {
			return new RespDto<RankingDto>(HttpStatus.BAD_REQUEST.value(), "잘못된 page를 입력하셨습니다.", null);
		} else if (page == 1) {
			pageStart = page;
		} else {
			pageStart = (page - 1) * 10 + 1;
		}

		long allUser = rankingRepository.countUser();

		RankingDto rankingDtoHeader = RankingDto.builder().type(0).allUser(allUser).build();

		rankingDtos.add(rankingDtoHeader);

		List<RankingModel> rankingEntities = rankingRepository.find10ByPage(pageStart);

		if (rankingEntities == null || rankingEntities.size() == 0) {
			return new RespDto<RankingDto>(HttpStatus.BAD_REQUEST.value(), "해당 페이지의 데이터가 없습니다.", null);
		}

		for (RankingModel rankingEntity : rankingEntities) {
			
			RankingDto rankingDto = null;

			String tempName = (rankingEntity.getSummonerName()).replace(" ", "").toLowerCase();
			SummonerModel summonerEntity = summonerRepository.findByName(tempName);

			if (summonerEntity == null) {
//				tempName = (rankingEntity.getSummonerName()).replace(" ", "").toLowerCase();
//
//				ApiSummoner apiSummoner = getApiSummoner(tempName, getApikey());
//				try {
//					Thread.sleep(1210);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//				if (apiSummoner == null) {
//					rankingDto = RankingDto.builder().type(1).rankingModel(rankingEntity).build();
//				} else {
//					summonerEntity = summonerRepository.findByName(tempName);
//					rankingDto = RankingDto.builder().type(1).summonerModel(summonerEntity)
//							.rankingModel(rankingEntity).build();
//				}
				
				rankingDto = RankingDto.builder().type(1).rankingModel(rankingEntity).build();
				
				rankingDtos.add(rankingDto);


			} else {
				summonerEntity = summonerRepository.findByName(tempName);
				rankingDto = RankingDto.builder().type(1).summonerModel(summonerEntity)
						.rankingModel(rankingEntity).build();
				rankingDtos.add(rankingDto);
			}

		}

		return new RespDto<List<RankingDto>>(HttpStatus.OK.value(), "정상", rankingDtos);
	}

	public synchronized RespDto<?> getDetail(long gameId) {

		MatchCommonModel matchCommonEntity = matchCommonRepository.findByGameId(gameId);

		if (matchCommonEntity == null) {
			return new RespDto<DetailDto>(HttpStatus.BAD_REQUEST.value(), "잘못된 gameId를 입력하셨습니다.", null);
		}

		List<MatchTeamModel> matchTeamEntities = matchTeamRepository.findAllByGameId(gameId);

		if (matchTeamEntities == null || matchTeamEntities.size() == 0) {
			return new RespDto<DetailDto>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에 경기 팀 자료가 없습니다.", null);
		}

		MatchTeamModel winTeam;
		MatchTeamModel loseTeam;
		

		if (matchTeamEntities.get(0).getWin().equals("Win")) {
			winTeam = matchTeamEntities.get(0);
			loseTeam = matchTeamEntities.get(1);
		} else {
			winTeam = matchTeamEntities.get(1);
			loseTeam = matchTeamEntities.get(0);
		}

		List<MatchSummonerModel> matchSummonerEntities = matchSummonerRepository
				.findAllByGameIdOrderByParticipantIdAsc(gameId);

		if (matchSummonerEntities == null || matchSummonerEntities.size() == 0) {
			return new RespDto<DetailDto>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에 경기 유저 자료가 없습니다.", null);
		}

		List<MatchSummonerModel> winMatchSummonerEntities = new ArrayList<>();
		List<MatchSummonerModel> loseMatchSummonerEntities = new ArrayList<>();

		for (MatchSummonerModel matchSummonerEntity : matchSummonerEntities) {
			if (matchSummonerEntity.isWin()) {
				winMatchSummonerEntities.add(matchSummonerEntity);
			} else {
				loseMatchSummonerEntities.add(matchSummonerEntity);
			}
		}

		DetailDto detailDto = DetailDto.builder().matchCommonModel(matchCommonEntity).winTeam(winTeam)
				.loseTeam(loseTeam).winSummonerModels(winMatchSummonerEntities)
				.loseSummonerModels(loseMatchSummonerEntities).build();

		return new RespDto<DetailDto>(HttpStatus.OK.value(), "정상", detailDto);
	}

	public synchronized RespDto<?> getInfo(String name) {

		List<InfoDto> infoDtos = new ArrayList<>();

		// 공백과 대소문자 구분 제거
		String tempName = name.replace(" ", "").toLowerCase();

		// 소환사 정보 db에서 가져오기
		SummonerModel summonerEntity = summonerRepository.findByName(tempName);

		ApiSummoner apiSummoner = null;

		// db에 소환사 아이디가 없으면 api로 가져오기
		if (summonerEntity == null) {

			// api서버에 소환사 아이디가 있는지 확인하고 없으면 업데이트
			apiSummoner = getApiSummoner(tempName, getApikey());
			try {
				Thread.sleep(1210);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// api서버에도 소환사 아이디가 없다면 리턴
			if (apiSummoner == null) {
				return new RespDto<List<InfoDto>>(HttpStatus.BAD_REQUEST.value(), "소환사 아이디가 없습니다.", null);
			}

			summonerEntity = summonerRepository.findByName(tempName);
		}

		// 소환사 엔트리 가져오기
		List<EntryModel> entryEntities = entryRepository.findAllBySummonerId(summonerEntity.getSummonerId());

		// db에 소환사 아이디가 없으면 api로 가져오기
		if (entryEntities == null || entryEntities.size() == 0) {

			// api서버에 소환사 아이디가 있는지 확인하고 있으면 저장
			List<ApiEntry> apiEntries = getApiEntries(summonerEntity.getSummonerId(), getApikey());
			try {
				Thread.sleep(1210);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// api서버에도 아이디 없다면
			if (apiEntries == null) {

				List<EntryModel> entryModels = new ArrayList<>();
				entryModels.add(EntryModel.builder().build());
				entryModels.add(EntryModel.builder().build());

				// 헤더에 소환사정보만 담아넣기
				InfoDto infoDtoHeader = InfoDto.builder().type(0).summonerModel(summonerEntity).entryModels(entryModels)
						.build();

				infoDtos.add(infoDtoHeader);

				return new RespDto<List<InfoDto>>(HttpStatus.OK.value(), "소환사 엔트리 정보가 없습니다.", infoDtos);
			}
			entryEntities = entryRepository.findAllBySummonerId(summonerEntity.getSummonerId());

		}

		if (entryEntities.size() < 2) {
			entryEntities.add(EntryModel.builder().build());
		}

		List<EntryModel> entryModels = new ArrayList<>();

		if (entryEntities.get(0).getQueueType().equals("RANKED_SOLO_5x5")) {
			entryModels.add(entryEntities.get(0));
			entryModels.add(entryEntities.get(1));
		} else {
			entryModels.add(entryEntities.get(1));
			entryModels.add(entryEntities.get(0));
		}

		// 랭킹가져오기
		RankingModel rankingEntity = rankingRepository.findBySummonerName(tempName);

		// 헤더
		InfoDto infoDtoHeader = null;

		if (rankingEntity == null) {
			infoDtoHeader = InfoDto.builder().type(0).summonerModel(summonerEntity).entryModels(entryModels).build();
		} else {
			infoDtoHeader = InfoDto.builder().type(0).radder(rankingEntity.getId()).summonerModel(summonerEntity)
					.entryModels(entryModels).build();
		}

		infoDtos.add(infoDtoHeader);

		// 소환사의 경기 가져오기
		List<MatchSummonerModel> matchSummonerModels = matchSummonerRepository
				.findAllByAccountIdOrderByGameCreationDesc(summonerEntity.getAccountId());

		System.out.println("matchSummonerModels size " + matchSummonerModels.size());

		// 경기내용이 없을 경우
		if (matchSummonerModels == null || matchSummonerModels.size() == 0) {

			GetApiMatchEntryDto getApiMatchEntryDto = GetApiMatchEntryDto.builder()
					.accountId(summonerEntity.getAccountId()).puuid(summonerEntity.getName())
					.summonerName(summonerEntity.getName()).build();

			// api서버에서 확인하고 있으면 db저장
			ApiMatchEntry apiMatchEntry = getApiMatchEntry(getApiMatchEntryDto, getApikey());
			try {
				Thread.sleep(1210);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (apiMatchEntry == null) {
				return new RespDto<List<InfoDto>>(HttpStatus.OK.value(), "경기 엔트리 정보가 없습니다", infoDtos);
			}

			int countMatch = 0;

			for (Match match : apiMatchEntry.getMatches()) {

				// 트랜잭션으로 만들어야됨.
				MatchCommonModel matchCommonEntity = matchCommonRepository.findByGameId(match.getGameId());

				// 이미 가져온 경기가 있다면 넘기기
				if (matchCommonEntity != null) {
					continue;
				}

				// 20경기만 가져오기
				if (countMatch > 19) {
					break;
				}

				countMatch++;

				// 매치아이디로 경기 가져와서 db저장
				ApiMatch apiMatch = getApiMatch(match.getGameId(), getApikey());
				try {
					Thread.sleep(1210);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (apiMatch == null) {
					return new RespDto<List<InfoDto>>(HttpStatus.OK.value(), "경기 내용을 가져오지 못했습니다.", infoDtos);
				}

			}

			matchSummonerModels = matchSummonerRepository
					.findAllByAccountIdOrderByGameCreationDesc(summonerEntity.getAccountId());

		}

		// 경기 유저 내용 dto에 담기
		for (MatchSummonerModel matchSummonerEntity : matchSummonerModels) {

			InfoDto infoDto = null;

			infoDto = InfoDto.builder().type(1).matchSummonerModel(matchSummonerEntity).build();

			infoDtos.add(infoDto);

		}

		return new RespDto<List<InfoDto>>(HttpStatus.OK.value(), "정상", infoDtos);
	}

	// api데이터가져오기
	public synchronized boolean getApiData(String name) {

		// 공백과 대소문자 구분 제거
		String tempName = name.replace(" ", "").toLowerCase();

		try {

			// apiKey 가져오기
			String apiKey = getApikey();

			// 소환사 정보

			ApiSummoner apiSummoner = getApiSummoner(tempName, apiKey);
			Thread.sleep(1210);

			if (apiSummoner == null) {
				return false;
			}

			// 엔트리
			List<ApiEntry> apiEntries = getApiEntries(apiSummoner.getId(), apiKey);
			Thread.sleep(1210);

			if (apiEntries == null || apiEntries.size() == 0) {
				return false;
			}

			GetApiMatchEntryDto getApiMatchEntryDto = GetApiMatchEntryDto.builder()
					.accountId(apiSummoner.getAccountId()).puuid(apiSummoner.getName())
					.summonerName(apiSummoner.getName()).build();

			// 경기리스트
			ApiMatchEntry apiMatchEntry = getApiMatchEntry(getApiMatchEntryDto, apiKey);
			Thread.sleep(1210);

			if (apiMatchEntry == null) {
				return false;
			}

			int countMatch = 0;

			for (Match match : apiMatchEntry.getMatches()) {

				MatchCommonModel matchCommonEntity = matchCommonRepository.findByGameId(match.getGameId());

				// 이미 가져온 경기가 있다면 넘기기
				if (matchCommonEntity != null) {
					continue;
				}

				// 20경기만 가져오기
				if (countMatch > 19) {
					break;
				}

				countMatch++;

				ApiMatch apiMatch = getApiMatch(match.getGameId(), apiKey);
				Thread.sleep(1210);

				if (apiMatch == null) {
					return false;
				}

			}

			return true;

		} catch (Exception e) {
			System.out.println("정상적으로 데이터를 가져오는데 실패하였습니다.");
		}

		return false;
	}

	// 모든 랭킹정보 가져오기
	public synchronized void getAllRank() {

		getChRank();
		getGrMaRank();
		getMaRank();

		List<String> tierStrings = new ArrayList<>();
		tierStrings.add("DIAMOND");
		tierStrings.add("PLATINUM");
		tierStrings.add("GOLD");
		tierStrings.add("SILVER");
		tierStrings.add("BRONZE");

		List<String> rankStrings = new ArrayList<>();
		rankStrings.add("I");
		rankStrings.add("II");
		rankStrings.add("III");
		rankStrings.add("IV");

		for (String tier : tierStrings) {

			for (String rank : rankStrings) {

				getCommonRank(tier, rank);

			}

		}
	}

	// api키 가져오기
	private String getApikey() {
		try {

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

			return sb.toString();

		} catch (Exception e) {
			System.out.println("api키를 가져오지 못했습니다.");
		}
		return null;
	}

	@Transactional
	private synchronized ApiSummoner getApiSummoner(String name, String apiKey) {

		try {
			// 소환사 정보
			System.out.println("소환사정보 가져오기 시작");
			System.out.println(name);

			URL url = new URL(
					"https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=" + apiKey);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			System.out.println("HttpURLConnection con = (HttpURLConnection) url.openConnection();");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			
			System.out.println("BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), \"UTF-8\"));");

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}
			
			System.out.println("StringBuilder sb = new StringBuilder();");

			Gson gson = new Gson();

			System.out.println("Gson gson = new Gson();");
			
			ApiSummoner apiSummoner = gson.fromJson(sb.toString(), ApiSummoner.class);
			System.out.println("apiSummoner 낫널");
			if (apiSummoner == null) {
				System.out.println("apiSummoner == null");
				return null;

			}

			String tempName = name.replace(" ", "").toLowerCase();



			SummonerModel summonerEntity = summonerRepository.findByName(tempName);

			SummonerModel summonerModel = null;
			System.out.println("summonerEntity");

			if (summonerEntity == null) {
				System.out.println("summonerEntity == null");
				summonerModel = SummonerModel.builder().summonerId(apiSummoner.getId())
						.accountId(apiSummoner.getAccountId()).name(apiSummoner.getName())
						.profileIconId(apiSummoner.getProfileIconId()).puuid(apiSummoner.getPuuid())
						.summonerLevel(apiSummoner.getSummonerLevel()).revisionDate(apiSummoner.getRevisionDate())
						.build();

			} else {

				summonerModel = SummonerModel.builder().id(summonerEntity.getId()).summonerId(apiSummoner.getId())
						.accountId(apiSummoner.getAccountId()).name(apiSummoner.getName())
						.profileIconId(apiSummoner.getProfileIconId()).puuid(apiSummoner.getPuuid())
						.summonerLevel(apiSummoner.getSummonerLevel()).revisionDate(apiSummoner.getRevisionDate())
						.build();

			}

			summonerRepository.save(summonerModel);

			return apiSummoner;

		} catch (Exception e) {

			System.out.println("getApiSummoner 소환사정보를 가져오지 못했습니다.");

		}

		return null;
	}

	@Transactional
	private synchronized ApiSummoner getApiSummonerByAccountId(String accountId, String apiKey) {

		try {
			// 소환사 정보

			URL url = new URL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-account/" + accountId
					+ "?api_key=" + apiKey);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			Gson gson = new Gson();

			ApiSummoner apiSummoner = gson.fromJson(sb.toString(), ApiSummoner.class);

			if (apiSummoner == null) {

				return null;

			}

			SummonerModel summonerEntity = summonerRepository.findByAccountId(accountId);

			SummonerModel summonerModel = null;

			if (summonerEntity == null) {

				summonerModel = SummonerModel.builder().summonerId(apiSummoner.getId())
						.accountId(apiSummoner.getAccountId()).name(apiSummoner.getName())
						.profileIconId(apiSummoner.getProfileIconId()).puuid(apiSummoner.getPuuid())
						.summonerLevel(apiSummoner.getSummonerLevel()).revisionDate(apiSummoner.getRevisionDate())
						.build();

			} else {

				summonerModel = SummonerModel.builder().id(summonerEntity.getId()).summonerId(apiSummoner.getId())
						.accountId(apiSummoner.getAccountId()).name(apiSummoner.getName())
						.profileIconId(apiSummoner.getProfileIconId()).puuid(apiSummoner.getPuuid())
						.summonerLevel(apiSummoner.getSummonerLevel()).revisionDate(apiSummoner.getRevisionDate())
						.build();

			}

			summonerRepository.save(summonerModel);

			return apiSummoner;

		} catch (Exception e) {

			System.out.println("소환사정보를 가져오지 못했습니다.");

		}

		return null;
	}

	@Transactional
	private synchronized List<ApiEntry> getApiEntries(String summonerId, String apiKey) {

		// 엔트리가져오기
		try {

			URL url1 = new URL("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId
					+ "?api_key=" + apiKey);

			HttpURLConnection con = (HttpURLConnection) url1.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			Gson gson = new Gson();

			Type listType = new TypeToken<ArrayList<ApiEntry>>() {
			}.getType();

			List<ApiEntry> apiEntries = gson.fromJson(sb.toString(), listType);

			if (apiEntries == null || apiEntries.size() == 0) {
				return null;
			}

			for (ApiEntry apiEntry : apiEntries) {
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

				EntryModel entryEntity = entryRepository.findBySummonerIdAndQueueType(apiEntry.getSummonerId(),
						apiEntry.getQueueType());

				EntryModel entryModel = null;

				if (entryEntity == null) {

					entryModel = EntryModel.builder().leagueId(apiEntry.getLeagueId())
							.leaguePoints(apiEntry.getLeaguePoints()).queueType(apiEntry.getQueueType())
							.summonerId(apiEntry.getSummonerId()).summonerName(apiEntry.getSummonerName())
							.rank(apiEntry.getRank()).tier(apiEntry.getTier()).wins(apiEntry.getWins())
							.losses(apiEntry.getLosses()).tierRankId(apiEntry.getTier().toLowerCase() + "_" + division)
							.build();
				} else {

					entryModel = EntryModel.builder().id(entryEntity.getId()).leagueId(apiEntry.getLeagueId())
							.leaguePoints(apiEntry.getLeaguePoints()).queueType(apiEntry.getQueueType())
							.summonerId(apiEntry.getSummonerId()).summonerName(apiEntry.getSummonerName())
							.rank(apiEntry.getRank()).tier(apiEntry.getTier()).wins(apiEntry.getWins())
							.losses(apiEntry.getLosses()).tierRankId(apiEntry.getTier().toLowerCase() + "_" + division)
							.build();

				}

				entryRepository.save(entryModel);

			}

			return apiEntries;

		} catch (Exception e) {
			System.out.println("apiEntries 가져오기 실패");
		}

		return null;

	}

	@Transactional
	private synchronized ApiMatchEntry getApiMatchEntry(GetApiMatchEntryDto dto, String apiKey) {
		// 매치엔트리 가져오기
		try {
			URL url2 = new URL("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/" + dto.getAccountId()
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

			if (apiMatchEntry == null) {
				return null;
			}

			for (Match match : apiMatchEntry.getMatches()) {

				MatchEntryModel matchEntryEntity = matchEntryRepository.findByGameId(match.getGameId());

				MatchEntryModel matchEntryModel = null;

				if (matchEntryEntity == null) {

					matchEntryModel = MatchEntryModel.builder().accountId(dto.getAccountId())
							.champion(match.getChampion()).gameId(match.getGameId()).lane(match.getLane())
							.name(dto.getSummonerName()).platformId(match.getPlatformId()).puuid(dto.getPuuid())
							.queue(match.getQueue()).role(match.getRole()).season(match.getSeason())
							.timestamp(match.getTimestamp()).build();

				} else {

					matchEntryModel = MatchEntryModel.builder().id(matchEntryEntity.getId())
							.accountId(dto.getAccountId()).champion(match.getChampion()).gameId(match.getGameId())
							.lane(match.getLane()).name(dto.getSummonerName()).platformId(match.getPlatformId())
							.puuid(dto.getPuuid()).queue(match.getQueue()).role(match.getRole())
							.season(match.getSeason()).timestamp(match.getTimestamp()).build();

				}

				matchEntryRepository.save(matchEntryModel);

			}

			return apiMatchEntry;

		} catch (Exception e) {

			System.out.println("매치엔트리를 가져오지 못했습니다.");

		}

		return null;
	}

	@Transactional
	private synchronized ApiMatch getApiMatch(long matchId, String apiKey) {

		try {
			// 매치 가져오기

			URL url3 = new URL("https://kr.api.riotgames.com/lol/match/v4/matches/" + matchId + "?api_key=" + apiKey);

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

			if (apiMatch == null) {
				return null;
			}

			MatchCommonModel matchCommonEntity = matchCommonRepository.findByGameId(apiMatch.getGameId());

			if (matchCommonEntity == null) {

				MatchCommonModel matchCommonModel = MatchCommonModel.builder().gameCreation(apiMatch.getGameCreation())
						.gameDuration(apiMatch.getGameDuration()).gameId(apiMatch.getGameId())
						.queueId(apiMatch.getQueueId()).gameMode(apiMatch.getGameMode()).mapId(apiMatch.getMapId())
						.platformId(apiMatch.getPlatformId()).seasonId(apiMatch.getSeasonId()).build();

				matchCommonRepository.save(matchCommonModel);

			}

			// 서비스로 만들어서 트랜잭션화 해야됨

			List<MatchTeamModel> matchTeamEntities = matchTeamRepository.findAllByGameId(apiMatch.getGameId());

			if (matchCommonEntity == null) {

				for (Team team : apiMatch.getTeams()) {

					MatchTeamModel matchTeamModel = MatchTeamModel.builder().baronKills(team.getBaronKills())
							.dragonkils(team.getDragonKills()).gameId(apiMatch.getGameId()).teamId(team.getTeamId())
							.towerKills(team.getTowerKills()).win(team.getWin()).build();

					matchTeamRepository.save(matchTeamModel);
				}

			} else if (matchTeamEntities.size() < 2) {
				matchTeamRepository.deleteAllByGameId(apiMatch.getGameId());

				for (Team team : apiMatch.getTeams()) {

					MatchTeamModel matchTeamModel = MatchTeamModel.builder().baronKills(team.getBaronKills())
							.dragonkils(team.getDragonKills()).gameId(apiMatch.getGameId()).teamId(team.getTeamId())
							.towerKills(team.getTowerKills()).win(team.getWin()).build();

					matchTeamRepository.save(matchTeamModel);
				}

			}

			List<MatchSummonerModel> matchSummonerEntities = matchSummonerRepository
					.findAllByGameId(apiMatch.getGameId());

			if (matchSummonerEntities == null) {
				// 널이면 바로실행

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
							.item5(stats.getItem5()).item6(stats.getItem6())
							.participantId(participant.getParticipantId()).perkPrimaryStyle(stats.getPerkPrimaryStyle())
							.perkSubStyle(stats.getPerkSubStyle()).queueId(apiMatch.getQueueId())
							.sightWardsBoughtInGame(stats.getSightWardsBoughtInGame())
							.wardsKilled(stats.getWardsKilled()).wardsPlaced(stats.getWardsPlaced())
							.spell1Id(participant.getSpell1Id()).spell2Id(participant.getSpell2Id())
							.summonerName(player.getSummonerName()).teamId(participant.getTeamId())
							.totalDamageDealtToChampions(stats.getTotalDamageDealtToChampions())
							.accountId(player.getAccountId()).totalMinionsKilled(stats.getTotalMinionsKilled())
							.win(stats.isWin()).build();

					matchSummonerRepository.save(matchSummonerModel);

				}

			} else if (matchSummonerEntities.size() < 10) {
				// 소환사가 10명 미만일 경우 모두 지우고 다시입력
				matchSummonerRepository.deleteAllByGameId(apiMatch.getGameId());

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
							.item5(stats.getItem5()).item6(stats.getItem6())
							.participantId(participant.getParticipantId()).perkPrimaryStyle(stats.getPerkPrimaryStyle())
							.perkSubStyle(stats.getPerkSubStyle()).queueId(apiMatch.getQueueId())
							.sightWardsBoughtInGame(stats.getSightWardsBoughtInGame())
							.wardsKilled(stats.getWardsKilled()).wardsPlaced(stats.getWardsPlaced())
							.spell1Id(participant.getSpell1Id()).spell2Id(participant.getSpell2Id())
							.summonerName(player.getSummonerName()).teamId(participant.getTeamId())
							.totalDamageDealtToChampions(stats.getTotalDamageDealtToChampions())
							.accountId(player.getAccountId()).totalMinionsKilled(stats.getTotalMinionsKilled())
							.win(stats.isWin()).build();

					matchSummonerRepository.save(matchSummonerModel);

				}
			}

			return apiMatch;

		} catch (Exception e) {
			System.out.println("경기를 가져오지 못했습니다.");
		}

		return null;

	}

	// 챌린저 랭크 가져오기
	@Transactional
	private synchronized void getChRank() {
		try {
			URL url = new URL(
					"https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key="
							+ getApikey());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			br.close(); // 버퍼 닫기
			con.disconnect(); // 스트림 닫기

			Gson gson = new Gson();
			ApiRanking apiRanking = gson.fromJson(sb.toString(), ApiRanking.class);
			Thread.sleep(1210);

			if (apiRanking == null) {
				return;
			}

			List<Entry> entries = apiRanking.getEntries();

			Collections.sort(entries, new DescRank());

			for (Entry entry : entries) {

				String tempName = (entry.getSummonerName()).replace(" ", "").toLowerCase();

				RankingModel rankingEntity = rankingRepository.findBySummonerName(tempName);

				RankingModel rankingModel = null;

				if (rankingEntity == null) {

					rankingModel = RankingModel.builder().lose(entry.getLosses()).win(entry.wins)
							.leaguePoints(entry.getLeaguePoints()).tier("CHALLENGER").rank(entry.getRank())
							.summonerName(entry.getSummonerName()).summonerId(entry.getSummonerId()).build();

				} else if (rankingEntity.getSummonerId().equals(entry.getSummonerId())) {

					rankingModel = RankingModel.builder().id(rankingEntity.getId()).lose(entry.getLosses())
							.win(entry.wins).leaguePoints(entry.getLeaguePoints()).tier("CHALLENGER")
							.rank(entry.getRank()).summonerName(entry.getSummonerName())
							.summonerId(entry.getSummonerId()).build();
				}

				rankingRepository.save(rankingModel);
			}

		} catch (Exception e) {
			System.out.println("챌린저 정보 가져오기에 실패하였습니다.");
		}
	}

	// 그랜드마스터 랭크정보가져오기
	@Transactional
	private synchronized void getGrMaRank() {
		try {
			URL url = new URL(
					"https://kr.api.riotgames.com/lol/league/v4/grandmasterleagues/by-queue/RANKED_SOLO_5x5?api_key="
							+ getApikey());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			br.close(); // 버퍼 닫기
			con.disconnect(); // 스트림 닫기

			Gson gson = new Gson();
			ApiRanking apiRanking = gson.fromJson(sb.toString(), ApiRanking.class);
			Thread.sleep(1210);

			if (apiRanking == null) {
				return;
			}

			List<Entry> entries = apiRanking.getEntries();

			Collections.sort(entries, new DescRank());

			for (Entry entry : entries) {

				String tempName = (entry.getSummonerName()).replace(" ", "").toLowerCase();

				RankingModel rankingEntity = rankingRepository.findBySummonerName(tempName);

				RankingModel rankingModel = null;

				if (rankingEntity == null) {

					rankingModel = RankingModel.builder().lose(entry.getLosses()).win(entry.wins)
							.leaguePoints(entry.getLeaguePoints()).tier("GRANDMASTER").rank(entry.getRank())
							.summonerName(entry.getSummonerName()).summonerId(entry.getSummonerId()).build();

				} else if (rankingEntity.getSummonerId().equals(entry.getSummonerId())) {

					rankingModel = RankingModel.builder().id(rankingEntity.getId()).lose(entry.getLosses())
							.win(entry.wins).leaguePoints(entry.getLeaguePoints()).tier("GRANDMASTER")
							.rank(entry.getRank()).summonerName(entry.getSummonerName())
							.summonerId(entry.getSummonerId()).build();
				}

				rankingRepository.save(rankingModel);
			}

		} catch (Exception e) {
			try {
				Thread.sleep(1210);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("챌린저 정보 가져오기에 실패하였습니다.");
		}

	}

	// 마스터 랭크정보 가져오기
	@Transactional
	private synchronized void getMaRank() {
		try {
			URL url = new URL(
					"https://kr.api.riotgames.com/lol/league/v4/masterleagues/by-queue/RANKED_SOLO_5x5?api_key="
							+ getApikey());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String input = "";
			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			br.close(); // 버퍼 닫기
			con.disconnect(); // 스트림 닫기

			Gson gson = new Gson();
			ApiRanking apiRanking = gson.fromJson(sb.toString(), ApiRanking.class);
			Thread.sleep(1210);

			if (apiRanking == null) {
				return;
			}

			List<Entry> entries = apiRanking.getEntries();

			Collections.sort(entries, new DescRank());

			for (Entry entry : entries) {

				String tempName = (entry.getSummonerName()).replace(" ", "").toLowerCase();

				RankingModel rankingEntity = rankingRepository.findBySummonerName(tempName);

				RankingModel rankingModel = null;

				if (rankingEntity == null) {

					rankingModel = RankingModel.builder().lose(entry.getLosses()).win(entry.wins)
							.leaguePoints(entry.getLeaguePoints()).tier("MASTER").rank(entry.getRank())
							.summonerName(entry.getSummonerName()).summonerId(entry.getSummonerId()).build();

				} else if (rankingEntity.getSummonerId().equals(entry.getSummonerId())) {

					rankingModel = RankingModel.builder().id(rankingEntity.getId()).lose(entry.getLosses())
							.win(entry.wins).leaguePoints(entry.getLeaguePoints()).tier("MASTER").rank(entry.getRank())
							.summonerName(entry.getSummonerName()).summonerId(entry.getSummonerId()).build();
				}

				rankingRepository.save(rankingModel);
			}

		} catch (Exception e) {
			try {
				Thread.sleep(1210);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("챌린저 정보 가져오기에 실패하였습니다.");
		}

	}

	// 일반유저 랭크 정보 가져오기
	@Transactional
	private synchronized void getCommonRank(String tier, String rank) {

		int page = 1;
		List<Entry> entries = new ArrayList<>();

		while (true) {

			try {
				String urlSource = "https://kr.api.riotgames.com/lol/league/v4/entries/RANKED_SOLO_5x5/" + tier + "/"
						+ rank + "?page=" + page + "&api_key=" + getApikey();

				URL url = new URL(urlSource);

				HttpURLConnection con = (HttpURLConnection) url.openConnection();

				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

				StringBuilder sb = new StringBuilder();

				String input = "";
				while ((input = br.readLine()) != null) {
					sb.append(input);
				}

				br.close(); // 버퍼 닫기
				con.disconnect(); // 스트림 닫기

				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<Entry>>() {
				}.getType();

				List<Entry> apiEntries = gson.fromJson(sb.toString(), listType);

				if (apiEntries == null || apiEntries.size() == 0) {
					break;
				}

				Thread.sleep(1210);

				if (entries == null) {
					return;
				}
				entries.addAll(apiEntries);

				page++;

			} catch (Exception e) {
				try {
					Thread.sleep(1210);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.out.println("일반유저 정보 가져오기에 실패하였습니다.");
			}

		}

		Collections.sort(entries, new DescRank());

		for (Entry entry : entries) {

			String tempName = (entry.getSummonerName()).replace(" ", "").toLowerCase();

			RankingModel rankingEntity = rankingRepository.findBySummonerName(tempName);

			RankingModel rankingModel = null;

			if (rankingEntity == null) {

				rankingModel = RankingModel.builder().lose(entry.getLosses()).win(entry.wins)
						.leaguePoints(entry.getLeaguePoints()).tier(tier).rank(entry.getRank())
						.summonerName(entry.getSummonerName()).summonerId(entry.getSummonerId()).build();

			} else if (rankingEntity.getSummonerId().equals(entry.getSummonerId())) {

				rankingModel = RankingModel.builder().id(rankingEntity.getId()).lose(entry.getLosses()).win(entry.wins)
						.leaguePoints(entry.getLeaguePoints()).tier(tier).rank(entry.getRank())
						.summonerName(entry.getSummonerName()).summonerId(entry.getSummonerId()).build();
			}

			rankingRepository.save(rankingModel);
		}
	}

//	@GetMapping("test/desc")
//	public List<MatchSummonerModel> testmsm() {
//		return matchSummonerRepository.findAllBySummonerNameByOrderByGameCreationDesc("hideonbush");
//	}
//
//	@GetMapping("test/summoner/{name}")
//	public SummonerModel getsummoner(@PathVariable String name) {
//
//		String tempName = name.replace(" ", "").toLowerCase();
//
//		return summonerRepository.findByName(tempName);
//	}
//
//	@GetMapping("test/entry/{name}")
//	public List<EntryModel> getEntry(@PathVariable String name) {
//
//		String tempName = name.replace(" ", "").toLowerCase();
//
//		return entryRepository.findAllBySummonerName(tempName);
//	}
//
//	@GetMapping("test/rank")
//	public List<RankingModel> getRank() {
//
//		return rankingRepository.findAll();
//	}
//
//	@GetMapping("test/matchcommon/{gameId}")
//	public MatchCommonModel getMatchcommon(@PathVariable long gameId) {
//
//		return matchCommonRepository.findByGameId(gameId);
//	}
//
//	@GetMapping("test/matchteam/{gameId}")
//	public List<MatchTeamModel> getMatchTeam(@PathVariable long gameId) {
//
//		return matchTeamRepository.findAllByGameId(gameId);
//	}
//
//	@GetMapping("test/matchsummoner/{gameId}")
//	public List<MatchSummonerModel> getMatchSummoner(@PathVariable long gameId) {
//
//		return matchSummonerRepository.findAllByGameIdOrderByParticipantIdAsc(gameId);
//	}
}
