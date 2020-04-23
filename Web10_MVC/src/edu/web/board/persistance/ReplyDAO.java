package edu.web.board.persistance;

import java.util.List;

import edu.web.board.domain.ReplyVO;

public interface ReplyDAO {
	public int insert(ReplyVO vo);
	public List<ReplyVO> select(int bno);
	public int update(ReplyVO vo);
	public int delete(int rno, int bno);
	
} // end ReplyDAO