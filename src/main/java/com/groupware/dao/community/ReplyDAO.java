package com.dao.community;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.dto.community.ReplyDTO;
import com.dto.community.ReplyDetailsDTO;

public class ReplyDAO {
	
	public void insert(SqlSession session, ReplyDTO reply) {
		session.insert("ReplyMapper.insert", reply);
	}

	public ReplyDTO getReplyByNum(SqlSession session, Long replyNum) {
		return session.selectOne("ReplyMapper.getReplyByNum", replyNum);
	}

	public List<ReplyDTO> getReplyListByComNum(SqlSession session, Long comNum) {
		return session.selectList("ReplyMapper.getReplyListByComNum");
	}

	public void update(SqlSession session, ReplyDTO reply) {
		session.update("ReplyMapper.update", reply);
	}

	public void delete(SqlSession session, Long replyNum) {
		session.delete("ReplyMapper.delete", replyNum);
	}
	
	public List<ReplyDetailsDTO> getReplyDetailsListByComNum(SqlSession session, Long comNum) {
		return session.selectList("ReplyMapper.getReplyDetailsListByComNum", comNum);
	}
}
