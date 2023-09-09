package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dto.MemberDTO;
import com.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	MemberService service;
	
	// 마이페이지
	@RequestMapping("/loginCheck/myPage")
	public String myPage(HttpSession session) {
		MemberDTO dto = (MemberDTO) session.getAttribute("login");
		dto = service.myPage(dto.getMember_num());
		System.out.println("mypage dto >>>>" + dto);
		session.setAttribute("login", dto);
		return "redirect:../myPage"; // servlet-context.xml -> myPage
	}
}
