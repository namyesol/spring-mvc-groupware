package com.controller.community;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dto.MemberDTO;
import com.dto.community.CommunityDTO;
import com.service.community.CommunityService;

@WebServlet("/NewCommunityServlet")
public class NewCommunityServlet extends HttpServlet {
	
	private static final long serialVersionUID = -5099685725362951586L;

	private CommunityService communtiyService;
	
	public NewCommunityServlet() {
		this.communtiyService = new CommunityService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			String nextPage = "/community/community-new.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			Long memberNum = Long.valueOf(member.getMember_num());
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			CommunityDTO community = new CommunityDTO(memberNum, title, content);
			
			communtiyService.save(community);
			
			response.sendRedirect("/CommunityDetailsServlet"+ "?comNum=" + community.getComNum());
		}
	}

}
