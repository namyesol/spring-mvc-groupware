package com.groupware.controller.community;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.groupware.common.PageRequestDTO;
import com.groupware.common.PageResponseDTO;
import com.groupware.dto.MemberDTO;
import com.groupware.dto.community.CommunityDTO;
import com.groupware.dto.community.CommunityDetailsDTO;
import com.groupware.dto.community.ReplyDetailsDTO;
import com.groupware.service.community.CommunityService;
import com.groupware.service.community.ReplyService;

@Controller
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private ReplyService replyService;
	
	//자유게시판 리스트
	// @GetMapping("/communities")
	@GetMapping("/CommunityListServlet")
	public String getCommunityList(
			@RequestParam(name="page", required=false, defaultValue="1") int page,
			@RequestParam(name="size", required=false, defaultValue="5") int size,
			HttpSession session, Model model) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {
			
			PageRequestDTO pageRequest = new PageRequestDTO(page, size);
			
			PageResponseDTO<CommunityDetailsDTO> pageResponse = communityService.getCommunityDetailsList(pageRequest);
			model.addAttribute("pageResponse", pageResponse);
			
			return "community/community-list";
		}
	}
	
	//새로작성폼 보여주기
	// @GetMapping("/communities/new")
	@GetMapping("/NewCommunityServlet")
	public String showNewCommunityForm(HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {
			return "community/community-new";
		}
	}
	
	//새로작성
	// @PostMapping("/communities/new")
	@PostMapping("/NewCommunityServlet")
	public String newCommunity(@RequestParam String title, @RequestParam String content, HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {
			Long memberNum = Long.valueOf(member.getMember_num());
			CommunityDTO community = new CommunityDTO(memberNum, title, content);
			
			communityService.save(community);
			
			return "redirect:" + "/CommunityDetailsServlet"+ "?comNum=" + community.getComNum();
		}
	}
	
	//상세페이지
	// @GetMapping("/communities/{comNum}") 
	@GetMapping("/CommunityDetailsServlet")
	public String getCommunityDetails(@RequestParam Long comNum, HttpSession session, Model model) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {

			communityService.increaseViews(comNum);
			
			CommunityDetailsDTO communityDetails = communityService.getCommunityDetailsByNum(comNum);
			List<ReplyDetailsDTO> replyDetailsList = replyService.getReplyDetailsListByComNum(comNum);
			
			model.addAttribute("communityDetails", communityDetails);
			model.addAttribute("replyDetailsList", replyDetailsList);

			return "community/community-details";
		}
	}
	
	//수정폼 보여주기
	// @GetMapping("/communities/{comNum}/edit")
	@GetMapping("/EditCommunityServlet")
	public String showUpdatCommunityForm(@RequestParam Long comNum, HttpSession session, Model model) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {

			CommunityDTO community = communityService.getCommunityByNum(comNum);

			model.addAttribute("community", community);

			return "community/community-edit";
		}
	}
	
	//수정하기
	// @PostMapping("/communities/{comNum}/edit")
	@PostMapping("/EditCommunityServlet")
	public String updateCommunity(
			@RequestParam Long comNum,
			@RequestParam String title,
			@RequestParam String content,
			HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {
			Long memberNum = Long.valueOf(member.getMember_num());

			CommunityDTO updateDTO = new CommunityDTO();
			updateDTO.setTitle(title);
			updateDTO.setContent(content);
			
			communityService.update(comNum, memberNum, updateDTO);
			
			return "redirect:" + "/CommunityDetailsServlet" + "?comNum=" + comNum;
		}
	}
	
	//삭제하기
	// @PostMapping("/communities/{comNum}/delete") 
	@PostMapping("/DeleteCommunityServlet")
	public String deleteCommunity(@RequestParam Long comNum, HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {
			Long memNum = Long.valueOf(member.getMember_num());
			
			communityService.delete(comNum, memNum);
			
			return "redirect:" + "/CommunityListServlet";
		}
	}
}
