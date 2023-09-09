package com.groupware.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.groupware.service.MemberService;
import com.groupware.dto.MemberDTO;

@Controller
public class LoginController {
	@Autowired
	MemberService service;
	
	// 로그인
	@RequestMapping("login") // jsp에서 login으로 데이터 전달
	// session 사용할 경우 -> HttpSession import
	public String login(
			@RequestParam HashMap<String, String> map,
			Model model,
			HttpSession session) {
		System.out.println(map);
		MemberDTO dto = service.login(map);
		
		// dto가 있는 경우 세션에 "login"키로 dto 저장 -> top.jsp 메뉴 변경되는지 확인
		// dto가 없는 경우 model에 mesg : "아이디, 비번 없음" -> loginForm.jsp 경고창
		if (dto != null) {
			session.setAttribute("login", dto);
			return "homePage";
		} else {
			model.addAttribute("mesg", "회원정보없음");
			return "/";
		}
	} // login
	
	// 로그아웃
	@RequestMapping("/loginCheck/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // session 삭제
		// 주의 : return "main" -> 404 / sub 주소가 계속 남아있기 때문에
		// loginCheck/main.jsp 호출됨
		// 해결 : "../" 사용 -> sub 주소 삭제되고 "/" 주소만 남는다
		// servlet-context.xml에서 "/" -> main.jsp 호출하기 때문에 
		// "redirect:../"는 return "main"의 역할을 함
		// -> ? 됨
		return "redirect:../";
	}
}
