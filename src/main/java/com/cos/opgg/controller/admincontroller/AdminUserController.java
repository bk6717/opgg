package com.cos.opgg.controller.admincontroller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.opgg.model.User;
import com.cos.opgg.repository.UserRepository;
import com.cos.opgg.service.AdminUserService;

@Controller
public class AdminUserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired AdminUserService adminUserService;
	
	// 루트주소 요청시 어드민 로그인페이지로 이동
	@GetMapping({"","/"})
	public String index() {
		
		return "redirect:/admin/login";
	}
	

	@GetMapping("/admin/logout")
	public String logout(HttpServletResponse response) {

	    Cookie jwtCookie = new Cookie("jwtToken", null);
	    jwtCookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
	    jwtCookie.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
		response.addCookie(jwtCookie);
		
		return "loginForm";
	}
	

	// 어드민 로그인 페이지
	@GetMapping("/admin/login")
	public String login() {
		
		return "loginForm";
	}
	
	//유저 검색기능
	@PostMapping("/admin/user/search")
	public @ResponseBody List<User> search(@RequestBody Map<String, String> data) {
		
		return userRepository.mFindByEmail(data.get("email"));
	}
	
	//유저 권한변경
	@PutMapping("/admin/user/updateRole/{id}")
	public @ResponseBody String updateRole(@RequestBody Map<String, String> roles, @PathVariable int id) {
		
		adminUserService.updateRole(roles, id);
		
		return "성공" ;
	} 
	
	//유저 보기
	@GetMapping({"/admin/user"})	
	public String User(Model model) {
		
		model.addAttribute("users", userRepository.findAll());
				
		return "adminUser";
	}
	
	//유저 삭제 
	@DeleteMapping("/admin/user/delete/{id}")
	public @ResponseBody String deleteUser(@PathVariable int id ) {
		
		userRepository.deleteById(id);
		
		return "성공";
	}
	
	
}
