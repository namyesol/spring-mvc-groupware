package com.groupware.dao.notice;

import java.util.List;


import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.groupware.common.PageRequestDTO;
import com.groupware.dto.notice.NoticeDTO;
import com.groupware.dto.notice.NoticeDetailsDTO;

@Repository
public class NoticeDAO {

	@Autowired
	SqlSessionTemplate template;
	
	public void insert(NoticeDTO notice) {
		template.insert("NoticeMapper.insert", notice);
	}

	public NoticeDTO getNoticeByNo(Long noticeNum) {
		return template.selectOne("NoticeMapper.getNoticeByNum", noticeNum);
	}

	public List<NoticeDTO> getNoticeList() {
		return template.selectList("NoticeMapper.getNoticeList");
	}

	public void update(NoticeDTO notice) {
		template.update("NoticeMapper.update", notice);
	}

	public void delete(Long noticeNum) {
		template.delete("NoticeMapper.delete", noticeNum);
	}

	public NoticeDetailsDTO getNoticeDetailsByNo(Long noticeNum) {
		return template.selectOne("NoticeMapper.getNoticeDetailsByNum", noticeNum);
	}

	public List<NoticeDetailsDTO> getNoticeDetailsList(PageRequestDTO page) {
		return template.selectList("NoticeMapper.getNoticeDetailsList", page);
	}

	public void increaseViews(Long noticeNum) {
		template.update("NoticeMapper.increaseViews", noticeNum);
	}
	
	public int countNotice() {
		return template.selectOne("NoticeMapper.countNotice");
	}
}
