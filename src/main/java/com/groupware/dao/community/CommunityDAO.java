package com.groupware.dao.community;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.groupware.common.PageRequestDTO;
import com.groupware.dto.community.CommunityDTO;
import com.groupware.dto.community.CommunityDetailsDTO;

@Repository
public class CommunityDAO {

	@Autowired
	SqlSessionTemplate template;
	
	public void insert(CommunityDTO community) {
		template.insert("CommunityMapper.insert", community);
	}

	public CommunityDTO getCommunityByNum(Long comNum) {
		return template.selectOne("CommunityMapper.getCommunityByNum", comNum);
	}

	public List<CommunityDTO> getCommunityByMemberNum(Long memberNum) {	
		return template.selectOne("CommunityMapper.getCommunityByMemberNum", memberNum);
	}

	public List<CommunityDTO> getCommunityList() {
		return template.selectList("CommunityMapper.getCommunityList");
	}

	public void update(CommunityDTO communtiy) {
		template.update("CommunityMapper.update", communtiy);
	}

	public void delete(Long comNum) {
		template.delete("CommunityMapper.delete", comNum);
	}

	public void increaseViews(Long comNum) {
		template.update("CommunityMapper.increaseViews", comNum);		
	}
	
	public CommunityDetailsDTO getCommunityDetailsByNum(Long comNum) {
		return template.selectOne("CommunityMapper.getCommunityDetailsByNum", comNum);
	}

	public List<CommunityDetailsDTO> getCommunityDetailsList(PageRequestDTO page) {
		return template.selectList("CommunityMapper.getCommunityDetailsList", page);
	}
	
	public Integer count() {
		return template.selectOne("CommunityMapper.count");
	}

}
