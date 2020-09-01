package com.cos.opgg.controller.admincontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.User;
import com.cos.opgg.repository.UserRepository;
import com.cos.opgg.service.AdminUserService;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired AdminUserService adminUserService;
	
	@GetMapping("/login")
	public String login() {
		
		return "loginForm";
	}
	
	//유저 검색기능
	@PostMapping("user/search")
	public @ResponseBody List<User> search(@RequestBody Map<String, String> data) {
		
		return userRepository.mFindByEmail(data.get("email"));
	}
	
	//유저 권한변경
	@PutMapping("user/updateRole/{id}")
	public @ResponseBody String updateRole(@RequestBody Map<String, String> roles, @PathVariable int id) {
		
		adminUserService.updateRole(roles, id);
		
		return "성공" ;
	} 
	
	//유저 보기
	@GetMapping({"user"})	
	public String User(Model model) {
		
		model.addAttribute("users", userRepository.findAll());
				
		return "adminUser";
	}
	
	//유저 삭제 
	@DeleteMapping("user/delete/{id}")
	public @ResponseBody String deleteUser(@PathVariable int id ) {
		
		userRepository.deleteById(id);
		
		return "성공";
	}
	
	
}
