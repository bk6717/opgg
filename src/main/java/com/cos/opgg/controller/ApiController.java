package com.cos.opgg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.api.dto.InfoDto;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.service.ApiService;

@RestController
@RequestMapping("/api/")
public class ApiController{
	
	@Autowired
	ApiService apiService;	
	
	// rankingDto 가져오기 아이디 검색
		@GetMapping("ranking/name/{name}")
		public RespDto<?> getRankByName(@PathVariable String name) {

			return apiService.getRank(name);
		}

		// rankingDto 가져오기
		@GetMapping("ranking/page/{page}")
		public RespDto<?> getRankByPage(@PathVariable long page) {

			return apiService.getRank(page);
		}

		// detailDto 가져오기
		@GetMapping("detail/gameid/{gameId}")
		public RespDto<?> getDetailByGameId(@PathVariable long gameId) {

			return apiService.getDetail(gameId);
		}

		// infoDto 가져오기
		@GetMapping("info/name/{name}")
		public RespDto<?> getInfoByName(@PathVariable String name) {

			return apiService.getInfo(name);
		}

		// 전적갱신하기
		@PutMapping("info/update/name/{name}")
		public RespDto<?> updateInfoByName(@PathVariable String name) {

			boolean isGetData = apiService.getApiData(name);

			if (isGetData) {
				return apiService.getInfo(name);
			} else {
				return new RespDto<List<InfoDto>>(HttpStatus.BAD_REQUEST.value(), "전적갱신에 실패하였습니다.", null);
			}

		}

		// api데이터 연습
		@GetMapping("input/123123123/{name}")
		public String testInput(@PathVariable String name) {

			boolean b = apiService.getApiData(name);

			if (b) {
				return "입력성공";
			} else {
				return "입력실패";
			}
		}
}
