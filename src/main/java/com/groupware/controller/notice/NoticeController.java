package com.groupware.controller.notice;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.groupware.common.PageRequestDTO;
import com.groupware.common.PageResponseDTO;
import com.groupware.dto.MemberDTO;
import com.groupware.dto.notice.NoticeDTO;
import com.groupware.dto.notice.NoticeDetailsDTO;
import com.groupware.service.notice.NoticeService;

@Controller
public class NoticeController {
	
	@Autowired
	NoticeService noticeService;
	
	@GetMapping("/notices")
	public String getNoticeList(@RequestParam(required=false, defaultValue = "1") int page,
								@RequestParam(required=false, defaultValue = "5") int size,  HttpSession session, Model model) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("login");
		if(member == null) {
			return "redirect:/";
		}
		//페이지네이션 정보 생성
		
		PageRequestDTO pageRequest = new PageRequestDTO(page, size);
		
		PageResponseDTO<NoticeDetailsDTO> pageResponse = noticeService.getNoticeDetailsList(pageRequest);
		
		model.addAttribute("pageResponse", pageResponse);
		
		return "notice/notice-list";
	}
	
	@GetMapping("/notices/new") 
	public String showNewNoticeForm(HttpSession session) {
		
		MemberDTO member =(MemberDTO)session.getAttribute("login");
		
		if(member == null) {
			return "redirect:/";
		}
		return "notice/notice-new";
	}
	
	@PostMapping("/notices/new")
	public String newNotice(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			HttpSession session) {
		
		MemberDTO member =(MemberDTO)session.getAttribute("login");
		
		if(member == null) {
			return "redirect:/";
		}
		Long memberNum = Long.valueOf(member.getMember_num());
		
		NoticeDTO notice = new NoticeDTO(memberNum, title, content);

		noticeService.createNotice(memberNum, notice);
		
		return "redirect:/notices/"+ notice.getNoticeNum();

	}
	
	@GetMapping("/notices/{noticeNum}")
	public String getNoticeDetails(@PathVariable("noticeNum") Long noticeNum, HttpSession session, Model model) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("login");
		
		if(member==null) {
			return "redirect:/";
		}
		
		NoticeDetailsDTO notice = noticeService.getNoticeDetailsByNo(noticeNum);
		model.addAttribute("notice", notice);
		
		return "notice/notice-details";
	}
	
	@GetMapping("/notices/{noticeNum}/edit")
	public String showUpdateNoticeForm(@PathVariable("noticeNum") Long noticeNum, HttpSession session, Model model) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("login");
		
		if(member==null) {
			return "redirect:/";
		}
		NoticeDTO notice = noticeService.getNoticeByNo(noticeNum);
		model.addAttribute("notice", notice);
		
		return "notice/notice-edit";
	}
	
	@PostMapping("/notices/{noticeNum}/edit")
	public String updateNotice(
			@PathVariable("noticeNum") Long noticeNum,
			@RequestParam("title") String title,
			@RequestParam("content") String content,HttpSession session) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("login");
		
		if(member==null) {
			return "redirect:/";
		}
		Long memberNum = Long.valueOf(member.getMember_num());
		
		NoticeDTO updateDTO = new NoticeDTO(memberNum, title, content);

		noticeService.updateNotice(noticeNum, memberNum, updateDTO);
		
		return "redirect:/notices/"+ noticeNum;
	}
	
	@PostMapping("/notices/{noticeNum}/delete") 
	public String deleteNotice(@PathVariable Long noticeNum, HttpSession session) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("login");
		
		if(member==null) {
			return "redirect:/";
		}
		
		Long memberNum = Long.valueOf(member.getMember_num());
		
		noticeService.deleteNotice(noticeNum, memberNum);
		
		return "redirect:/notices";
	}
}
