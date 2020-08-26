package com.cos.opgg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.api.model.SummonerModel;
import com.cos.opgg.dto.CommunityDto;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.dto.RespListDto;
import com.cos.opgg.model.Post;
import com.cos.opgg.model.Reply;
import com.cos.opgg.repository.PostRepository;
import com.cos.opgg.repository.ReplyRepository;
import com.cos.opgg.repository.SummonerRepository;
import com.cos.opgg.service.ApiService;

@RestController
public class TestController {
	
	@Autowired
	SummonerRepository summonerRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	ReplyRepository replyRepostory;
	@Autowired
	ApiService apiService;
	
	// rankingDto 가져오기 아이디 검색
	@GetMapping("test/ranking/name/{name}")
	public RespListDto<?> getRankByName(@PathVariable String name) {

		return apiService.getRank(name);
	}


	// rankingDto 가져오기
	@GetMapping("test/ranking/page/{page}")
	public RespListDto<?> getRankByPage(@PathVariable long page) {

		return apiService.getRank(page);
	}

	
	// detailDto 가져오기
	@GetMapping("test/detail/gameid/{gameId}")
	public RespDto<?> getDetailByGameId(@PathVariable long gameId) {

		return apiService.getDetail(gameId);
	}


	// infoDto 가져오기
	@GetMapping("test/info/name/{name}")
	public RespListDto<?> getInfoByName(@PathVariable String name) {

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
	
	//커뮤니티 글목록 보기
	@GetMapping("/test/post")
	public List<Post> PostTest() {
		List<Post> post = postRepository.findAll();
		
		return post;
	}
	
	//커뮤니티 글 상세보기
	@GetMapping("/test/post/{id}")
	public Post ttttt(@PathVariable int id) {
		Post post = postRepository.findById(id);
		return post;
		
	}
	
	//커뮤니티 글 검색
	@GetMapping("/test/post/search/{content}")
	public List<Post> seach(@PathVariable(name = "content") String content) {
		List<Post> post = postRepository.search(content);
		System.out.println(post);
		return post;
	}
	
	@GetMapping("/reply")
	public List<Reply> find(){
		List<Reply> reply = replyRepostory.findAll();
		return reply;
	}
	
	
}


