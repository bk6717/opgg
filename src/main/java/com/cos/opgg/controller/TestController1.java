package com.cos.opgg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.api.dto.DetailDto;
import com.cos.opgg.api.dto.InfoDto;
import com.cos.opgg.api.dto.RankingDto;
import com.cos.opgg.service.ApiService1;

@RestController
public class TestController1 {
	
	@Autowired
	ApiService1 apiService1;
	
	// rankingDto 가져오기 아이디 검색
	@GetMapping("test1/ranking/name/{name}")
	public List<RankingDto> getRankByName(@PathVariable String name) {

		return apiService1.getRank(name);
	}


	// rankingDto 가져오기
	@GetMapping("test1/ranking/page/{page}")
	public List<RankingDto> getRankByPage(@PathVariable long page) {

		return apiService1.getRank(page);
	}

	
	// detailDto 가져오기
	@GetMapping("test1/detail/gameid/{gameId}")
	public DetailDto getDetailByGameId(@PathVariable long gameId) {

		return apiService1.getDetail(gameId);
	}


	// infoDto 가져오기
	@GetMapping("test1/info/name/{name}")
	public List<InfoDto> getInfoByName(@PathVariable String name) {

		return apiService1.getInfo(name);
	}
	
	
	@GetMapping("test1/input/123123123/{name}")
	public String testInput(@PathVariable String name) {

		boolean b = apiService1.getApiData(name);

		if (b) {
			return "입력성공";
		} else {
			return "입력실패";
		}
	}

	@GetMapping("test1/getallrank/97987987979797987987")
	public String getAllRank() {
		
		apiService1.getAllRank();
		
		return "clear";
	}
	
}
