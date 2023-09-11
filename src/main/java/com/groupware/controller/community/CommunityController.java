package com.groupware.controller.community;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	public void getCommunityList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			// 페이지네이션 정보 생성
			String pageParam = request.getParameter("page");
			String sizeParam = request.getParameter("size");
			// 요청페이지의 기본 값
			int page = 1;
			// 요청페이지의 기본 크기 
			int size = 5;
			
			// 사용자의 요청이 있다면 페이지 번호와 크기를 변경함
			if (pageParam != null && !pageParam.isEmpty()) {
				page = Integer.parseInt(pageParam);
			}
			if (sizeParam != null && !sizeParam.isEmpty()) {
				size = Integer.parseInt(sizeParam);
			}
			
			PageRequestDTO pageRequest = new PageRequestDTO(page, size);
			
			PageResponseDTO<CommunityDetailsDTO> pageResponse = communityService.getCommunityDetailsList(pageRequest);
			request.setAttribute("pageResponse", pageResponse);
			
			String nextPage = "WEB-INF/views/community/community-list.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		}
	}
	
	//새로작성폼 보여주기
	// @GetMapping("/communities/new")
	@GetMapping("/NewCommunityServlet")
	public void showNewCommunityForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			String nextPage = "WEB-INF/views/community/community-new.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		}
	}
	
	//새로작성
	// @PostMapping("/communities/new")
	@PostMapping("/NewCommunityServlet")
	public void newCommunity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			Long memberNum = Long.valueOf(member.getMember_num());
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			CommunityDTO community = new CommunityDTO(memberNum, title, content);
			
			communityService.save(community);
			
			response.sendRedirect("/CommunityDetailsServlet"+ "?comNum=" + community.getComNum());
		}
	}
	
	//상세페이지
	// @GetMapping("/communities/{comNum}") 
	@GetMapping("/CommunityDetailsServlet")
	public void getCommunityDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			long comNum = Long.parseLong(request.getParameter("comNum"));

			communityService.increaseViews(comNum);
			
			CommunityDetailsDTO communityDetails = communityService.getCommunityDetailsByNum(comNum);
			List<ReplyDetailsDTO> replyDetailsList = replyService.getReplyDetailsListByComNum(comNum);
			
			request.setAttribute("communityDetails", communityDetails);
			request.setAttribute("replyDetailsList", replyDetailsList);

			String nextPage = "WEB-INF/views/community/community-details.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		}
	}
	
	//수정폼 보여주기
	// @GetMapping("/communities/{comNum}/edit")
	@GetMapping("/EditCommunityServlet")
	public void showUpdatCommunityForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			Long comNum = Long.parseLong(request.getParameter("comNum"));

			CommunityDTO community = communityService.getCommunityByNum(comNum);

			request.setAttribute("community", community);

			String nextPage = "WEB-INF/views/community/community-edit.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		}
	}
	
	//수정하기
	// @PostMapping("/communities/{comNum}/edit")
	@PostMapping("/EditCommunityServlet")
	public void updateCommunity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			Long memberNum = Long.valueOf(member.getMember_num());
			Long comNum = Long.parseLong(request.getParameter("comNum"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			CommunityDTO updateDTO = new CommunityDTO();
			updateDTO.setTitle(title);
			updateDTO.setContent(content);
			
			communityService.update(comNum, memberNum, updateDTO);
			
			response.sendRedirect("/CommunityDetailsServlet" + "?comNum=" + comNum);
		}
	}
	
	//삭제하기
	// @PostMapping("/communities/{comNum}/delete") 
	@PostMapping("/DeleteCommunityServlet")
	public void deleteCommunity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			Long comNum = Long.parseLong(request.getParameter("comNum"));
			Long memNum = Long.valueOf(member.getMember_num());
			
			communityService.delete(comNum, memNum);
			
			response.sendRedirect("/CommunityListServlet");
		}
	}
}
