package com.groupware.controller.community;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.groupware.common.PageRequestDTO;
import com.groupware.common.PageResponseDTO;
import com.groupware.dto.MemberDTO;
import com.groupware.dto.community.CommunityDTO;
import com.groupware.dto.community.CommunityDetailsDTO;
import com.groupware.dto.notice.NoticeDTO;
import com.groupware.dto.notice.NoticeDetailsDTO;
import com.groupware.service.community.CommunityService;
import com.groupware.service.notice.NoticeService;

@Controller
public class CommunityController {

	@Autowired
	CommunityService communityService;
	
	//자유게시판 리스트
	@GetMapping("/communities")
	public String getCommunityList(
			@RequestParam(required=false, defaultValue = "1") int page,
			@RequestParam(required=false, defaultValue = "5") int size,  HttpSession session, Model model) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("login");
		if(member == null) {
			return "redirect:/";
		}
		//페이지네이션 정보 생성
		PageRequestDTO pageRequest = new PageRequestDTO(page, size);
				
		PageResponseDTO<CommunityDetailsDTO> pageResponse = communityService.getCommunityDetailsList(pageRequest);
				
		model.addAttribute("pageResponse", pageResponse);
				
		return "community/community-list";

	}
	//새로작성폼 보여주기
	@GetMapping("/communities/new") 
	public String showNewCommunityForm(HttpSession session) {
		
		MemberDTO member =(MemberDTO)session.getAttribute("login");
		
		if(member == null) {
			return "redirect:/";
		}
		return "community/community-new";
	}
	//새로작성
	@PostMapping("/communities/new")
	public String newCommunity(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			HttpSession session) {
		
		MemberDTO member =(MemberDTO)session.getAttribute("login");
		
		if(member == null) {
			return "redirect:/";
		}
		Long memberNum = Long.valueOf(member.getMember_num());
		
		CommunityDTO community = new CommunityDTO(memberNum, title, content);

		communityService.save(community);
		
		return "redirect:/communities/"+ community.getComNum();
	}
	//상세페이지
	@GetMapping("/communities/{comNum}")
	public String getCommunityDetails(@PathVariable("comNum") Long comNum, HttpSession session, Model model) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("login");
		
		if(member==null) {
			return "redirect:/";
		}
		
		CommunityDetailsDTO communityDetails = communityService.getCommunityDetailsByNum(comNum);
		model.addAttribute("communityDetails", communityDetails);
		
		return "community/community-details";
	}
	//수정폼 보여주기
	@GetMapping("/communities/{comNum}/edit")
	public String showUpdatCommunityForm(@PathVariable("comNum") Long comNum, HttpSession session, Model model) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("login");
		
		if(member==null) {
			return "redirect:/";
		}
		CommunityDTO community = communityService.getCommunityByNum(comNum);
		model.addAttribute("community", community);
		
		return "community/community-edit";
	}
	//수정하기
	@PostMapping("/communities/{comNum}/edit")
	public String updateCommunity(
			@PathVariable("comNum") Long comNum,
			@RequestParam("title") String title,
			@RequestParam("content") String content,HttpSession session) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("login");
		
		if(member==null) {
			return "redirect:/";
		}
		Long memberNum = Long.valueOf(member.getMember_num());
		
		CommunityDTO updateDTO = new CommunityDTO(memberNum, title, content);

		communityService.update(comNum, memberNum, updateDTO);
		
		return "redirect:/communities/"+ comNum;
	}
	//삭제하기
	@PostMapping("/communities/{comNum}/delete") 
	public String deleteCommunity(@PathVariable Long comNum, HttpSession session) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("login");
		
		if(member==null) {
			return "redirect:/";
		}
		
		Long memberNum = Long.valueOf(member.getMember_num());
		
		communityService.delete(comNum, memberNum);
		
		return "redirect:/communities";
	}
	
	
	
	
}//end class
