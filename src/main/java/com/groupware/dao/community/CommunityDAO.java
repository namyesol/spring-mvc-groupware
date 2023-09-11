package com.dao.community;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.common.PageRequestDTO;
import com.dto.community.CommunityDTO;
import com.dto.community.CommunityDetailsDTO;

public class CommunityDAO {

	
	public void insert(SqlSession session, CommunityDTO community) {
		session.insert("CommunityMapper.insert", community);
	}

	public CommunityDTO getCommunityByNum(SqlSession session, Long comNum) {
		return session.selectOne("CommunityMapper.getCommunityByNum", comNum);
	}

	public List<CommunityDTO> getCommunityByMemberNum(SqlSession session, Long memberNum) {	
		return session.selectOne("CommunityMapper.getCommunityByMemberNum", memberNum);
	}

	public List<CommunityDTO> getCommunityList(SqlSession session ) {
		return session.selectList("CommunityMapper.getCommunityList");
	}

	public void update(SqlSession session, CommunityDTO communtiy) {
		session.update("CommunityMapper.update", communtiy);
	}

	public void delete(SqlSession session, Long comNum) {
		session.delete("CommunityMapper.delete", comNum);
	}

	public void increaseViews(SqlSession session, Long comNum) {
		session.update("CommunityMapper.increaseViews", comNum);		
	}
	
	public CommunityDetailsDTO getCommunityDetailsByNum(SqlSession session, Long comNum) {
		return session.selectOne("CommunityMapper.getCommunityDetailsByNum", comNum);
	}

	public List<CommunityDetailsDTO> getCommunityDetailsList(SqlSession session, PageRequestDTO page) {
		return session.selectList("CommunityMapper.getCommunityDetailsList", page);
	}
	
	public Integer count(SqlSession session) {
		return session.selectOne("CommunityMapper.count");
	}

}
