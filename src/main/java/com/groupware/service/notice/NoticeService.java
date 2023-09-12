package com.groupware.service.notice;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.common.PageRequestDTO;
import com.groupware.common.PageResponseDTO;
import com.groupware.dao.notice.NoticeDAO;
import com.groupware.dto.notice.NoticeDTO;
import com.groupware.dto.notice.NoticeDetailsDTO;
@Service
public class NoticeService {
	@Autowired
	private NoticeDAO dao;

	public void createNotice(Long memberNum, NoticeDTO notice) {
		dao.insert(notice); 
	}

	public NoticeDTO getNoticeByNo(Long noticeNum) {
		return dao.getNoticeByNo(noticeNum);
	}

	public List<NoticeDTO> getNoticeList() {
		return dao.getNoticeList(); 
	}

	public NoticeDetailsDTO getNoticeDetailsByNo(Long noticeNum) {
		dao.increaseViews( noticeNum);
		NoticeDetailsDTO notice = dao.getNoticeDetailsByNo(noticeNum);
		return notice;
	}

	public PageResponseDTO<NoticeDetailsDTO> getNoticeDetailsList(PageRequestDTO page) {
		int count= dao.countNotice();
		List<NoticeDetailsDTO> noticeDetailsList = dao.getNoticeDetailsList(page);
		if (!noticeDetailsList.isEmpty()) {
			return new PageResponseDTO<NoticeDetailsDTO>(page, noticeDetailsList, count);
		}
		
		return new PageResponseDTO<NoticeDetailsDTO>(page, Collections.emptyList(), 0);
	}
	
	public void updateNotice(Long noticeNum, Long memberNum, NoticeDTO updateDTO) {

			NoticeDTO notice = dao.getNoticeByNo(noticeNum);
	
			if (!memberNum.equals(notice.getMemberNum())) {
				return;
			}
	
			notice.setTitle(updateDTO.getTitle());
			notice.setContent(updateDTO.getContent());
	
			dao.update(notice);
	}

	public void deleteNotice(Long noticeNum, Long memberNum) {
			
			NoticeDTO notice = dao.getNoticeByNo(noticeNum);
	
			if (!memberNum.equals(notice.getMemberNum())) {
				return;
			}
			dao.delete(noticeNum);	
	}
}
