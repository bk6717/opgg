package com.cos.opgg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.dto.RespDto;
import com.cos.opgg.service.ApiService;

@RestController
public class TestController {
	
	@Autowired
	ApiService apiService;
	
	// rankingDto 가져오기 아이디 검색
	@GetMapping("test/ranking/name/{name}")
	public RespDto<?> getRankByName(@PathVariable String name) {

		return apiService.getRank(name);
	}


	// rankingDto 가져오기
	@GetMapping("test/ranking/page/{page}")
	public RespDto<?> getRankByPage(@PathVariable long page) {

		return apiService.getRank(page);
	}

	
	// detailDto 가져오기
	@GetMapping("test/detail/gameid/{gameId}")
	public RespDto<?> getDetailByGameId(@PathVariable long gameId) {

		return apiService.getDetail(gameId);
	}


	// infoDto 가져오기
	@GetMapping("test/info/name/{name}")
	public RespDto<?> getInfoByName(@PathVariable String name) {

		return apiService.getInfo(name);
	}

	
	
	@GetMapping("test/input/123123123/{name}")
	public String testInput(@PathVariable String name) {

		boolean b = apiService.getApiData(name);

		if (b) {
			return "입력성공";
		} else {
			return "입력실패";
		}
	}

	@GetMapping("test/getallrank/97987987979797987987")
	public String getAllRank() {
		
		apiService.getAllRank();
		
		return "clear";
	}
	
}
